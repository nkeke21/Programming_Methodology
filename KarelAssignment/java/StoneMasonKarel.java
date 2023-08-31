/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
	public void run() {
		/*
		 * precondition: karel stands on 1x1
		 * postcondition: karel have already rebuilt all the collumns 
		 */
		rebuildCollumnsInGeneral();
		rebuildCollumn();//last collumn
	}
	/*
	 * this function make karel rebuild a collumn and then return it back 
	 * to the place where rebuildCollumn start
	 */
	private void rebuildCollumn() {
		turnLeft();
		while(frontIsClear()) {
			safePutBeeper();
			move();
		}
		safePutBeeper();
		moveBack();
	}
	/*
	 *  we dont know where beeper is, so we need function to know where beeper is not 
	 *  and put beeper on that kind of place
	 */
	private void safePutBeeper() {
		if(noBeepersPresent()) {
			putBeeper();
		}
	}
	/*
	 *  this function moves Karel to turn around, then move
	 *  while front is clear and finally turn left
	 */
	private void moveBack(){
		turnAround();
		while(frontIsClear()) {
			move();
		}
		turnLeft();
	}
	//this function makes karel rebuild collumns while front is clear.
	private void rebuildCollumnsInGeneral() {
		while(frontIsClear()) {
			rebuildCollumn();
			for(int i=0; i < 4; i++) {//this order makes karel move forward for 4x times.
				move();
			}
		}
	}
}
