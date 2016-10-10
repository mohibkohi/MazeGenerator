
/*
 * Peterson Phe
 * Mohib Kohi
 * Assignment 5 - Maze Generator
 * TCSS 342 - Spring 2016
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;
import javax.swing.JFrame;

public class Maze extends JFrame {
	private static final long serialVersionUID = -1788469750638998160L;
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private static final Color INIT_COLOR = Color.GRAY;
	private static final Color VISITED_COLOR = Color.DARK_GRAY;

	private static enum Dir {
		NORTH, EAST, SOUTH, WEST
	}; // 0, 1, 2, 3

	private static final Dir[] DIR_VALUES = Dir.values();
	private static final int DIR_SIZE = Dir.values().length;
	
	private MazePanel myPanel;
	private Cell grid[][];
	private int myWidth;
	private int myDepth;
	private boolean myDebug;
	private Cell myStart;
	private Cell myEnd;
	private Stack<Cell> mySolution;
	private Random myRand;
	PrintStream out;
	
	public Maze(int width, int depth, boolean debug) {
		myWidth = width;
		myDepth = depth;
		myDebug = debug;
		mySolution = new Stack<>();
		myRand = new Random();
		grid = new Cell[myWidth][myDepth];
		
		//set up GUI components for GUI maze.
		setupGUI();

		//file = new File("Solution.txt");
		for (int r = 0; r < myWidth; r++) {
			for (int c = 0; c < myDepth; c++) {
				grid[r][c] = new Cell(r, c);
			}
		}

		// set src and dest for algorithm
		myStart = grid[0][0];
		myStart.northWall = false;
		myEnd = grid[myWidth - 1][myDepth - 1];
		myEnd.southWall = false;

		generateMaze();
		// printCells();
	}

	// prints each cell's state
	private void printCells() {
		for (int r = 0; r < myWidth; r++) {
			for (int c = 0; c < myWidth; c++) {
				System.out.println(grid[r][c].toString());
			}
		}
	}

	// print out the maze
	public void display() {
		// display simple version of maze for debugging
		// for (int r = 0; r < myWidth; r++)
		// {
		// for (int c = 0; c < myDepth; c++)
		// {
		// Cell curr = grid[r][c];
		// String path = "V ";
		// if (curr.isSolution)
		// path = "+ ";
		// System.out.print(curr.visited ? path : "X ");
		// }
		// System.out.println();
		// }
		// System.out.println();

		// display maze with walls
		for (int r = 0; r < myWidth; r++) {
			// top wall
			for (int c = 0; c < myDepth; c++) {
				if (grid[r][c].northWall) {
					System.out.print("+---");
				}
				else {
					System.out.print("+   ");
				}
			}

			System.out.println("+");

			// west wall
			for (int c = 0; c < myWidth; c++) {
				if (grid[r][c].westWall) {
					System.out.print(grid[r][c].isSolution ? "| S " : "|   ");
					
				}
					
				else {
					System.out.print(grid[r][c].isSolution ? "  S " : "    ");
				}
			}
			System.out.println("|");
		}

		// bottom wall
		for (int c = 0; c < myWidth; c++) {
			if (grid[myWidth - 1][c].southWall) {
				System.out.print("+---");
			}
			else {
				System.out.println("+   +\n");
			}
		}
		//Update the drawing panel with new grid.
		myPanel.draw(grid);
	}

	// mark Cells in solutions stack and print
	public void solveMaze() {
		System.out.println("\nSolution:");
		while (!mySolution.isEmpty())
			mySolution.pop().isSolution = true;
		
		display();
	}

	// non-recursive DFS algorithm for generating the maze
	private void generateMaze() {
		Stack<Cell> path = new Stack<>();
		mySolution = new Stack<>();

		path.push(myStart);
		mySolution.push(myStart);
		myStart.visited = true;
		boolean goalFound = false;
		//Timer time = new Timer();
		
		while (!path.empty()) {
			// check for neighbors of the current top
			Cell curr = path.peek();
			HashMap<Dir, Cell> neighbors = getNeighbors(curr);

			// pick a random neighbor if available
			if (neighbors.size() > 0) {
				Integer dir = myRand.nextInt(DIR_SIZE);
				while (neighbors.get(DIR_VALUES[dir]) == null)
					dir = myRand.nextInt(DIR_SIZE);

				Cell next = neighbors.get(DIR_VALUES[dir]);

				if (!next.visited) // undiscovered cell->mark and continue
				{
					path.push(next);
					next.visited = true;
					carve(curr, next, dir);

					if (!goalFound) // haven't found dest, keep digging for
									// solution
						mySolution.push(next);

					if (next == myEnd) // set found flag - can stop pushing
						goalFound = true;
				}
			} else // else backtrack
			{
				path.pop();

				if (!goalFound) // pop if dest not found to keep stacks parallel
					mySolution.pop();
			}

			if (myDebug) {
								
				display();
			}
		}
	}

	// carve the wall
	private void carve(Cell theCurrent, Cell theNeighbor, Integer theDirection) {
		Dir direction = DIR_VALUES[theDirection];

		switch (direction) {
		case NORTH:
			theCurrent.northWall = false;
			theNeighbor.southWall = false;
			break;

		case EAST:
			theCurrent.eastWall = false;
			theNeighbor.westWall = false;
			break;

		case SOUTH:
			theCurrent.southWall = false;
			theNeighbor.northWall = false;
			break;
		case WEST:
			theCurrent.westWall = false;
			theNeighbor.eastWall = false;
			break;

		default:
			System.out.println("Inside default case for direction: " + theDirection);
			break;
		}
	}

	// gets a random neighbor cell
	private HashMap<Dir, Cell> getNeighbors(Cell theCell) {
		HashMap<Dir, Cell> adjacent = new HashMap<>();

		// traverse for adjacent cells
		// will continue looping without a goto break label
		for (int r = 0; r < myWidth; r++) {
			for (int c = 0; c < myDepth; c++) {
				// found the cell -> add valid neighbors
				if (grid[r][c] == theCell) {
					if (c > 0 && !grid[r][c - 1].visited) // left
						adjacent.put(Dir.WEST, grid[r][c - 1]);

					if (c < myWidth - 1 && !grid[r][c + 1].visited) // right
						adjacent.put(Dir.EAST, grid[r][c + 1]);

					if (r > 0 && !grid[r - 1][c].visited) // top
						adjacent.put(Dir.NORTH, grid[r - 1][c]);

					if (r < myDepth - 1 && !grid[r + 1][c].visited) // bottom
						adjacent.put(Dir.SOUTH, grid[r + 1][c]);
				}
			}
		}

		return adjacent;
	}

	// sets up the GUI for displaying the maze
	private void setupGUI() {
		// panel settings
		myPanel = new MazePanel(WIDTH, HEIGHT, myWidth, myDepth);
		myPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		myPanel.setBackground(Color.WHITE);
		add(myPanel, BorderLayout.CENTER);
		pack();

		// frame settings
		setTitle("Maze Generator!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
		setResizable(false);
		setContentPane(myPanel);

		setVisible(true);
	}

	
}
