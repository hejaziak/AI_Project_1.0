package MainPackage;

import java.util.Comparator;

public class State {

	public int i;
	public int j;
	public String orientation;
	public int whiteWalkersLeft;
	public int dragonGlass;
	public Position[] whiteWalkersPositions;

	// initial constructor, TODO: add orientation ?
	public State(int i, int j, int whiteWalkersLeft, Position[] whiteWalkersPositions) {
		this.whiteWalkersLeft = whiteWalkersLeft;
		this.i = i;
		this.j = j;
		this.dragonGlass = 0;
		this.orientation = "L";
		this.whiteWalkersPositions = whiteWalkersPositions;
	}

	public State(int i, int j, String orientation, int whiteWalkersLeft, int dragonGlass,
			Position[] whiteWalkersPositions) {
		// TODO Auto-generated constructor stub
		this.i = i;
		this.j = j;
		this.orientation = orientation;
		this.whiteWalkersLeft = whiteWalkersLeft;
		this.dragonGlass = dragonGlass;
		this.whiteWalkersPositions = whiteWalkersPositions;
	}

}

class Position implements Comparable<Position> {
	int x;
	int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		String result = String.format("X: %d , Y: %d\n", x, y);
		return result;
	}

	@Override
	public int compareTo(Position p) {
		if (this.x > p.x)
			return 1;
		else if (this.x < p.x)
			return -11;
		else if (this.y > p.y)
			return 1;
		else if (this.y < p.y)
			return -1;
		else
			return 0;
	}
}
