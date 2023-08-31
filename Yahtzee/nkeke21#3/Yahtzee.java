
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.Arrays;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}
	/** variable(massive) for dice */
	private int[] dice;
	
	/** this variable helps to count amount of score */
	private int total;
	
	/** i create matrix - cat. this variable helps a player not to enter the 
	 * total of score in the same category */
	private int[][] cat;

	private void playGame() {
		scor(); //void for keeping score
		catt();
		game();
	}
	private void game() {
		for (int k = 0; k < N_SCORING_CATEGORIES; k++) {
			for (int i = 1; i <= nPlayers; i++) {
				roll(i);
				int category = categ(i);
				scoring(category);
				update(category, i);
			}
		}
		fillLastCategories();
		winner();
	}
	
	/** part of game which consists rolling a dice */
	private void roll(int m) {
		
		/** text for informing a player to roll dice*/
		String text = playerNames[m - 1] + "'s turn! Click \"Roll Dice\" button to roll the dice";
		
		display.printMessage(text);
		total = 0; 
		display.waitForPlayerToClickRoll(m);
		dice = new int[N_DICE]; // massive for dices 
		for (int i = 0; i < dice.length; i++) {
			dice[i] = rgen.nextInt(1, 6);
		}
		display.displayDice(dice); 
		dieSelect(dice);
	}
	/** re-rolling the dices */
	private void dieSelect(int[] dice) {
		String text = "select the dice you wish to re-roll and click \"Roll-Again\"";
		/**  a player has got two chances to re-roll the dics */
		for (int i = 0; i < 2; i++) {
			display.printMessage(text);
			display.waitForPlayerToSelectDice();
			/* if a player choose a dice it must be re-rolled*/
			for (int k = 0; k < N_DICE; k++) {
				if (display.isDieSelected(k)) {
					dice[k] = rgen.nextInt(1, 6);
				}
			}
			display.displayDice(dice);
		}
	}
	/** create a matrix to save the information to know which categroy is filled*/
	private void catt() {
		cat = new int[nPlayers][TOTAL];
	}
	
	/**method is created to control scores in categories*/ 
	private int categ(int i) {
		/** next three lines are written to inform a player*/
		String text = "Select a category for this roll";
		String incorrect = "You selected the same categroy, choose again";
		display.printMessage(text);
		
		int category = display.waitForPlayerToSelectCategory();
		
		/** if category is chosen again, player should choose it again*/
		while (!cate(category, cat, i)) {
			display.printMessage(incorrect);
			category = display.waitForPlayerToSelectCategory();
		}
		return category;
	}
	
	/** boolean which helps us to know which category is already filled */
	private boolean cate(int num, int[][] cat, int n) {
		if (cat[n - 1][num - 1] != 1) {
			return true;
		}
		return false;
	}
	
	/** void for scoring */
	private void scoring(int category) {
		/** the next lines are the situations when a player fill the first six category*/
		if (category <= SIXES) {
			if (category == ONES) {
				total = upperScore(ONES);
			} else if (category == TWOS) {
				total = upperScore(TWOS);
			} else if (category == THREES) {
				total = upperScore(THREES);
			} else if (category == FOURS) {
				total = upperScore(FOURS);
			} else if (category == FIVES) {
				total = upperScore(FIVES);
			} else if (category == SIXES) {
				total = upperScore(SIXES);
			}
		}
		
		/** score's categories of lower score */
		if (category == THREE_OF_A_KIND) {
			threeOfKind();
		} else if (category == FOUR_OF_A_KIND) {
			fourOfKind();
		} else if (category == FULL_HOUSE) {
			fullhous();
		} else if (category == SMALL_STRAIGHT) {
			small_straight();
		} else if (category == LARGE_STRAIGHT) {
			long_straight();
		} else if (category == YAHTZEE) {
			yahtz();
		} else if (category == CHANCE) {
			total();
		}
	}
	
	/** method counts the total score when a player enter the score in the fist six categories */
	private int upperScore(int a) {
		int sum = 0;
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == a) {
				sum += a;
			}
		}
		return sum;
	}
	
	/** situation for three of kind */
	private void threeOfKind() {
		if (three()) {
			total();
		} else {
			total = 0;
		}
	}
	
	/** boolean which makes a player known if it is really three of a kind*/
	private boolean three() {
		
		/** count is created, because by count we can know if minimum three of dices are the same*/
		int count = 1;
		
		for (int i = 0; i < dice.length; i++) {
			for (int k = i + 1; k < dice.length; k++) {
				if (dice[i] == dice[k]) {
					count++;
				}
			}
			if (count == THREES) {
				return true;
			}
			count = 1;
		}
		return false;
	}

	/** situation for four of kind*/
	private void fourOfKind() {
		if (four()) {
			total();
		} else {
			total = 0;
		}
	}
	
	/** this boolean is created to check if it is really four of kind */
	private boolean four() {
		int count = 1;
		for (int i = 0; i < dice.length; i++) {
			for (int k = i + 1; k < dice.length; k++) {
				if (dice[i] == dice[k]) {
					count++;
				}
				if (count == FOURS) {
					return true;
				}
			}
			count = 1;
		}
		return false;
	}
	
	/** this void if for only score's of lowerscore */
	private void total() {
		for (int i = 0; i < dice.length; i++) {
			total += dice[i];
		}
	}
	
	/** situation for full house */
	private void fullhous() {
		if (fullhouse()) {
			total = 25;
		} else {
			total = 0;
		}
	}
	
	/** boolean which check if rolling dices are full house */
	private boolean fullhouse() {
		
		/** by count we can check if full house is rolled,
		 * so i created variables to count
		 */
		int trip = 1, doub = 1;
		
		/** firstly, i check if there are three dices with the same meaning*/
		for (int i = 0; i < dice.length; i++) {
			for (int j = i + 1; j < dice.length; j++) {
				if (dice[i] == dice[j]) {
					trip++;
				}
			}
			
			/** after this we check if left two dices are the same */
			if (trip == 3) {
				for (int m = 0; m < dice.length; m++) {
					for (int k = m + 1; k < dice.length; k++) {
						if (dice[m] != dice[i]) {
							if (dice[m] == dice[k]) {
								doub++;
							}
						}
						if (doub == TWOS) {
							return true;
						}
					}
				}
			} else {
				trip = 1;
			}
		}
		return false;
	}
	
	/** situation for small_straight */
	private void small_straight() {
		if (sm_straight()) {
			total = 30;
		} else {
			total = 0;
		}
	}
	
	/** boolean which tells if rolled dices are the dices of small straight */
	private boolean sm_straight() {
		Arrays.sort(dice);
		
		/** by count we can check if it is small straight */
		int quadr = 1;
		for (int i = 0; i < 2; i++) {
			int m = i;
			for (int j = i + 1; j < dice.length; j++) {
				if (dice[j] != dice[m]) {
					if (dice[j] - dice[m] == 1) {
						quadr++;
						m++;
					}
				}
				if (quadr >= FOURS) {
					return true;
				}
			}
			quadr = 1;
		}
		return false;
	}
	
	/** situation for long straight. i think that it is more convenient to write situations
	 * for small straight and long straight in the different voids and booleans. it is also
	 * simple for reader to know how code is written
	 */
	
	private void long_straight() {
		if (lg_straight()) {
			total = 40;
		} else {
			total = 0;
		}
	}

	/** boolean which check if rolled dices are the dices of long straight */
	private boolean lg_straight() {
		Arrays.sort(dice);
		for (int i = 1; i < dice.length; i++) {
			if (dice[i] - dice[i - 1] != 1) {
				return false;
			}
		}
		return true;
	}
	
	/** situation for Yahtzee */
	private void yahtz() {
		if (yaht()) {
			total = 50;
		} else {
			total = 0;
		}
	}
	/** boolean which check if the rolled dices are the dices of Yahtzee */
	private boolean yaht() {
		for (int i = 1; i < dice.length; i++) {
			if (dice[i] != dice[i - 1]) {
				return false;
			}
		}
		return true;
	}
	/** massives for upper score, lower score and total of both of them */
	private int[] upperscore;
	private int[] lowerscore;
	private int[] sum;

	/** void for scoring*/
	private void scor() {
		upperscore = new int[nPlayers];
		lowerscore = new int[nPlayers];
		sum = new int[nPlayers];
	}
	
	/** this void add scores in the upper score massive */

	private void upper(int nplayer) {
		upperscore[nplayer - 1] += total;
	}

	/** this void add scores in the lower score massive */
	private void lower(int nplayer) {
		lowerscore[nplayer - 1] += total;
	}
	
	/** this void sum up lower score and upper score together */
	private void sumUp(int nplayer) {
		sum[nplayer -1] = lowerscore[nplayer - 1] + upperscore[nplayer - 1];
		display.updateScorecard(TOTAL, nplayer, sum[nplayer - 1]);
	}

	/** void which add scores in the created massives */
	private void update(int category, int nplayer) {
		display.updateScorecard(category, nplayer, total);
		cat[nplayer - 1][category - 1] = 1;
		if (category <= SIXES) {
			upper(nplayer);
		} else {
			lower(nplayer);
		}
		sumUp(nplayer);
	}
	
	/** void which fills left categories */
	private void fillLastCategories() {
		for (int i = 1; i <= nPlayers; i++) {
			display.updateScorecard(UPPER_SCORE, i, upperscore[i - 1]);
			display.updateScorecard(LOWER_SCORE, i, lowerscore[i - 1]);
			
			if (uppSco(i)) {
				sum[i - 1] = sum[i - 1] + 35;
				display.updateScorecard(UPPER_BONUS, i, 35);
				display.updateScorecard(TOTAL, i, sum[i - 1]);
			} else {
				display.updateScorecard(UPPER_BONUS, i, 0);
			}
		}
	}
	
	/** this void tells us who is the winner. note: in the term there was not written what
	 * happens if it is draw. so i left this void how it is*/
	private void winner() {
		int max = 0;
		int win_pl = 0;
		for(int i = 1 ; i <= nPlayers; i++) {
			if(max < sum[i-1]) {
				max = sum[i - 1];
				win_pl = i - 1;
			}
		}
		String text = "Congratulation ,"+ playerNames[win_pl] +", your are the winner with "
				+ "a total score of " + max;
		display.printMessage(text);
	}
	
	/** boolean which check if upperscore is more than 63 */
	private boolean uppSco(int nplayer) {
		if (upperscore[nplayer - 1] > 63) {
			return true;
		}
		return false;
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
