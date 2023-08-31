
/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	private static final int SENTINEL = 0;

	/*
	 * precondition: we have to enter numbers until we choose sentinel.
	 * postcondition: we have already got sentinel and know minimum and maximum
	 * numbers from our chosen numbers.
	 */
	public void run() {
		int num = 1, result = 0;
		int max = SENTINEL, min = SENTINEL;
		while (num != SENTINEL) {
			num = readInt("? ");
			result++;
			/** unless answer is not sentinel, we compare numbers to each other */
			if (num != SENTINEL) {
				max = MAX(num, max);
				min = MIN(num, min);
			}
		}
		printintro(min, max, result);
	}

	/** this function calculate max number (from our chosen numbers) */
	private int MAX(int num, int max) {
		if (max == SENTINEL) {
			max = num;
		}
		if (max < num) {
			max = num;
		}
		return max;
	}

	/** this function calculate min number (from our chosen numbers) */
	private int MIN(int num, int min) {
		if (min == SENTINEL) {
			min = num;
		}
		if (min > num) {
			min = num;
		}
		return min;
	}

	/** This function tells us max and min numbers. */
	private void printintro(int min, int max, int result) {
		if (result == 1) {
			println(" you entered " + SENTINEL + " on the first try !!!");
		} else if (result > 1) {
			println("smallest: " + min);
			println("largest: " + max);
		}
	}
}
