
/*
 * Peterson Phe
 * Mohib Kohi
 * Assignment 5 - Maze Generator
 * TCSS 342 - Spring 2016
 */

public class Cell {
	// cells / vertices

	int x;
	int y;
	boolean visited = false;
	boolean northWall = true;
	boolean eastWall = true;
	boolean southWall = true;
	boolean westWall = true;
	boolean isSolution = false;

	Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "(" + x + ", " + y + "), " + "Visited: " + visited + ", isSolution: " + isSolution + ", northWall: "
				+ northWall + ", eastWall: " + eastWall + ", southWall: " + southWall + ", westWall: " + westWall;
	}

}
