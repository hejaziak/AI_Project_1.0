package MainPackage;

import java.util.ArrayList;


public class SaveWestros extends GenericSearch {

	public int dragonGlassCapacity;
	public int numberOfWhiteWalkers;
	public String[][] grid;

	public SaveWestros(int dragonGlassCapacity, int numberOfWhiteWalkers, String[][] grid) {
		this.dragonGlassCapacity = dragonGlassCapacity;

		this.dragonGlassCapacity = dragonGlassCapacity;
		this.numberOfWhiteWalkers = numberOfWhiteWalkers;
		this.grid = grid;
		this.initialNode = configureInitialNode();
	}

	public Node configureInitialNode() {
		Position[] whiteWalkersPositions = getWhiteWalkersPositions(grid, numberOfWhiteWalkers);
		State initialState = new State(grid.length - 1, grid[0].length - 1, numberOfWhiteWalkers,
				whiteWalkersPositions);
		Node InitialNode = new Node(initialState, 0);
		return InitialNode;

	}

	public ArrayList<Node> expand(Node node) {
		return expand(node, 1);

	}

	public ArrayList<Node> expand(Node node, int heuristic) {

		ArrayList<Node> results = new ArrayList<Node>();
		Node node1 = new Node(node, Operators.MOVE_FORWARD, node.depth + 1, node.pathCost + 2, null, 0);
		Node node2 = new Node(node, Operators.ROTATE_LEFT, node.depth + 1, node.pathCost + 2, null, 0);
		Node node3 = new Node(node, Operators.ROTATE_RIGHT, node.depth + 1, node.pathCost + 2, null, 0);
		Node node4 = new Node(node, Operators.USE_DRAGON_GLASS, node.depth + 1, node.pathCost + 20, null, 0);

		State state = node.state;
		int forwardI = 0;
		int forwardJ = 0;
		String leftOrient = "";
		String rightOrient = "";
		switch (state.orientation) {
		case "L":
			forwardI = state.i;
			forwardJ = state.j - 1;
			leftOrient = "D";
			rightOrient = "U";
			break;
		case "D":
			forwardI = state.i + 1;
			forwardJ = state.j;
			leftOrient = "R";
			rightOrient = "L";
			break;
		case "R":
			forwardI = state.i;
			forwardJ = state.j + 1;
			leftOrient = "U";
			rightOrient = "D";
			break;
		case "U":
			forwardI = state.i - 1;
			forwardJ = state.j;
			leftOrient = "L";
			rightOrient = "R";
			break;
		}

		// Move Forward Command
		if (checkObstacles(grid, state.whiteWalkersPositions, forwardI, forwardJ)) {
			int dragonGlass = checkDragonStone(forwardI, forwardJ, grid, state.dragonGlass);
			node1.state = new State(forwardI, forwardJ, state.orientation, state.whiteWalkersLeft, dragonGlass,
					state.whiteWalkersPositions);
			results.add(node1);
		}

		// Rotate Left Command
		node2.state = new State(state.i, state.j, leftOrient, state.whiteWalkersLeft, state.dragonGlass,
				state.whiteWalkersPositions);
		results.add(node2);

		// Rotate Right Command
		node3.state = new State(state.i, state.j, rightOrient, state.whiteWalkersLeft, state.dragonGlass,
				state.whiteWalkersPositions);
		results.add(node3);

		// Check If the agent still have any dragon glass
		if (state.dragonGlass > 0) {
			Position[] newPositions = killWhiteWalkers(state.whiteWalkersPositions, state.i, state.j);
			node4.state = new State(state.i, state.j, state.orientation, newPositions.length, state.dragonGlass - 1,
					newPositions);
			results.add(node4);
		}
		if (heuristic == 1)
			results = heuristicFunction1(results);
		else
			results = heuristicFunction2(results);
		return results;
	}

	public ArrayList<Node> heuristicFunction1(ArrayList<Node> nodes) {
		for (Node node : nodes) {
			Position[] whiteWalkersLeft = node.state.whiteWalkersPositions;
			int i = node.state.i;
			int j = node.state.j;
			int max = -1;
			if (whiteWalkersLeft.length == 0) {
				node.heuristicCost = 0;
				continue;
			}
			for (Position whiteWalker : whiteWalkersLeft) {
				max = (int) Math.max(max,
						Math.sqrt(Math.pow((i - whiteWalker.x), 2) + Math.pow((j - whiteWalker.y), 2)));
			}
			node.heuristicCost = max;

		}
		return nodes;
	}

	public ArrayList<Node> heuristicFunction2(ArrayList<Node> nodes) {
		for (Node node : nodes) {
			Position[] whiteWalkersLeft = node.state.whiteWalkersPositions;
			int heuristic = whiteWalkersLeft.length / 3;
			node.heuristicCost = heuristic;
		}
		return nodes;
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

	// Check if the position required contains any white walkers or obstacles.
	// If there is neither the agent is aloud to pass.
	private boolean checkObstacles(String[][] grid, Position[] whiteWalkers, int i, int j) {
		if (!checkPosition(i, j, grid))
			return false;
		for (Position pos : whiteWalkers) {
			if (pos.x == i && pos.y == j)
				return false;
		}
		if (grid[i][j].compareTo("O") == 0)
			return false;

		return true;

	}

	// Check if current Cell contains a dragon stone so we can refill the agent's
	// dragon glass.
	private int checkDragonStone(int i, int j, String[][] grid, int dragonGlass) {
		if (grid[i][j] == "D") {
			return dragonGlassCapacity;
		} else
			return dragonGlass;

	}

	// Check if there is any whitewalkers in the cells neighbouring the agent when
	// the dragon glass is used.
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

	// Check if the position the agent wants to walk to is a valid position inside
	// the grid.
	private boolean checkPosition(int i, int j, String[][] grid) {
		int n = grid.length;
		int m = grid[0].length;

		return (i >= 0) && (i < n) && (j >= 0) && (j < m);
	}

}
