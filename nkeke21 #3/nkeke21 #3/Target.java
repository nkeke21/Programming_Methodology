
/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
	private static final double CM_TO_PIXELS(double a) {
		return (72 / 2.54) * a;
	}

	/** radius of the biggest circle */
	private static final double CIR_1_RAD = 72;
	/** radius of medium circle */
	private static final double CIR_2_RAD = CM_TO_PIXELS(1.65);
	/** radius of the smallest circle */
	private static final double CIR_3_RAD = CM_TO_PIXELS(0.76);

	public void run() {
		/** Drawing the biggest circle (colored red). */
		drawcircle(Color.RED, CIR_1_RAD, CIR_1_RAD);
		/** Drawing medium circle(colored white). */
		drawcircle(Color.WHITE, CIR_2_RAD, CIR_2_RAD);
		/** Drawing the smallest circle (colored red). */
		drawcircle(Color.RED, CIR_3_RAD, CIR_3_RAD);
	}

	/** this function draws the circle */
	private void drawcircle(Color color, double x, double y) {
		GOval circle = new GOval(x, y);
		circle.setColor(color);
		circle.setFilled(true);
		double circlex = (getWidth() - x) / 2; // x coordinate
		double circley = (getHeight() - y) / 2;// y coordinate
		add(circle, circlex, circley);
	}
}
