package MainPackage;

public class Node  implements Comparable<Node>{

	public Node parentNode;
	public Operators operator;
	public int depth;
	public int pathCost;
	public State state;

	public Node(State state) {
	 this.state=state;
	 parentNode = null ;
	 operator = null; //TODO:Add initial operator ?
	 depth=0;
	 pathCost = 0 ;
	}

	public Node(Node parentNode, Operators operator, int depth, int pathCost, State state) {
		this.parentNode = parentNode;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
		this.state = state;
	}

	@Override
	public int compareTo(Node n) {
		if(pathCost==n.pathCost)
			return 0 ;
		else if (pathCost>n.pathCost)
			return 1;
		else return -1;
	}

}
