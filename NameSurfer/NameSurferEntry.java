/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;

import java.util.*;

public class NameSurferEntry implements NameSurferConstants {
	/**massive to store ranks of entered name according to decades */
	private int[] rank;
	
	/**variable to store name of entered name*/
	private String name;

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	/**NameSurferEntry makes line separate. it separate name from ranks. after this
	 * NameSurferEntry add ranks to the massive.
	 */
	public NameSurferEntry(String line) {
		/**boolean to remember first word of each line*/
		boolean first = true;
		rank = new int[NDECADES]; //creating massive to store ranks
	
		StringTokenizer stk = new StringTokenizer(line);
		while(stk.hasMoreTokens()) {
			if(first) {
				name = stk.nextToken();
				first = false;
			} else {
				for(int i = 0; i < NDECADES; i ++) {
					rank[i] = Integer.parseInt(stk.nextToken());
				}
				break;
			}
		}
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		return rank[decade];
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		String res = "";
		res+=name+" ";
		ArrayList<Integer>list = new ArrayList<>();
		for(int i = 0; i < NDECADES; i++) {
			list.add(rank[i]);
		}
		res+=list;
		return res;
	}
}

