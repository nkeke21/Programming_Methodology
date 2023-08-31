
/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {
	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	/*
	 * precondition: we have got nothing on the screen. 
	 * postcondition: we have already built pyramid.
	 */
	public void run() {
		/** build pyramid */
		addBrick(BRICK_WIDTH, BRICK_HEIGHT);
	}

	private void addBrick(double a, double b) {
		for (int i = 0; i < (BRICKS_IN_BASE); i++) {
			for (int j = 0; j < BRICKS_IN_BASE - i; j++) {

				/*
				 * This function helps us to put the blocks next to each other on each base.
				 */
				double x = j * a;
				GRect rect1 = new GRect(a, b);

				/** this function help us to put bricks on the next base */
				double offset_Bricks = (i + 1) * b;
				double halfLength = ((BRICKS_IN_BASE - i) * a) / 2; // length/2 of every base
				double LOCATION_X = getWidth() / 2 - halfLength; // x coordinate
				double LOCATION_Y = getHeight() - offset_Bricks; // t coordinate
				add(rect1, LOCATION_X + x, LOCATION_Y);// adding block
			}
		}
	}
}
