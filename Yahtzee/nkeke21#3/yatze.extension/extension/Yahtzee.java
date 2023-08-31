
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.applet.AudioClip;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	/** calling top ten class*/
	top10 tp = new top10();

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
	/** massive for dices */
	private int[] dice;
	
	/**this variable helps us to count score */
	private int total;
	
	/** by this variable we can guess whic category is filled*/
	private int[][] cat;

	/** playing game*/
	private void playGame() {
		scor();
		catt();
		game();
	}
	
	/** most important part*/
	private void game() {
		for (int k = 0; k < N_SCORING_CATEGORIES; k++) {
			for (int i = 1; i <= nPlayers; i++) {
				roll(i);
				int category = categ(i);
				scoring(category);
				update(category, i);
			}
		}
		fillBonusCat();
		winner();
		top10();
	}
	
	/** rolling the dices*/
	private void roll(int m) {
		String text = playerNames[m - 1] + "'s turn! Click \"Roll Dice\" button to roll the dice";
		display.printMessage(text);
		total = 0;
		display.waitForPlayerToClickRoll(m);
		dice = new int[5];
		
		/**returns meanings of dices with random*/
		for (int i = 0; i < dice.length; i++) {
			dice[i] = rgen.nextInt(1, 6);
		}
		display.displayDice(dice);
		
		/**re-roll selected dices*/
		dieSelect(dice);
	}
	
	/** this void re-roll selected dices*/
	private void dieSelect(int[] dice) {
		String text = "select the dice you wish to re-roll and click \"Roll-Again\"";
		
		/** a player has got two chances to re-roll dices */
		for (int i = 0; i < 2; i++) {
			display.printMessage(text);
			display.waitForPlayerToSelectDice();
			for (int k = 0; k < N_DICE; k++) {
				if (display.isDieSelected(k)) {
					dice[k] = rgen.nextInt(1, 6);
				}
			}
			display.displayDice(dice);
		}
	}
	
	/** create cat matrix to save information, which category is selected */
	private void catt() {
		cat = new int[nPlayers][TOTAL];
	}
	
	/**this void is for choosing a category*/
	private int categ(int i) {
		/**this texts are for infroming a player what to do*/
		String text = "Select a category for this roll";
		String incorrect = "You selected the same categroy, choose again";
		display.printMessage(text);
		
		int category = display.waitForPlayerToSelectCategory();
		
		/**this makes a player to choose another category wich is not chosen*/
		while (!cate(category, cat, i)) {
			display.printMessage(incorrect);
			category = display.waitForPlayerToSelectCategory();
		}
		return category;
	}
	
	/**boolean to detect which category is already chosen*/
	private boolean cate(int num, int[][] cat, int n) {
		if (cat[n - 1][num - 1] != 1) {
			return true;
		}
		return false;
	}
	
	/**scoring */
	private void scoring(int category) {
		
		/** situation for components of upper score*/
		if (category <= SIXES) {
			if (category == ONES) {
				ones.play();
				total = upperScore(ONES);
			} else if (category == TWOS) {
				twos.play();
				total = upperScore(TWOS);
			} else if (category == THREES) {
				threes.play();
				total = upperScore(THREES);
			} else if (category == FOURS) {
				fours.play();
				total = upperScore(FOURS);
			} else if (category == FIVES) {
				fives.play();
				total = upperScore(FIVES);
			} else if (category == SIXES) {
				sixes.play();
				total = upperScore(SIXES);
			}
		}
		
		/** situations for components of lower score*/
		if (category == THREE_OF_A_KIND) {
			threeOfkind.play();
			threeOfKind();
		} else if (category == FOUR_OF_A_KIND) {
			fourOfkind.play();
			fourOfKind();
		} else if (category == FULL_HOUSE) {
			fullHouse.play();
			fullhous();
		} else if (category == SMALL_STRAIGHT) {
			smallStraight.play();
			small_straight();
		} else if (category == LARGE_STRAIGHT) {
			largeStraight.play();
			long_straight();
		} else if (category == YAHTZEE) {
			yatzee.play();
			yahtz();
		} else if (category == CHANCE) {
			chance.play();
			total();
		}
	}
	
	/**method to count score of componets of upper score*/
	private int upperScore(int a) {
		int sum = 0;
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == a) {
				sum += a;
			}
		}
		return sum;
	}
	
	/**situation for three of kind*/
	private void threeOfKind() {
		if (three()) {
			total();
		} else {
			total = 0;
		}
	}
	/** boolean to detect meanings of dices match three of kind*/
	private boolean three() {
		
		/** i count the dices if they match each other, if there 
		 * are three same dices this situation match three of kind 
		 */
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
	
	/**situation for four of kind*/
	private void fourOfKind() {
		if (four()) {
			total();
		} else {
			total = 0;
		}
	}
	
	/** boolean to detect meanings of dices match three of kind*/
	private boolean four() {
		
		/** i count the dices if they match each other, if there 
		 * are three same dices this situation match three of kind 
		 */
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
	
	/** this void counts total score for some situation.
	 * for example: three of kind, four of kind and ...
	 */
	private void total() {
		for (int i = 0; i < dice.length; i++) {
			total += dice[i];
		}
	}
	
	/** situation for fullhouse */
	private void fullhous() {
		if (fullhouse()) {
			total = 25;
		} else {
			total = 0;
		}
	}
	
	/** boolean to detect if rolled dices match full house */
	private boolean fullhouse() {
		int trip = 1, doub = 1;
		
		/**first of all i count if there are three same dices in rolled dices*/
		for (int i = 0; i < dice.length; i++) {
			for (int j = i + 1; j < dice.length; j++) {
				if (dice[i] == dice[j]) {
					trip++;
				}
			}
			/** then i count if there are another two dices which are the same */
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

	/** situation for small straight*/
	private void small_straight() {
		if (sm_straight()) {
			total = 30;
		} else {
			total = 0;
		}
	}
	
	/**boolean to detect if rolled dices match small straight*/
	private boolean sm_straight() {
		Arrays.sort(dice);
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
	
	/** situation for long straight*/
	private void long_straight() {
		if (lg_straight()) {
			total = 40;
		} else {
			total = 0;
		}
	}
	
	/**boolean to detect if rolled dices match long straight*/
	private boolean lg_straight() {
		Arrays.sort(dice);
		for (int i = 1; i < dice.length; i++) {
			if (dice[i] - dice[i - 1] != 1) {
				return false;
			}
		}
		return true;
	}

	/**situation for yahtzee*/
	private void yahtz() {
		if (yaht()) {
			total = 50;
		} else {
			total = 0;
		}
	}
	/**boolean to detect if rolled dices match yahtzee */
	private boolean yaht() {
		for (int i = 1; i < dice.length; i++) {
			if (dice[i] != dice[i - 1]) {
				return false;
			}
		}
		return true;
	}
	/**variable to count upper score in total for each player*/
	private int[] upperscore; 
	
	/**variable to count lower score in total for each player*/
	private int[] lowerscore;
	
	private int[] sum;
	
	/**create variables*/
	private void scor() {
		upperscore = new int[nPlayers];
		lowerscore = new int[nPlayers];
		sum = new int[nPlayers];
	}
	
	/** this void add scores in upper score for each player*/
	private void upper(int nplayer) {
		upperscore[nplayer - 1] += total;
	}
	
	/**this void add scores in lower score for each player*/
	private void lower(int nplayer) {
		lowerscore[nplayer - 1] += total;
	}
	
	/**sum up every scores for each player*/
	private void sumUp(int nplayer) {
		sum[nplayer - 1] = lowerscore[nplayer - 1] + upperscore[nplayer - 1];
		display.updateScorecard(TOTAL, nplayer, sum[nplayer - 1]);
	}
	
	/**this void control changing canvas, also it
	 * controls where scores should be added
	 */
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
	
	/**this void fills left categories(upper score,lower score, total and upper bonus)*/
	private void fillBonusCat() {
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
	
	/** void to detect winner*/
	private void winner() {
		int max = 0;
		int win_pl = 0;
		for (int i = 1; i <= nPlayers; i++) {
			if (max < sum[i - 1]) {
				max = sum[i - 1];
				win_pl = i - 1;
			}
		}
		tp.score(max); //enter score of a player
		tp.user(playerNames[win_pl]); // enter a name of a player
		
		/**this help tp class to make a list of players which played yahtzee before*/
		tp.listing(); 
		
		/** the next lines detect if a player can be in the top ten*/
		if (tp.moveTo10()) {
			String text = "Congratulation ," + playerNames[win_pl] + ", you win with the score of " + max + " and moved"
					+ " to the top ten";
			display.printMessage(text);
		} else {
			String text = "Congratulation ," + playerNames[win_pl] + ", your are the winner with " + "a total score of "
					+ max;
			display.printMessage(text);
		}
		pause(1000);
	}
	
	/** boolean which detect if a player has got enough scores
	 *  in upper score  to have got bonus score
	 */
	private boolean uppSco(int nplayer) {
		if (upperscore[nplayer - 1] > 63) {
			return true;
		}
		return false;
	}
	
	/** this void tells us the names of players and their scores, who are in top 10 */
	private void top10() {
		removeAll();
		FirstToTen();
	}
	
	/** this void create the list of top10*/
	private void FirstToTen() {
		List<String> lst = new ArrayList<>();
		lst = tp.returnList();
		int[] supp = upp(lst);
		listing(supp, lst);
		background();
		inform_top10();
		lighting.play();
	}
	
	/** visual effect */
	private void background() {
		GImage background = new GImage("visual.gif");
		background.setSize(getWidth(), getHeight());
		add(background);
	}

	private ArrayList<String> list;

	private void listing(int[] supp, List<String> lst) {
		list = new ArrayList<>();
		for (int i = supp.length - 1; i >= 0; i--) {
			int num = lst.indexOf(intToString(supp[i]));
			if (num != -1) {
				list.add(lst.get(num - 1));
				list.add(lst.get(num));
			}
			lst.remove(num);
			lst.add(num, "-10");
		}
	}
	
	/**these constans are for names and scores. they are coordinates*/
	private static final int off_middle1 = 120;
	private static final int off_middle2 = 100;
	private static final int off_top = 70;
	
	/** painting top 10 list */
	private void inform_top10() {
		int x1 = getWidth() / 2 - off_middle1;
		int x2 = getWidth() / 2 + off_middle2;
		for (int i = 0; i < list.size(); i += 2) {
			GLabel name = new GLabel(list.get(i));
			GLabel score = new GLabel(list.get(i + 1));
			name.setFont("-20");
			name.setColor(Color.CYAN);
			score.setFont("-20");
			score.setColor(Color.CYAN);
			add(name, x1, off_top + i * 12);
			add(score, x2, off_top + i * 12);
		}
	}
	
	/** makes the masive of scores*/
	private int[] upp(List<String> lst) {
		int[] res = new int[lst.size() / 2];
		for (int i = 1; i < lst.size(); i += 2) {
			res[(i - 1) / 2] = Integer.parseInt(lst.get(i));
		}
		Arrays.sort(res);
		return res;
	}
	
	/** method to change in into the string */
	private String intToString(int input) {
		String result = "";
		while (input >= 1) {
			result = (char) (input % 10 + '0') + result;
			input /= 10;
		}
		return result;
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	
	/** voices */
	AudioClip lighting = MediaTools.loadAudioClip("Lighting.au");
	AudioClip ones = MediaTools.loadAudioClip("ones.au");
	AudioClip twos = MediaTools.loadAudioClip("twos.au");
	AudioClip threes = MediaTools.loadAudioClip("threes.au");
	AudioClip fours = MediaTools.loadAudioClip("fours.au");
	AudioClip fives = MediaTools.loadAudioClip("fives.au");
	AudioClip sixes = MediaTools.loadAudioClip("sixes.au");
	AudioClip threeOfkind = MediaTools.loadAudioClip("threeofkind.au");
	AudioClip fourOfkind = MediaTools.loadAudioClip("fourofkind.au");
	AudioClip smallStraight = MediaTools.loadAudioClip("smallstraight.au");
	AudioClip largeStraight = MediaTools.loadAudioClip("largestraight.au");
	AudioClip fullHouse = MediaTools.loadAudioClip("fullhouse.au");
	AudioClip yatzee = MediaTools.loadAudioClip("yatzee.au");
	AudioClip chance = MediaTools.loadAudioClip("chance.au");
}
