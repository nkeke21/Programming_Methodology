
/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {/* You fill this in */
		println("Enter values to compute phytagorean theorem");
		int n1 = readInt("a: ");
		int n2 = readInt("b: ");
		double phytagor = total(n1, n2);
		println("c = " + phytagor);
	}

	/*
	 * precondition : we have got entered n1 and n2 and have to do pythagroean.
	 * postcondition : we have already done pythagorean and have got the answer.
	 */
	private double total(double a, double b) {
		double A_2 = a * a;
		double B_2 = b * b;
		double TOTAL_2 = A_2 + B_2;
		return Math.sqrt(TOTAL_2);
	}
}
