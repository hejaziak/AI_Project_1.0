package MainPackage;

public class SaveWestros extends GenericSearch{
	
	
	
	
	@Override
	public State[] stateSpace(State state, String[][] grid) {
		super.stateSpace(state,grid);
		State [] results = new State[4];
		
		
		// if state was facing left
		if(state.orientation.compareTo("L") == 0) {
			
			// Rotate Left Command
			results[0] = new State(state.i,state.j,"D",state.whiteWalkersLeft,state.dragonGlass);
			
			// Rotate Right Command
			results[1] = new State(state.i,state.j,"U",state.whiteWalkersLeft,state.dragonGlass);
			
			// Move Forward Command
			results[2] = new State(state.i-1,state.j,"L",state.whiteWalkersLeft,state.dragonGlass);
			
			// Use DragonGlass Command
			int whiteWalkersKilled = noKilledWhiteWalkers(state.i,state.j,grid);
			results[3] = new State(state.i,state.j,"L",state.whiteWalkersLeft - whiteWalkersKilled,
					state.dragonGlass);
			
		}
		
		//if state was facing down
		if(state.orientation.compareTo("D") == 0) {
			
			// Rotate Left Command
			results[0] = new State(state.i,state.j,"R",state.whiteWalkersLeft,state.dragonGlass);
			
			// Rotate Right Command
			results[1] = new State(state.i,state.j,"L",state.whiteWalkersLeft,state.dragonGlass);
			
			// Move Forward Command
			results[2] = new State(state.i,state.j+1,"D",state.whiteWalkersLeft,state.dragonGlass);
			
			// Use DragonGlass Command
			int whiteWalkersKilled = noKilledWhiteWalkers(state.i,state.j,grid);
			results[3] = new State(state.i,state.j,"D",state.whiteWalkersLeft - whiteWalkersKilled,
					state.dragonGlass);
		}
		
		//if state was facing right
		if(state.orientation.compareTo("R") == 0) {
			// Rotate Left Command
			results[0] = new State(state.i,state.j,"U",state.whiteWalkersLeft,state.dragonGlass);
			
			// Rotate Right Command
			results[1] = new State(state.i,state.j,"D",state.whiteWalkersLeft,state.dragonGlass);
			
			// Move Forward Command
			results[2] = new State(state.i+1,state.j,"R",state.whiteWalkersLeft,state.dragonGlass);
			
			// Use DragonGlass Command
			int whiteWalkersKilled = noKilledWhiteWalkers(state.i,state.j,grid);
			results[3] = new State(state.i,state.j,"R",state.whiteWalkersLeft - whiteWalkersKilled,
					state.dragonGlass);
		}	
		
		//if state was facing UP
		if(state.orientation.compareTo("U") == 0) {
			// Rotate Left Command
			results[0] = new State(state.i,state.j,"L",state.whiteWalkersLeft,state.dragonGlass);
			
			// Rotate Right Command
			results[1] = new State(state.i,state.j,"R",state.whiteWalkersLeft,state.dragonGlass);
			
			// Move Forward Command
			results[2] = new State(state.i,state.j-1,"U",state.whiteWalkersLeft,state.dragonGlass);
			
			// Use DragonGlass Command
			int whiteWalkersKilled = noKilledWhiteWalkers(state.i,state.j,grid);
			results[3] = new State(state.i,state.j,"U",state.whiteWalkersLeft - whiteWalkersKilled,
					state.dragonGlass);

		}
		
		return results;
			
	}


	@Override
	public boolean goalTest(State state) {
		
		super.goalTest(state);
		
		if( state.whiteWalkersLeft == 0)
			return true;
		
		return false;
		
	}

	@Override
	public int pathCost(Node node) {
		super.pathCost(node);
		int cost = node.parentNode.pathCost;
		if ( node.operator == Operators.USE_DRAGON_GLASS) {
			cost += 1;
		}
		return cost;
	}

	//TODO
	public static void search(){
		
	}
	
	private int noKilledWhiteWalkers(int i, int j, String[][] grid) {
		int counter = 0 ;
		// TODO check boundaries
		if(grid[i-1][j].compareTo("W") == 0)
			counter++;
		
		if(grid[i+1][j].compareTo("W") == 0)
			counter++;
		
		if(grid[i][j-1].compareTo("W") == 0)
			counter++;
		
		if(grid[i][j+1].compareTo("W") == 0)
			counter++;
		
		return counter;
	}
	
	/**
	 *  Strategy should return:
	 *  - Representation of the sequence of moves
	 *  - The cost
	 *  - The number of chosen nodes
	 */
	
	public static  void BFS(String[][] grid, Node node) {
		// TODO
	}
	
	public static  void DFS(String[][] grid, Node node) {
		// TODO
	}
	
	public static  void ID(String[][] grid, Node node) {
		// TODO
	}
	
	public static  void UC(String[][] grid, Node node) {
		// TODO
	}
	
	public static  void GR1(String[][] grid, Node node) {
		// TODO
	}
	
	public static  void GR2(String[][] grid, Node node) {
		// TODO
	}
	
	public static  void AS1(String[][] grid, Node node) {
		// TODO
	}
	
	public static  void AS2(String[][] grid, Node node) {
		// TODO
	}
	
	

}
