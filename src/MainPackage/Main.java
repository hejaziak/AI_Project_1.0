package MainPackage;

public class Main {
	
	public static String[][] GenGrid(){
		int m = ((int) (Math.random()*6)) + 4;
		int n = ((int) (Math.random()*6)) + 4;
		int size = m*n;
				
		int maxWhiteWalkers = (int) (0.25 * size);
		int maxObstacles = (int) (0.25 * size);
		
		
		int whiteWalkers = (int) (Math.random()*maxWhiteWalkers) + 1;
		int obstacles = (int) (Math.random()*maxObstacles) + 1;
		int dragonGlassCapacity = (int) (Math.random()*whiteWalkers) + 1;
		
		System.out.println("White walkers: " + whiteWalkers);
		System.out.println("Obstacles: " + obstacles);
		System.out.println("Capacity: " + dragonGlassCapacity );
		
		System.out.println();
		
	
		
		String [][] grid = new String[m][n];
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j] = "~";
			}
		}
		
		grid[m-1][n-1] = "J"; 
		placeElements(grid,"W",whiteWalkers);
		placeElements(grid,"O",obstacles);
		placeElements(grid,"D",1);
		
		
		
		return grid;
		
	}
	
	public static void printGrid(String [][] grid) {
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
	
	public static String[][] placeElements(String[][] grid, String type, int max){
		int m = grid.length;
		int n = grid[0].length;
		
		for (int i = 0; i < max ; i++) {
			int randI = (int) (Math.random()*m);
			int randJ = (int) (Math.random()*n);
			
			while(!validPosition(randI,randJ,grid)) {
				
				randI = (int) (Math.random()*m);
				randJ = (int) (Math.random()*n);
			}
			
			grid[randI][randJ] = type;
			
		}
		
		return grid;
		
	}
	

	
	public static boolean  validPosition(int i, int j , String[][] grid) {
		if (grid[i][j].compareTo("~") == 0)
			return true;
		
		return false;
	}
	
	
	
	public static void main(String[] args) {
		String[][] grid = GenGrid();
		printGrid(grid);
		
	}

}
