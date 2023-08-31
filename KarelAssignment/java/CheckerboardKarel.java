/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;
/*
* precondition: Karel stands on the 1 x 1
* postcondition: Karel have already put beepers like a chechkerboard.
*/
public class CheckerboardKarel extends SuperKarel {
	public void run() {
		putBeepersOnFirstLine();
		makeChessBoard();
	}
	/* this function makes karel put beepers on the first line for any situations
 	 *and then to return it to the place where this function started.
 	 */
	private void putBeepersOnFirstLine() {
		putBeeper();
		if (frontIsClear()) {
			move();
			putBeepersAndReturn();
		}
	}
	/* this function makes karel move, putBeeper and move while frontIsClear and
	 * then move it back while front is clear.
	 */
	private void putBeepersAndReturn() {
		while (frontIsClear()) {
			move();
			putBeeper();
			safeMove();
		}
		moveBack();
	}
	private void safeMove() {
		// this function helps Karel not to crash a wall
		if (frontIsClear()) {
			move();
		}
	}
	/* 
	 * this function makes karel turn around and 
	 * then move back, while front is clear and finally turn around
	 */
	private void moveBack() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnAround();
	}
	
	// this function makes karel to move from one a line to a second line.
	private void moveToSecondFloor() {
		turnLeft();
		/*
		 * this function helps karel not to crash wall while
		 * moving from one line to another
		 */
		if (frontIsClear()) {
			move();
			turnRight();
		}
	}
	// this function makes karel put beepers on next a line.
	private void putBeepersOnSecondLine() {
		putBeepersAndReturn();
	}
	/*
	 * this function makes Karel to put beepers on the first line, on the second line
	 * and ... line while left is clear and finally Karel should make a checkerboard.
	 * board.
	 */
	private void makeChessBoard() {
		while (leftIsClear()) {
			moveToSecondFloor();
			putBeepersOnSecondLine();
			if (leftIsClear()) {
				moveToSecondFloor();
				putBeepersOnFirstLine();
			}
		}
	}
}
