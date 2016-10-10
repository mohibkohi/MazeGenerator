
/*
 * Peterson Phe
 * Mohib Kohi
 * Assignment 5 - Maze Generator
 * TCSS 342 - Spring 2016
 */

import java.awt.EventQueue;
import java.io.FileNotFoundException;

public class Main {
	private static final int WIDTH = 5;
	private static final int DEPTH = 5;
	private static final boolean DEBUG = true;

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// 5x5 maze per requirements
				Maze test = new Maze(WIDTH, DEPTH, DEBUG);
				//Only showing the maze. 
				test.display();
				//Show the maze with its solution.
				test.solveMaze();

//				 generate mazes of different sizes
//				 Maze test2 = new Maze(WIDTH+1, WIDTH+1, DEBUG);
//				 test2.display();
//				 test2.solveMaze();
//				
//				 Maze test3 = new Maze(WIDTH+2, WIDTH+2, DEBUG);
//				 test3.display();
//				 test3.solveMaze();
			}
		});
	}
}
