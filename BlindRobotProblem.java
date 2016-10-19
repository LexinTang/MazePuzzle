package mazeworld;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BlindRobotProblem extends UUSearchProblem {
	private Maze maze;
	private int goalx, goaly;
	
	public BlindRobotProblem(Maze maze, int gx, int gy){
		Set<Pair> start_state = new HashSet<Pair>(); 
		for (int i = 0; i < maze.cols; i++){
			for (int j = 0; j < maze.rows; j++){
				if (maze.cells[i][j]){
					start_state.add(new Pair(i, j));
				}
			}
		}
		startNode = new BlindRobotNode(start_state, 0, null, "Initial Belief State");
		this.maze = maze;
		goalx = gx;
		goaly = gy;
	}
	
	public String[] action = {"West", "South", "East", "North"};
	
	private class Pair{
		public int x;
		public int y;
		public Pair(int x, int y){
			this.x = x;
			this.y = y;
		}
	
		@Override
		public boolean equals(Object other) {
			return (this.x == ((Pair) other).x && this.y == ((Pair) other).y);
		}
		
		
		@Override
		public int hashCode() {
			return this.x * 100 + this.y;
		}
		
		@Override
		public String toString() {
			String node_state = "(" + this.x + ", " + this.y + ")";
			return node_state;
		}
	}
	
	private class BlindRobotNode implements UUSearchNode, Comparable<BlindRobotNode>{
		private Set<Pair> state; 
		private int depth;
		private int heuristic;
		private int cost;
		private BlindRobotNode parent;
		private String prevAction;
		
		public BlindRobotNode(Set<Pair> set, int d, BlindRobotNode p, String a) {
			state = new HashSet<Pair>();

			for (Pair position : set){
				state.add(new Pair(position.x, position.y));
			}
			//System.out.println("this node " + this.toString());
			
			depth = d;
			
			heuristic = 0;	// Manhattan distance
			for (Pair position : state){
				heuristic += Math.abs(position.x - goalx) + Math.abs(position.y - goaly);
			}
			
			cost = depth + heuristic;
			parent = p;
			prevAction = a;
		}
		
		public ArrayList<UUSearchNode> getSuccessors() {
			
			ArrayList<UUSearchNode> reachableStates = new ArrayList<UUSearchNode>();
			int x_max = maze.cols - 1;
			int y_max = maze.rows - 1;
			int[] dir_x = {-1, 0, 1, 0}; // direction = West, South, East, North
			int[] dir_y = {0, -1, 0, 1};
			
			for (int i = 0; i < 4; i++){
				Set<Pair> newState = new HashSet<Pair>();
				for (Pair position : this.state){
					int x_new = position.x + dir_x[i];
					int y_new = position.y + dir_y[i];
				
					if (x_new >= 0 && x_new <= x_max && y_new >=0 && y_new <= y_max && maze.cells[x_new][y_new]){
						newState.add(new Pair(x_new, y_new));
					}
					else{
						newState.add(position);
					}
				}
				reachableStates.add(new BlindRobotNode(newState, depth + 1, this, action[i]));
			}
			
			return reachableStates;
		}
		
		@Override
		public boolean goalTest() {
			if (this.state.size() == 1){
				for (Pair position : this.state){
					if (position.x == goalx && position.y == goaly){
						return true;
					}
				}
			}
			
			return false;
		}
		
		@Override
		public boolean equals(Object other) {
			return this.state.equals(((BlindRobotNode) other).state);
		}
		
		@Override 
		public int compareTo(BlindRobotNode other){
			return this.cost - other.getCost();
		}
		
		@Override
		public int hashCode() {
			return this.state.hashCode();
		}
		
		@Override
		public String toString() {
			// you write this method
			String node_state = prevAction ;//+ "-> setSize = " + this.state.size() + " {";
			/*for (Pair position : this.state){
				node_state += position + " ";
			}
			node_state += "}";*/
			return node_state;
		}
		
		@Override
		public UUSearchNode getParent(){
			return parent;
		}

		@Override
		public int getDepth() {
			return depth;
		}
		
		@Override
		public int getCost() {
			return cost;
		}
	}
}
