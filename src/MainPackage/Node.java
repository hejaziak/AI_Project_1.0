package MainPackage;

public class Node {

	public Node parentNode;
	public Operators operator;
	public int depth;
	public int pathCost;
	public State state;
	public int heuristicCost;

	public Node(State state,int heuristicCost) {
	 this.state=state;
	 this.heuristicCost = heuristicCost;
	 this.parentNode = null ;
	 this.operator = null; //TODO:Add initial operator ?
	 this.depth=0;
	 this.pathCost = 0 ;
	 
	 
	}

	public Node(Node parentNode, Operators operator, int depth, int pathCost, State state,int heuristicCost) {
		this.parentNode = parentNode;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
		this.state = state;
		this.heuristicCost = heuristicCost;
		
	}


}
