package mazeworld;
import java.util.ArrayList;
import java.util.Arrays;

public class SingleRobotProblem extends UUSearchProblem{
	private Maze maze;
	private int goalx, goaly;
	
	public SingleRobotProblem(Maze maze, int sx, int sy, int gx, int gy){
		startNode = new MazeNode(sx, sy, 0, null);
		this.maze = maze;
		goalx = gx;
		goaly = gy;
	}
	
	private class MazeNode implements UUSearchNode, Comparable<MazeNode>{
		private int[] state; 
		private int depth;
		private int heuristic;
		private int cost;
		private MazeNode parent;
		public MazeNode(int x, int y, int d, MazeNode p) {
			state = new int[2];
			this.state[0] = x;
			this.state[1] = y;
			depth = d;
			heuristic = Math.abs(x - goalx) + Math.abs(y - goaly);	// Manhattan distance
			//heuristic = 0;
			cost = depth + heuristic;
			parent = p;
		}
		
		public ArrayList<UUSearchNode> getSuccessors() {
			ArrayList<UUSearchNode> reachableStates = new ArrayList<UUSearchNode>();
			int x_max = maze.cols - 1;
			int y_max = maze.rows - 1;
			int[] dir_x = {-1, 0, 1, 0};
			int[] dir_y = {0, -1, 0, 1};
			
			for (int i = 0; i < 4; i++){
				int x_new = this.state[0] + dir_x[i];
				int y_new = this.state[1] + dir_y[i];
			
				if (x_new >= 0 && x_new <= x_max && y_new >=0 && y_new <= y_max && maze.cells[x_new][y_new]){
					reachableStates.add(new MazeNode(x_new, y_new, depth + 1, this));
				}
			}
			
			return reachableStates;
		}
		
		@Override
		public boolean goalTest() {
			return (this.state[0] == goalx && this.state[1] == goaly); 
		}
		
		@Override
		public boolean equals(Object other) {
			return Arrays.equals(state, ((MazeNode) other).state);
		}

		@Override 
		public int compareTo(MazeNode other){
			return this.cost - other.getCost();
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(state);
		}
		
		@Override
		public String toString() {
			// you write this method
			String node_state = "(" + this.state[0] + ", " +  this.state[1] + ")";
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
