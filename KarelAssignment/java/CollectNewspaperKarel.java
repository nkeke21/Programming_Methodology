/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;
	/*
	 * precondition:Karel is standting in a room facing east
	 * postcondition:Karel has already taken a newspaper and 
	 * standing ,again, in a room facing east
	 */
public class CollectNewspaperKarel extends SuperKarel {
	public void run() {
		movingRight();
		moveForwardToTakeNewspaper();
		pickBeeper();
		moveBack();
		movingRight_2();
	}

	/*
	 * this function make Karel turn right and move to the next spot and then turn
	 * left
	 */
	private void movingRight() {
		turnRight();
		move();
		turnLeft();
	}

	//in this function 	Karel is going forward to take a newspaper
	private void moveForwardToTakeNewspaper() {
		for (int i = 0; i < 3; i++) {//this function makes karel move 3x times
			move();
		}
	}

	/*
	 * in this function Karel turn around and move fowrard while  
	 * front is clear.
	 */
	private void moveBack() {
		turnAround();
		moveForwardToTakeNewspaper();
	}

	/*
	 * in this function karel turn right move forward
	 * to the next spot and turn right
	 */
	private void movingRight_2() {
		turnRight();
		move();
		turnRight();
	}
}
