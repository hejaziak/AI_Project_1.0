package MainPackage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeMap;

public class Main {
	public static int dragonGlassCapacity;
	public static int numberOfWhiteWalkers;

	public static TreeMap<Node, Integer> map;

	public static String[][] GenGrid() {
		int m = ((int) (Math.random() * 6)) + 4;
		int n = ((int) (Math.random() * 6)) + 4;
		int size = m * n;

		int maxWhiteWalkers = (int) (0.25 * size);
		int maxObstacles = (int) (0.25 * size);

		int whiteWalkers = (int) (Math.random() * maxWhiteWalkers) + 1;
		int obstacles = (int) (Math.random() * maxObstacles) + 1;
		int dragonGlassCapacity = (int) (Math.random() * whiteWalkers) + 1;

//		System.out.println("White walkers: " + whiteWalkers);
//		System.out.println("Obstacles: " + obstacles);
//		System.out.println("Capacity: " + dragonGlassCapacity);
//
//		System.out.println();

		String[][] grid = new String[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j] = "~";
			}
		}

		grid[m - 1][n - 1] = "J";
		placeElements(grid, "W", whiteWalkers);
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

		}

		return grid;

	}

	public static boolean validPosition(int i, int j, String[][] grid) {
		if (grid[i][j].compareTo("~") == 0)
			return true;

		return false;
	}

	public static String[][] TestGrid() {
		String[][] grid = { { "~", "O", "~", "~", "~" }, { "O", "W", "~", "~", "~" }, { "O", "~", "W", "W", "W" },
				{ "~", "~", "O", "~", "D" }, { "W", "W", "~", "W", "J" }, };
		dragonGlassCapacity = 3;
		numberOfWhiteWalkers = 7;
		return grid;
	}

//	public static Node test(SaveWestros problem) {
//		String[][] grid = TestGrid();
//		printGrid(grid);
//		
////		Node UCres = problem.UC(grid, 7); // The number of white walkers in the problem
////		Node IDSres = problem.IDS(grid,7);
//		
////		Node BFSres = problem.BFS(grid,7);
////		Node DFSres = problem.DFS(grid, 7); 
//
////		Node AS1res = problem.AS1(grid, 7); // The number of white walkers in the problem
////		Node GR1res = problem.GR1(grid, 7);
//		
////		Node AS2res = problem.AS2(grid, 7);
////		Node GR2res = problem.GR2(grid, 7);
//		
//		//return IDSres;
//
//
//	}

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

	public static void search(String[][] grid, String strategy, boolean visualize) {
		// itt should create a new SearchProblem of type SaveWesteros and pass it to the
		// GeneralSearch method together with the input strategy.

		SaveWestros problem = new SaveWestros(dragonGlassCapacity, numberOfWhiteWalkers, grid);

		Node node = generalSearchProcedure(problem, strategy);

		while (node.parentNode != null) {
			System.out.println(node.operator);
			node = node.parentNode;
		}

	}

	public static void main(String[] args) {

//		SaveWestros problem = new SaveWestros(2); // The number of the dragon glass capacity inserted in the problem
//
//		Node node = test(problem);
//		System.out.println(node);
//		while (node.parentNode != null) {
//		System.out.println(node.operator);
//		node = node.parentNode;
//	}

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

		search(TestGrid(), "DFS", true);

	}

}
