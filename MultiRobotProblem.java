package mazeworld;
import java.util.ArrayList;
import java.util.Arrays;

public class MultiRobotProblem extends UUSearchProblem{
	private Maze maze;
	private int[][] goal;
	private int totalRobots;
	
	public MultiRobotProblem(Maze maze, int[][] start, int[][] goal){
		this.maze = maze;
		totalRobots = start.length;
		System.out.println("total robots = " + totalRobots);
		this.goal = new int[totalRobots][2];
		for (int i = 0; i < totalRobots; i++){
			this.goal[i][0] = goal[i][0];
			this.goal[i][1] = goal[i][1];
		}

		startNode = new MultiRobotNode(start, 0, null, 0);	// start point, depth, parent node, turn
	}
	
	private class MultiRobotNode implements UUSearchNode, Comparable<MultiRobotNode>{
		private int[][] state; 
		private int depth;
		private int heuristic;
		private int cost;
		private MultiRobotNode parent;
		public int turn;
		
		public MultiRobotNode(int[][] start, int d, MultiRobotNode p, int t) {
			state = new int[totalRobots][2];
			heuristic = 0;
			for (int i = 0; i < totalRobots; i++){
				this.state[i][0] = start[i][0];
				this.state[i][1] = start[i][1];
				heuristic += Math.abs(start[i][0] - goal[i][0]) + Math.abs(start[i][1] - goal[i][1]);	// Manhattan distance
			}
			depth = d;
			cost = depth + heuristic;
			parent = p;
			turn = t;
		}
		
		private boolean isSafeState(int x, int y){
			int x_max = maze.cols - 1;
			int y_max = maze.rows - 1;
			if (x >= 0 && x <= x_max && y >=0 && y <= y_max && maze.cells[x][y]){
				for (int k = 0; k < totalRobots; k++){
					if (k == turn){
						continue;
					}
					if (x == this.state[k][0] && y == this.state[k][1]){
						return false;
					}
				}
			}
			else{
				return false;
			}

			return true;
		}
		
		public ArrayList<UUSearchNode> getSuccessors() {
			//System.out.println("get successors of node" + this.toString());
			ArrayList<UUSearchNode> reachableStates = new ArrayList<UUSearchNode>();
			int[] dir_x = {-1, 0, 1, 0};
			int[] dir_y = {0, -1, 0, 1};
			
			for (int i = 0; i < 4; i++){
				int x_new = this.state[turn][0] + dir_x[i];
				int y_new = this.state[turn][1] + dir_y[i];
				
				if (isSafeState(x_new, y_new)){
					int[][] new_state = new int[totalRobots][2];
					for (int k = 0; k < totalRobots; k++){
						if (k == turn){
							new_state[k][0] = x_new;
							new_state[k][1] = y_new;
						}
						else{
							new_state[k][0] = this.state[k][0];
							new_state[k][1] = this.state[k][1];
						}
					}
					reachableStates.add(new MultiRobotNode(new_state, depth + 1, this, (turn + 1) % totalRobots));
				}
			}
			
			// skip my turn
			reachableStates.add(new MultiRobotNode(this.state, depth + 1, this, (turn + 1) % totalRobots));

			return reachableStates;
		}
		
		@Override
		public boolean goalTest() {
			return Arrays.deepEquals(this.state, goal);
		}
		
		@Override
		public boolean equals(Object other) {
			return (Arrays.deepEquals(state, ((MultiRobotNode) other).state) && (this.turn ==  ((MultiRobotNode) other).turn));
		}

		@Override 
		public int compareTo(MultiRobotNode other){
			return this.cost - other.getCost();
		}
		
		@Override
		public int hashCode() {
			return Arrays.deepHashCode(state) * 10 + turn;
		}
		
		@Override
		public String toString() {
			// you write this method
			String node_state = (char)('A' + turn) + "'s turn: ";
			for (int i = 0; i < totalRobots; i++){
				node_state += (char)('A' + i);
				node_state += "(" + this.state[i][0] + ", " +  this.state[i][1] + ") ";
			}
			return node_state;
		}
		
		@Override
		public MultiRobotNode getParent() {
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

