package mazeworld;

import java.util.List;

public class MazeworldDriver {
	public static void main(String args[]) {
		// load maze from file
		System.out.println("Loading maze");
		Maze maze = new Maze();
		//maze.generateRandomMaze("maze4.txt", 20, 20);
		
		//maze.readFromFile("maze1.txt");	// simple maze
		//maze.readFromFile("maze2.txt");	// corridor
		//maze.readFromFile("maze3.txt");	// 7 * 7
		maze.readFromFile("maze4.txt");	// 20 * 20
		//maze.readFromFile("maze5.txt");	// 40 * 40
		
		System.out.println("Single Robot Problem");
		// set start point = bottom left corner (0, 0), goal = bottom right corner (maze.cols - 1, 0)
		SingleRobotProblem prob1 = new SingleRobotProblem(maze, 0, 0, maze.cols - 1, 0);
		
		List<UUSearchProblem.UUSearchNode> path;
		
		path = prob1.breadthFirstSearch();	
		System.out.println("bfs path length:  " + path.size() + " " + path);
		prob1.printStats();
		System.out.println("");
		
		path = prob1.AStarSearch();	
		System.out.println("A* search path length:  " + path.size() + " " + path);
		prob1.printStats();
		System.out.println("");
		
		final int MAXDEPTH = 5000;
		path = prob1.depthFirstMemoizingSearch(MAXDEPTH);	
		System.out.println("dfs memoizing path length:" + path.size() + " " + path);
		prob1.printStats();
		System.out.println("");
		
		path = prob1.depthFirstPathCheckingSearch(MAXDEPTH);
		System.out.println("dfs path checking path length:" + path.size() + " " + path);
		prob1.printStats();
		System.out.println("--------");

		// set start point and goal for each robot
		
		/*
		//maze1.txt 3 * 3
		int[][] start = {{0, 2}, {0, 1}};
		int[][] goal = {{2, 0}, {2, 1}};
		*/
		
		/*
		//maze2.txt 2 * 10
		int[][] start = {{0, 0}, {1, 0}, {2, 0}};
		int[][] goal = {{9, 0}, {8, 0}, {7, 0}};
		*/
		
		/*
		// maze3.txt 7 * 7
		int[][] start = {{0, 0}, {1, 6}, {4, 5}};
		int[][] goal = {{6, 1}, {6, 2}, {6, 0}};
	*/
		
		//maze4.txt 20 * 20
		int[][] start = {{0, 0}, {0, 1}, {0, 2}};
		int[][] goal = {{19, 2}, {19, 1}, {19, 0}};
	
		
		/*
		//maze5.txt 40 * 40
		int[][] start = {{0, 0}, {0, 1}, {0, 2}};
		int[][] goal = {{39, 2}, {39, 1}, {39, 0}};
		*/
		
		System.out.println("Multi-Robot Problem");
		MultiRobotProblem prob2 = new MultiRobotProblem(maze, start, goal);
		/*
		path = prob2.breadthFirstSearch();
		System.out.println("bfs path length:  " + path.size() + " " + path);
		prob2.printStats();
		System.out.println("");*/
		
		path = prob2.AStarSearch();	
		System.out.println("A* search path length:  " + path.size() + " " + path);
		prob2.printStats();
		System.out.println("--------");
		
		// blind robot problem
		System.out.println("Blind Robot Problem");
		BlindRobotProblem prob3 = new BlindRobotProblem(maze, maze.cols - 1, 0);

		path = prob3.AStarSearch();	
		System.out.println("A* search path length:  " + path.size() + " " + path);
		prob3.printStats();
		System.out.println("--------");
	}
}
