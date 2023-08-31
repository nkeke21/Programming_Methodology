
/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	/*
	 * Precondition: we have to enter numbers 
	 * Postcondition: we have already got 1.
	 */
	public void run() {
		/* You fill this in */
		int num = readInt("Enter a number: ");// Entering number
		/** this variable helps us to calculate the amount of attempts. */
		int AMOUNT = 0;
		while (num != 1) {
			AMOUNT++; // calculating amount of attempts
			num = hailstone(num);
		}

		println("the process took " + AMOUNT + " to reach 1");
	}

	private int hailstone(int num) {
		/** If entered number is even. this code divides it by two. */
		if (num % 2 == 0) {
			println(num + " is even, so i take half: " + num / 2);
			num /= 2;
		} else {
			/*
			 * If entered number is odd. this code subtract it by three and then add 1.
			 */
			println(num + " is odd, so i make 3n+1: " + (3 * num + 1));
			num = 3 * num + 1;
		}
		return num;
	}
}
