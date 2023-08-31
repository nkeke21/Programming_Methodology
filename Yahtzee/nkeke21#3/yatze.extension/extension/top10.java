import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class top10 {
	private ArrayList<String>list;
	public top10() {
		/** read the file*/
		String line = "a";
		list = new ArrayList<String>();
		try {
			BufferedReader rd = new BufferedReader(new FileReader("top10.txt"));
			while (line != null) {
				line = rd.readLine();
				if(line != null) {
					list.add(line);
				} 
			}
			rd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		scorefull();
	}
	private boolean p1 = false;
	private int score = 0;
	private int[] scoretp = new int[10];
	
	/**this void read the file and then make the massive which
	 * is sorted, this helps us to know who has got the most low score
	 */
	private void scorefull() {
		for(int i = 1 ; i < list.size(); i+= 2) {
			int supp = Integer.parseInt(list.get(i));
			scoretp[(i-1)/2] = supp;
		}
		Arrays.sort(scoretp);
	}
	
	/**input the score*/
	public void score(int input) {
		score = input;
	}
	private String user = "";
	
	/**input the user name*/
	public void user(String input) {
		user = input;
	}
	
	/** this void tells if a player is convenient to be in the top 10*/
	private void newpl() {
		for(int i = 0 ; i <list.size(); i++) {
			if(newpla()) {
				if(score > scoretp[0] && scoretp[0] != 0) {
					replace(scoretp[0]);
					p1 = true;
				} else if (scoretp[0] == 0) {
					list.add(user);
					list.add(intToString(score));
					p1 = true;
				}
			}
		}
	}
	/** return true if a new player can be in the top10*/
	public boolean moveTo10() {
		return p1;
	}
	
	/**this boolean helps us to know if a player is a new or not */
	private boolean newpla() {
		for(int i = 0 ; i < list.size(); i++) {
			if(user.equals(list.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	/** replacing old candidate of top 10*/
	private void replace(int m) {
		for(int i = 1; i < list.size(); i++) {
			if(list.get(i).equals(intToString(m))) {
				list.remove(list.get(i));
				list.remove(list.get(i-1));
				list.add(user);
				list.add(intToString(score));
				break;
			}
		}
	}
	
	/** void for making new list*/
	public void listing() {
		try {
			newpl();
			PrintWriter pr = new PrintWriter(new FileWriter("top10.txt"));
			for(int i = 0; i< list.size() ; i++) {
				if(!list.get(i).equals("")) {	
					pr.println(list.get(i));
					if(user.equals(list.get(i))) {
						if(score >0) {
							if(Integer.parseInt(intToString(score)) >= Integer.parseInt(list.get(i+1))){
								pr.println(intToString(score));
								list.remove(i+1);
								list.add(i+1, intToString(score));
								i++;
							}
						}
					} 
				}
			}
			pr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**return the list which we use in console*/
	public ArrayList returnList() {
		return list;
	}

	/** converting int to string */
	private String intToString(int input) {
		String result ="";
		while(input>=1) {
			result = (char)(input % 10 +'0') +result;
			input /= 10;
		}
		return result;
	}
}
