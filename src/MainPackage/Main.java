package MainPackage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeMap;

public class Main {
	public static int dragonGlassCapacity;
	public static int numberOfWhiteWalkers;
	public static Position dragonGlassLoc ;
	public static TreeMap<Node, Integer> map;

	public static String[][] GenGrid() {
		int m = ((int) (Math.random() * 6)) + 4;
		int n = ((int) (Math.random() * 6)) + 4;
		int size = m * n;

		int maxWhiteWalkers = (int) (0.25 * size);
		int maxObstacles = (int) (0.25 * size);
		
		 int obstacles = (int) (Math.random() * maxObstacles) + 1;
		 numberOfWhiteWalkers = (int) (Math.random() * maxWhiteWalkers) + 1;
		 dragonGlassCapacity = (int) (Math.random() * numberOfWhiteWalkers) + 1;

		String[][] grid = new String[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j] = "~";
			}
		}

		grid[m - 1][n - 1] = "<J";
		placeElements(grid, "W", numberOfWhiteWalkers);
		placeElements(grid, "O", obstacles);
		placeElements(grid, "D", 1);

		return grid;

	}

	public static void printGrid(String[][] grid) {

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}

	public static String[][] placeElements(String[][] grid, String type, int max) {
		int m = grid.length;
		int n = grid[0].length;

		for (int i = 0; i < max; i++) {
			int randI = (int) (Math.random() * m);
			int randJ = (int) (Math.random() * n);

			while (!validPosition(randI, randJ, grid)) {

				randI = (int) (Math.random() * m);
				randJ = (int) (Math.random() * n);
			}

			grid[randI][randJ] = type;
			if (type == "D") {
				dragonGlassLoc = new Position(randI,randJ);
			}
		}

		return grid;

	}

	public static boolean validPosition(int i, int j, String[][] grid) {
		if (grid[i][j].compareTo("~") == 0)
			return true;

		return false;
	}

	public static String[][] TestGrid() {
		String[][] grid = { { "~", "O", "~", "~", "~" },
				{ "O", "W", "~", "~", "~" }, 
				{ "O", "~", "W", "W", "W" },
				{ "~", "~", "O", "~", "D" }, 
				{ "W", "W", "~", "W", "<J" }, };
		dragonGlassCapacity = 3;
		numberOfWhiteWalkers = 7;
		dragonGlassLoc = new Position(3,4);
		return grid;
	}


	public static Node UC(GenericSearch problem) {
		Comparator<Node> pathCostComparator = new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				if (n1.pathCost == n2.pathCost)
					return 0;
				else if (n1.pathCost > n2.pathCost)
					return 1;
				else
					return -1;
			}

		};
		PriorityQueue<Node> queue = new PriorityQueue<>(pathCostComparator);
		queue.add(problem.initialNode);

		while (true) {
			if (queue.isEmpty())
				return null;
			Node node = queue.remove();
			if (problem.goalTest(node.state))
				return node;
			queue = UCExpand(node, queue, problem);
		}
	}

	// An intermediate function to call the expand function and add it's results to
	// the priority queue.
	private static PriorityQueue<Node> UCExpand(Node node, PriorityQueue<Node> queue, GenericSearch problem) {
		ArrayList<Node> nodes = problem.expand(node);
		for (Node n : nodes) {
			if (!map.containsKey(n)) {
				map.put(n, 1);
				queue.add(n);
			}
		}

		return queue;
	}

	public static Node BFS(GenericSearch problem) {

		LinkedList<Node> queue = new LinkedList<>();

		queue.add(problem.initialNode);
		map.put(problem.initialNode, 1);

		while (!queue.isEmpty()) {
			Node current = queue.remove();
			if (problem.goalTest(current.state)) {
				return current;
			} else {
				for (Node child : problem.expand(current)) {
					if (!map.containsKey(child)) {
						map.put(child, 1);
						queue.add(child);

					}

				}
			}
		}
		return null;

	}

	public static Node DFS(GenericSearch problem) {

		Stack<Node> stack = new Stack<>();

		stack.add(problem.initialNode);
		map.put(problem.initialNode, 1);

		while (!stack.isEmpty()) {
			Node current = stack.pop();

			if (problem.goalTest(current.state)) {
				return current;
			} else {
				for (Node child : problem.expand(current)) {
					if (!map.containsKey(child)) {
						map.put(child, 1);
						stack.push(child);

					}

				}
			}
		}
		return null;

	}

	public static Node IDS(GenericSearch problem) {

		map.put(problem.initialNode, 1);

		// loops through until a goal node is found
		for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {

			Node found = DLS(problem.initialNode, depth, problem);
			map.clear();
			if (found != null) {
				return found;
			}
		}
		// this will never be reached as it
		// loops forever until goal is found
		return null;
	}

	public static Node DLS(Node node, int depth, GenericSearch problem) {
		if (depth == 0 && problem.goalTest(node.state)) {
			return node;
		}

		ArrayList<Node> queue = new ArrayList<Node>();
		if (depth > 0) {

			for (Node child : problem.expand(node)) {
				if (!map.containsKey(child)) {
					map.put(child, 1);
					queue.add(child);
				}
			}

			for (Node child : queue) {
				Node found = DLS(child, depth - 1, problem);
				if (found != null) {
					return found;
				}
			}

		}
		return null;
	}

	public static Node GR1(GenericSearch problem) {
		return GR(problem, 1);

	}

	public static Node GR2(GenericSearch problem) {
		return GR(problem, 2);

	}

	public static Node GR(GenericSearch problem, int heuristic) {
		Comparator<Node> heuristicComparator = new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				if (n1.heuristicCost == n2.heuristicCost)
					return 0;
				else if (n1.heuristicCost > n2.heuristicCost)
					return 1;
				else
					return -1;
			}

		};
		PriorityQueue<Node> queue = new PriorityQueue<>(heuristicComparator);

		queue.add(problem.initialNode);
		while (true) {
			if (queue.isEmpty())
				return null;
			Node node = queue.remove();
			if (problem.goalTest(node.state))
				return node;
			ArrayList<Node> nodes = problem.expand(node);
			for (Node n : nodes) {
				if (!map.containsKey(n)) {
					map.put(n, 1);
					queue.add(n);
				}
			}
		}
	}

	public static Node AS1(GenericSearch problem) {

		return AS(problem, 1);
	}

	public static Node AS2(GenericSearch problem) {
		return AS(problem, 2);
	}

	public static Node AS(GenericSearch problem, int heuristic) {
		Comparator<Node> heuristicComparator = new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				if (n1.pathCost + n1.heuristicCost == n2.pathCost + n2.heuristicCost)
					return 0;
				else if (n1.pathCost + n1.heuristicCost > n2.pathCost + n2.heuristicCost)
					return 1;
				else
					return -1;
			}

		};
		PriorityQueue<Node> queue = new PriorityQueue<>(heuristicComparator);

		queue.add(problem.initialNode);
		while (true) {
			if (queue.isEmpty())
				return null;
			Node node = queue.remove();

			if (problem.goalTest(node.state))
				return node;
			ArrayList<Node> nodes = problem.expand(node, heuristic);
			for (Node n : nodes) {
				if (!map.containsKey(n)) {
					map.put(n, 1);
					queue.add(n);
				}
			}
		}
	}

	public static Node generalSearchProcedure(GenericSearch problem, String strategy) {
		switch (strategy) {
		case "BFS":
			return BFS(problem);

		case "DFS":
			return DFS(problem);

		case "IDS":
			return IDS(problem);

		case "UC":
			return UC(problem);
		case "GR1":
			return GR1(problem);

		case "GR2":
			return GR2(problem);

		case "AS1":
			return AS1(problem);

		case "AS2":
			return AS2(problem);

		default:
			return null;
		}

	}

	public static Position locateJohnSnow(String[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].length() > 1)
					return new Position(i, j);
			}

		}
		return null;
	}

	public static String[][] Visualize(String[][]grid,String action) {
		Position p = locateJohnSnow(grid);
		String[][]result = new String[grid.length][grid[0].length];

		switch(action) {
		case "USE_DRAGON_GLASS":result = killWalkers(grid,p.x,p.y);break;
		case "ROTATE_LEFT":result = rotate(grid,p.x,p.y,"left");break;
		case "ROTATE_RIGHT":result = rotate(grid,p.x,p.y,"right");break;
		case "MOVE_FORWARD":result = moveForward(grid,p.x,p.y);break;
		}
		if(grid[dragonGlassLoc.x][dragonGlassLoc.y].length()==1)
			grid[dragonGlassLoc.x][dragonGlassLoc.y]="D"; // incase john snow stepped on it in the previous step
		return result;
		

	}

	public static String[][] moveForward(String[][] grid, int i, int j) {
		String direction = grid[i][j].substring(0, 1);

		switch(direction) {
		case "v":grid[i+1][j]= grid[i][j];break;
		case "^":grid[i-1][j]=grid[i][j];break;
		case "<":grid[i][j-1]=grid[i][j];break;
		case ">":grid[i][j+1]=grid[i][j];break;
		}
		grid[i][j]="~";

		return grid;
	}

	public static String[][] killWalkers(String[][] grid, int i, int j) {
		if (checkPosition(i + 1, j, grid) && grid[i+1][j].compareTo("W")==0) 
			grid[i+1][j]="~";
		
		if (checkPosition(i - 1, j, grid) && grid[i-1][j].compareTo("W")==0) 
			grid[i-1][j]="~";
		
		if (checkPosition(i, j + 1, grid) && grid[i][j+1].compareTo("W")==0) 
			grid[i][j+1]="~";
		
		if (checkPosition(i, j - 1, grid) && grid[i][j-1].compareTo("W")==0) 
			grid[i][j-1]="~";
		
		return grid;
		
	}

	public static String[][]rotate(String[][] grid, int i, int j, String orientation) {
		String direction = grid[i][j].substring(0, 1);
		if((direction.compareTo("^")==0 && orientation.compareTo("left")==0)||(direction.compareTo("v")==0 && orientation.compareTo("right")==0) ) {
			grid[i][j]="<J";
		}
		if((direction.compareTo("<")==0 && orientation.compareTo("left")==0)||(direction.compareTo(">")==0 && orientation.compareTo("right")==0) ) {
			grid[i][j]="vJ";
		}
		if((direction.compareTo("v")==0 && orientation.compareTo("left")==0)||(direction.compareTo("^")==0 && orientation.compareTo("right")==0) ) {
			grid[i][j]=">J";
		}
		if((direction.compareTo(">")==0 && orientation.compareTo("left")==0)||(direction.compareTo("<")==0 && orientation.compareTo("right")==0) ) {
			grid[i][j]="^J";
		}
		return grid;
		
	}

	private static boolean checkPosition(int i, int j, String[][] grid) {
		int n = grid.length;
		int m = grid[0].length;

		return (i >= 0) && (i < n) && (j >= 0) && (j < m) && grid[i][j].compareTo("W")==0;
	}

	public static void search(String[][] grid, String strategy, boolean visualize) {
		// itt should create a new SearchProblem of type SaveWesteros and pass it to the
		// GeneralSearch method together with the input strategy.
		int counter = 1 ;
		SaveWestros problem = new SaveWestros(dragonGlassCapacity, numberOfWhiteWalkers, grid);
		Node node = generalSearchProcedure(problem, strategy);
		Stack<Operators> stack = new Stack<>();
		if(node==null) {
			System.out.println("No Solution Could Be Reached In This Grid !!!");
			return;
		}
		while (node.parentNode != null) {
			stack.add(node.operator);
			node = node.parentNode;

		}
		System.out.println();
		while (!stack.isEmpty()) {
			Operators action = stack.pop();
			System.out.print("Action "+counter+++": ");
			System.out.println(action);
		
			if(visualize) {
				grid=Visualize(grid, action.toString());
				printGrid(grid);
				System.out.println(new String(new char[40]).replace("\0", "_"));
				System.out.println();
			}
	
		}
		System.out.println("★★★★★★★★★★★ All The White Walkers Have Been Killed ★★★★★★★★★★★" );

	}

	public static void main(String[] args) {



		Comparator<Node> nodeComparator = new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				State state1 = n1.state;
				State state2 = n2.state;
				if (state1.i > state2.i)
					return 1;
				else if (state1.i < state2.i)
					return -1;
				else if (state1.j > state2.j)
					return 1;
				else if (state1.j < state2.j)
					return -1;
				else if (state1.orientation.compareTo(state2.orientation) > 0)
					return 1;
				else if (state1.orientation.compareTo(state2.orientation) < 0)
					return -1;
				else if (state1.whiteWalkersLeft > state2.whiteWalkersLeft)
					return 1;
				else if (state1.whiteWalkersLeft < state2.whiteWalkersLeft)
					return -1;
				else {
					loop: for (Position whiteWalker1 : state1.whiteWalkersPositions) {
						for (Position whiteWalker2 : state2.whiteWalkersPositions) {
							if (whiteWalker1.compareTo(whiteWalker2) == 0) {
								continue loop;
							}
						}
						return -1;
					}
					return 0;
				}
			}
		};
		map = new TreeMap<Node, Integer>(nodeComparator);
		String[][] grid = GenGrid();
		System.out.println("The problem is :\n");
		printGrid(grid);
		System.out.println(String.format("Number of white walkers: %d Capacity of dragon glass: %d", numberOfWhiteWalkers,dragonGlassCapacity));
		System.out.println(new String(new char[40]).replace("\0", "_"));


		search(grid, "DFS", true);

		System.out.println(new String(new char[40]).replace("\0", "_"));

	}

}
