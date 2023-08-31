
/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;
/*
 * precondition: karel is standing on 1x1
 * postcondition: karel has already put one beeper in the middle of the first line
 */
public class MidpointFindingKarel extends SuperKarel {
	public void run() {
		putBeepersOnFirstLine();
		movingToStart();
		takingBeepersFromEdges();
		//everything is done already, but in the end we need to put beeper.
		putBeeper();
	}
	//this function make karel put beepers on the first line 
	private void putBeepersOnFirstLine() {
		if(frontIsClear()) {
			move();
		}
		while(frontIsClear()) {
		putBeeper();
		move();
		}
	}
	//this function makes karel to move to the 1x1.
	private void movingToStart() {
		turnAround();
		while(frontIsClear()) {
			move();
		}
		turnAround();
	}
	/*
	 * in this function karel takes beepers from the edges and finally 
	 * karel will be in the middle(when the first line is odd) or in the middle
	 * of two space(when it is even)
	 */
	private void takingBeepersFromEdges() {
		if(frontIsClear()){
			move();// i need to use this function, otherwise karel will crash the wall(when the square size is 1x1)
		}
		/*
		 * in this function karel pick beeper than move while beepers present
		 * then turnaround, move and pick a beeper from another edge,
		 * then it moves to the start where this function started. but this is not end of this function.
		 * we need to move karel again because we need this function to work on and on while beepers present
		 */
		while(beepersPresent()) {
			safePickBeeper();
			/*
			 * we need order:(move) because for the next order
			 * karel need a beeper to move.
			 */
			move();
			while(beepersPresent()) {
				move();
			}
			turnAround();
			move();
			safePickBeeper();
			/*
			 * we need order:(move) because for the next order
			 * karel need a beeper to move.
			 */
			move();
			while(beepersPresent()) {
				move();
			}
			turnAround();
			/*
			 * we need to move karel again because we need function:(takingBeepersFromEdges)  
			 * to work on and on while beepers present
			 */
			move();
		}
	}
	/*
	 * i had to create safePickBeeper because if there is no beepers 
	 * Karel should know not to take it. otherwise function wont be correct
	 */
	private void safePickBeeper() {
		if(beepersPresent()) {
			pickBeeper();
		}
	}
}
