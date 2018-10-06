package MainPackage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class SaveWestros extends GenericSearch {

	int dragonGlassCapacity; // TODO: To change later

	public SaveWestros(int dragonGlassCapacity) {
		this.dragonGlassCapacity = dragonGlassCapacity;
	}

	public ArrayList<Node> expand(Node node, String[][] grid) {
		ArrayList<Node> results = new ArrayList<Node>();
		Node node1 = new Node(node, Operators.MOVE_FORWARD, node.depth + 1, node.pathCost + 1, null);
		Node node2 = new Node(node, Operators.ROTATE_LEFT, node.depth + 1, node.pathCost + 2, null);
		Node node3 = new Node(node, Operators.ROTATE_RIGHT, node.depth + 1, node.pathCost + 2, null);
		Node node4 = new Node(node, Operators.USE_DRAGON_GLASS, node.depth + 1, node.pathCost + 3, null);

		State state = node.state;
		if (state.orientation.compareTo("L") == 0) {
			// Move Forward Command
			if (noObstacles(grid, state.whiteWalkersPositions, state.i, state.j - 1)) {

				int dragonGlass = checkDragonStone(state.i, state.j - 1, grid, state.dragonGlass);

				node1.state = new State(state.i, state.j - 1, "L", state.whiteWalkersLeft, dragonGlass,
						state.whiteWalkersPositions);
				results.add(node1);
			}

			// Rotate Left Command

			node2.state = new State(state.i, state.j, "D", state.whiteWalkersLeft, state.dragonGlass,
					state.whiteWalkersPositions);
			results.add(node2);

			// Rotate Right Command

			node3.state = new State(state.i, state.j, "U", state.whiteWalkersLeft, state.dragonGlass,
					state.whiteWalkersPositions);
			results.add(node3);

			if (state.dragonGlass > 0) {
				Position[] newPositions = killWhiteWalkers(state.whiteWalkersPositions, state.i, state.j);
				node4.state = new State(state.i, state.j, "L", newPositions.length, state.dragonGlass - 1,
						newPositions);
				results.add(node4);
			}
		}
		if (state.orientation.compareTo("D") == 0) {

			// Move Forward Command

			if (noObstacles(grid, state.whiteWalkersPositions, state.i + 1, state.j)) {
				int dragonGlass = checkDragonStone(state.i + 1, state.j, grid, state.dragonGlass);
				node1.state = new State(state.i + 1, state.j, "D", state.whiteWalkersLeft, dragonGlass,
						state.whiteWalkersPositions);
				results.add(node1);
			}

			// Rotate Left Command

			node2.state = new State(state.i, state.j, "R", state.whiteWalkersLeft, state.dragonGlass,
					state.whiteWalkersPositions);
			results.add(node2);

			// Rotate Right Command

			node3.state = new State(state.i, state.j, "L", state.whiteWalkersLeft, state.dragonGlass,
					state.whiteWalkersPositions);
			results.add(node3);

			// Use DragonGlass Command

			if (state.dragonGlass > 0) {
				Position[] newPositions = killWhiteWalkers(state.whiteWalkersPositions, state.i, state.j);
				node4.state = new State(state.i, state.j, "D", newPositions.length, state.dragonGlass - 1,
						newPositions);
				results.add(node4);
			}
		}
		if (state.orientation.compareTo("R") == 0) {

			// Move Forward Command

			if (noObstacles(grid, state.whiteWalkersPositions, state.i, state.j + 1)) {
				int dragonGlass = checkDragonStone(state.i, state.j + 1, grid, state.dragonGlass);
				node1.state = new State(state.i, state.j + 1, "R", state.whiteWalkersLeft, dragonGlass,
						state.whiteWalkersPositions);
				results.add(node1);
			}

			// Rotate Left Command

			node2.state = new State(state.i, state.j, "U", state.whiteWalkersLeft, state.dragonGlass,
					state.whiteWalkersPositions);
			results.add(node2);

			// Rotate Right Command

			node3.state = new State(state.i, state.j, "D", state.whiteWalkersLeft, state.dragonGlass,
					state.whiteWalkersPositions);
			results.add(node3);

			// Use DragonGlass Command

			if (state.dragonGlass > 0) {
				Position[] newPositions = killWhiteWalkers(state.whiteWalkersPositions, state.i, state.j);
				node4.state = new State(state.i, state.j, "R", newPositions.length, state.dragonGlass - 1,
						newPositions);
				results.add(node4);
			}
		}
		if (state.orientation.compareTo("U") == 0) {

			// Move Forward Command

			if (noObstacles(grid, state.whiteWalkersPositions, state.i - 1, state.j)) {
				int dragonGlass = checkDragonStone(state.i - 1, state.j, grid, state.dragonGlass);
				node1.state = new State(state.i - 1, state.j, "U", state.whiteWalkersLeft, dragonGlass,
						state.whiteWalkersPositions);
				results.add(node1);
			}

			// Rotate Left Command

			node2.state = new State(state.i, state.j, "L", state.whiteWalkersLeft, state.dragonGlass,
					state.whiteWalkersPositions);
			results.add(node2);

			// Rotate Right Command

			node3.state = new State(state.i, state.j, "R", state.whiteWalkersLeft, state.dragonGlass,
					state.whiteWalkersPositions);
			results.add(node3);

			// Use DragonGlass Command

			if (state.dragonGlass > 0) {
				Position[] newPositions = killWhiteWalkers(state.whiteWalkersPositions, state.i, state.j);
				node4.state = new State(state.i, state.j, "U", newPositions.length, state.dragonGlass - 1,
						newPositions);
				results.add(node4);
			}
		}
		return results;

	}

	@Override
	public boolean goalTest(State state) {

		super.goalTest(state);

		if (state.whiteWalkersLeft == 0)
			return true;

		return false;

	}

	public Position[] getWhiteWalkersPositions(String[][] grid, int numOfWalkers) {
		Position[] positions = new Position[numOfWalkers];
		int k = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].compareTo("W") == 0) {
					positions[k] = new Position(i, j);
					k++;
				}

			}
		}
		return positions;
	}

	@Override
	public int pathCost(Node node) {
		super.pathCost(node);
		int cost = node.parentNode.pathCost;
		if (node.operator == Operators.USE_DRAGON_GLASS) {
			cost += 1;
		}
		return cost;
	}

	// TODO
	public static void search() {

	}

	private boolean noObstacles(String[][] grid, Position[] positions, int i, int j) {
		if (!checkPosition(i, j, grid))
			return false;
		for (Position pos : positions) {
			if (pos.x == i && pos.y == j)
				return false;
		}
		if (grid[i][j].compareTo("O") == 0)
			return false;

		return true;

	}

	private int checkDragonStone(int i, int j, String[][] grid, int dragonGlass) {
		if (grid[i][j] == "D") {
			return dragonGlassCapacity;
		}
		else
			return dragonGlass;

	}

	private Position[] killWhiteWalkers(Position[] whiteWalkersLeft, int i, int j) {
		ArrayList<Position> temp = new ArrayList<Position>();
		for (Position whiteWalker : whiteWalkersLeft) {
			int x = whiteWalker.x;
			int y = whiteWalker.y;

			if (!((x - 1 == i && y == j) || (x + 1 == i && y == j) || (x == i && y == j + 1) || (x == i && y == j - 1)))
				temp.add(whiteWalker);

		}
		Position[] results = new Position[temp.size()];
	
		results = temp.toArray(results);

		return results;

	}

	private boolean checkPosition(int i, int j, String[][] grid) {
		int n = grid.length;
		int m = grid[0].length;

		return (i >= 0) && (i < n) && (j >= 0) && (j < m);
	}

	/**
	 * Strategy should return: - Representation of the sequence of moves - The cost
	 * - The number of chosen nodes
	 */

	public static void BFS(String[][] grid, Node node) {
		// TODO
	}

	public static void DFS(String[][] grid, Node node) {
		// TODO
	}

	public static void ID(String[][] grid, Node node) {
		// TODO
	}

	public Node UC(String[][] grid, int whiteWalkersLeft) {
		// TODO
		// Initalize the Queue with the initial state and loop over the head of the
		// queue
		Comparator<Node> NodeComparator = new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				if (n1.pathCost < n2.pathCost)
					return -1;
				else if (n1.pathCost > n2.pathCost)
					return 1;
				else
					return 1;

			}
		};
		PriorityQueue<Node> queue = new PriorityQueue<>();
		Position[] whiteWalkersPositions = getWhiteWalkersPositions(grid, whiteWalkersLeft);
		State initialState = new State(grid.length - 1, grid[0].length - 1, whiteWalkersLeft, whiteWalkersPositions);
		Node InitialNode = new Node(initialState);
		queue.add(InitialNode);
		while (true) {
			if (queue.isEmpty())
				return null;

			Node node = queue.remove();
			if (goalTest(node.state))
				return node;
			queue = UCExpand(node, queue, grid);

		}
	}

	private PriorityQueue<Node> UCExpand(Node node, PriorityQueue<Node> queue, String[][] grid) {
		ArrayList<Node> nodes = expand(node, grid);
		for (Node n : nodes) {
			queue.add(n);
			}

		return queue;
	}

	private void UCPrintQueue(PriorityQueue<Node> queue) {
		PriorityQueue<Node> copy = new PriorityQueue<Node>(queue);
		while (!copy.isEmpty())
			System.out.print(copy.remove().operator + "  ");
		
		System.out.println();
	}

	public static void GR1(String[][] grid, Node node) {
		// TODO
	}

	public static void GR2(String[][] grid, Node node) {
		// TODO
	}

	public static void AS1(String[][] grid, Node node) {
		// TODO
	}

	public static void AS2(String[][] grid, Node node) {
		// TODO
	}

}
