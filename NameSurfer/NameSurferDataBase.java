import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.imageio.IIOException;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */	
	/**this map put takes name as a key and takes a line of name as a value*/
	private HashMap<String, String>map;
		
	/**reading file to store information about all names*/
	public NameSurferDataBase(String filename) {
		// You fill this in //
		map = new HashMap<>();
		/**next lines are for reading file and storing information into the map*/
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while(true) {
				String res = rd.readLine();
				if(res != null) {
					map.put(name(res).toLowerCase(), res);
				}else {
					break;
				}
			}
			rd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		if(map.containsKey(name.toLowerCase())) {
			NameSurferEntry res = new NameSurferEntry(map.get(name.toLowerCase()));
			return res;
		} 
		return null;
	}
	
	/**this method helps us to separate name from line*/
	private String name(String p) {
		StringTokenizer stk = new StringTokenizer(p);
		return stk.nextToken();
	}
}

