package MainPackage;

public abstract class GenericSearch {

	
	public Operators[] operators;
	public State initialState;
	
	// Returns set of space reachable from the input state.
	public State[] stateSpace(State state, String[][] grid) {
		return null;	
	}
	
	// Determines if it is a goal state
	public boolean goalTest(State state) {
		return false;
	}
	
	// Assigns cost to sequence of actions.
	public int pathCost(Node node) {
		return 0;
	}
	
}
