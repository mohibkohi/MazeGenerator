
/*
 * Peterson Phe
 * Mohib Kohi
 * Assignment 5 - Maze Generator
 * TCSS 342 - Spring 2016
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MazePanel extends JPanel {
	private static final long serialVersionUID = 7011282142772442377L;
	private ArrayList<ShapeContainer> myShapes;
	private int myPanelWidth;
	private int myPanelHeight;
	private int myWidth;
	private int myDepth;
	private int myRectWidth;
	private int myRectHeight;
	private Cell grid[][];

	public MazePanel() {

	}

	public MazePanel(int thePanelWidth, int thePanelHeight, int theWidth, int theDepth) {
		myShapes = new ArrayList<>();
		myPanelWidth = thePanelWidth;
		myPanelHeight = thePanelHeight;
		myWidth = theWidth;
		myDepth = theDepth;
		myRectWidth = myPanelWidth / myWidth;
		myRectHeight = myPanelHeight / myDepth;
		// grid = theGrid;

	}

	private void drawWall(Graphics2D theGraphics) {
		theGraphics.setColor(Color.BLACK);
		if (grid != null) {
			for (int r = 0; r < myDepth; r++) {
				
				for (int c = 0; c < myWidth; c++) {
					if (grid[r][c].isSolution) {
						theGraphics.fillRect((c * myRectWidth )+ (myRectWidth/5), (r * myRectHeight) + (myRectHeight/5),
									myRectWidth - (myRectWidth / 2), myRectHeight - (myRectHeight / 2));
					}
					if (grid[r][c].northWall) {
						theGraphics.drawLine(c * myRectWidth, r * myRectHeight, (c * myRectWidth) + myRectWidth,
								r * myRectHeight);
					}
					if (grid[r][c].eastWall) {
						theGraphics.drawLine((c * myRectWidth) + myRectWidth, r * myRectHeight,
								(c * myRectWidth) + myRectWidth, (r * myRectHeight) + myRectHeight);
					}
					//
					if (grid[r][c].southWall) {
						theGraphics.drawLine(c * myRectWidth, (r * myRectHeight) + myRectHeight,
								(c * myRectWidth) + myRectWidth, (r * myRectHeight) + myRectHeight);
					}
					if (grid[r][c].westWall) {
						theGraphics.drawLine(c * myRectWidth, r * myRectHeight, (c * myRectWidth),
								(r * myRectHeight) + myRectHeight);
					}
				}
				repaint();
			}
			
		}
	}

	public void draw(Cell theGrid[][]) {
		// myShapes.add(new ShapeContainer(s, c));
		grid = theGrid;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(8));
		drawWall(g2d);
		
	}

	class ShapeContainer {
		private final Shape myShape;
		private final Color myColor;

		public ShapeContainer(final Shape theShape, final Color theColor) {
			myShape = theShape;
			myColor = theColor;
		}
	}
}