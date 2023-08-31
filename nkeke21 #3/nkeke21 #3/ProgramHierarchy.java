
/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {
	private static final int GRECT_LENGTH = 150;
	private static final int GRECT_HEIGHT = 50;
	private static final int OFFSET_CENTER_2 = GRECT_HEIGHT * 2;
	private static final int OFFSET_GRECTS = GRECT_LENGTH + 30;

	public void run() {
		/* You fill this in. */
		RECT_1_LABEL_1();// drawing first rect with it's label
		RECT_2_LABEL_2_LINE_1();
		RECT_3_LABEL_3_LINE_2();
		RECT_4_LABEL_4_LINE_3();
	}

	// I took constant sizes because it will be easier to change size
	private void RECT_1_LABEL_1() {
		// This function paint rect 1 (colored cyan).
		GRect rect1 = new GRect(GRECT_LENGTH, GRECT_HEIGHT);
		double x_1 = (getWidth() - GRECT_LENGTH) / 2;
		double y_1 = getHeight() / 2 - OFFSET_CENTER_2;
		rect1.setColor(Color.CYAN);
		add(rect1, x_1, y_1);

		/*
		 * This function write program inside the rect1. We already know rect1's
		 * cordinates. So, it is easier to write "program" inside the rect1
		 */
		GLabel label1 = new GLabel("Program");
		final double lab_x = (getWidth() - label1.getWidth()) / 2;
		final double lab_y = y_1 + (GRECT_HEIGHT + label1.getAscent()) / 2;
		label1.setColor(Color.MAGENTA);
		add(label1, lab_x, lab_y);
	}

	/*
	 * Drawing second rect with it's label and line(connecting second rect to the
	 * rect1)
	 */
	private void RECT_2_LABEL_2_LINE_1() {
		GRect rect2 = new GRect(GRECT_LENGTH, GRECT_HEIGHT);
		double x_2 = (getWidth() - GRECT_LENGTH) / 2;
		double y_2 = getHeight() / 2;
		rect2.setColor(Color.CYAN);
		add(rect2, x_2, y_2);// Drawing rect 2(colored cyan).

		/*
		 * This function write consoleprogram inside the rect2. We already know rect2's
		 * cordinates. So, it is easier to write "ConsoleProgram" inside the rect2
		 */
		GLabel label2 = new GLabel("ConsoleProgram");
		final double lab2_x = (getWidth() - label2.getWidth()) / 2;
		final double lab2_y = y_2 + (GRECT_HEIGHT + label2.getAscent()) / 2;
		label2.setColor(Color.MAGENTA);
		add(label2, lab2_x, lab2_y);

		/*
		 * This function connect rect 2 to the rect1. Again rect2's cordinates help us
		 * to write line_1's correct cordinates
		 */
		final double lineMid_x = getWidth() / 2;
		final double lineMid_y_1 = getHeight() / 2;
		final double lineMid_y_2 = getHeight() / 2 - GRECT_HEIGHT;
		add(new GLine(lineMid_x, lineMid_y_1, lineMid_x, lineMid_y_2));
	}

	/*
	 * Drawing third rect with it's label and line(connecting second rect to the
	 * rect1)
	 */
	private void RECT_3_LABEL_3_LINE_2() {
		GRect rect3 = new GRect(GRECT_LENGTH, GRECT_HEIGHT);
		double x_3 = (getWidth() - GRECT_LENGTH) / 2 - OFFSET_GRECTS;
		double y_3 = getHeight() / 2;
		rect3.setColor(Color.CYAN);
		add(rect3, x_3, y_3);// this function add rect3(colored cyan)

		/*
		 * This function write GraphicsProgram inside the rect3. We already know rect3's
		 * cordinates. So, it is easier to write "GraphicsProgram" inside the rect3
		 */
		GLabel label3 = new GLabel("GraphicsProgram");
		final double lab3_x = x_3 + (GRECT_LENGTH - label3.getWidth()) / 2;
		final double lab3_y = y_3 + (GRECT_HEIGHT + label3.getAscent()) / 2;
		label3.setColor(Color.MAGENTA);
		add(label3, lab3_x, lab3_y);

		// This function draws left line which connect rect3 to the rect1.
		final double lineLeft_x_1 = x_3 + GRECT_LENGTH / 2;
		final double lineLeft_x_2 = getWidth() / 2;
		final double lineLeft_y_1 = getHeight() / 2;
		final double lineLeft_y_2 = getHeight() / 2 - GRECT_HEIGHT;
		add(new GLine(lineLeft_x_1, lineLeft_y_1, lineLeft_x_2, lineLeft_y_2));
	}

	/*
	 * Drawing 4th rect with it's label and line(connecting second rect to the
	 * rect1)
	 */
	private void RECT_4_LABEL_4_LINE_3() {
		GRect rect4 = new GRect(GRECT_LENGTH, GRECT_HEIGHT);
		double x_4 = (getWidth() - GRECT_LENGTH) / 2 + OFFSET_GRECTS;
		double y_4 = getHeight() / 2;
		rect4.setColor(Color.CYAN);
		add(rect4, x_4, y_4);// adding rect4(colored.cyan)

		/*
		 * This function write dialogprogram inside the rect4. we already know rect4's
		 * cordinates. So, it is easier to write "DialogProgram" inside the rect4
		 */
		GLabel label4 = new GLabel("DialogProgram");
		final double lab4_x = x_4 + (GRECT_LENGTH - label4.getWidth()) / 2;
		final double lab4_y = y_4 + (GRECT_HEIGHT + label4.getAscent()) / 2;
		label4.setColor(Color.MAGENTA);
		add(label4, lab4_x, lab4_y);

		// This function draws right line which connect rect4 to the rect1.
		final double lineRight_x_1 = x_4 + GRECT_LENGTH / 2;
		final double lineRight_x_2 = getWidth() / 2;
		final double lineRight_y_1 = getHeight() / 2;
		final double lineRight_y_2 = getHeight() / 2 - GRECT_HEIGHT;
		add(new GLine(lineRight_x_1, lineRight_y_1, lineRight_x_2, lineRight_y_2));
	}
}
