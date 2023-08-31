/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {
	
	/**variable info helps us to store information of each entered player to paint grap*/
	private Map<String, ArrayList<Integer>>info = new HashMap<>();
	
	/**variable colours helps us to match colours to the players*/
	private Map<String, Integer>colors = new HashMap<>();
	

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		//	 You fill this in //
		info.clear();
		colors.clear();
		color = 0;
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/	
	public void addEntry(NameSurferEntry entry) {
		ArrayList<Integer>rating = new ArrayList<>();
		
		/**storing information about ranks to each decades*/
		for(int i = 0 ;  i  < NDECADES; i ++) {
			rating.add(entry.getRank(i));
		}
		/**we only save information if there is information about this name in the file*/
		if(entry.getName()!=null) {
			info.put(entry.getName(), rating);
		}
		/**save information about colours for each name*/
		if(!colors.containsKey(entry.getName())) {
			colors.put(entry.getName(), color);
			color++;
		}
	}
	
	/**variable to count*/
	private int color;
	
	/**this method helps us to match colours to the entered names */
	private Color coloring(int col) {
		if(col % 4 == 0) {
			return Color.BLACK;
		} else if(col % 4 == 1) {
			return Color.RED;
		} else if(col % 4 == 2) {
			return Color.BLUE;
		}
		return Color.YELLOW;
	}
	
	/**painting graphic*/
	private void graphic() {
		if(info.size()!=0) {	
			for(String name: info.keySet()) {
				for(int i = 0 ; i < NDECADES; i++) {
					grap(name, info.get(name));
				}
			}
		}
	}
	
	/**this void providing painting graphic of entered name*/
	private void grap(String name, ArrayList<Integer>list) {
		/**as we know we should not paint graphic on whole canvas. we have got
		 * area which is surrounded by two margin*/
		int body = getHeight() - 2 * GRAPH_MARGIN_SIZE;
		
		/**coordinates for painting graphics */
		int x=0; int x1 = 0;
		double y = 0; double y1 = 0;
		
		/**this label helps me to write texts on canvas*/
		GLabel label = new GLabel("");
		
		for(int i = 0 ; i < NDECADES - 1; i++) {
			 x = i*(getWidth()/NDECADES);
			
			/**if list.get(i) != 0, it is a common situation*/
			if(list.get(i)!=0) {
				/**calculate y coordinate*/
				y = getHeight()-GRAPH_MARGIN_SIZE - (MAX_RANK-list.get(i)) * ((double)body/MAX_RANK);
				
				/**label to inform other whose graphic is painted*/
				label = new GLabel(name+" "+list.get(i));
			} else {
				/**but if list.get(i), this is individual case*/
				
				y = getHeight() - GRAPH_MARGIN_SIZE; // calculate y coordinate
				
				label= new GLabel((name+"*")); //label to inform customers whose graphic is painted
			}
			
			/**next two lines are for setting colour and adding label*/
			label.setColor(coloring(colors.get(name)));
			add(label, x, y);
			
			/**x coordinate of next decade*/
			x1 = (i+1)*(getWidth()/NDECADES);
			
			/**there are two case, when list.get(i) == 0 and when it is not*/
			if(list.get(i+1)!=0) {
				y1 = getHeight()-GRAPH_MARGIN_SIZE- (MAX_RANK-list.get(i+1)) * ((double)body/MAX_RANK);
			} else {
				y1 = getHeight() - GRAPH_MARGIN_SIZE;
			}
			
			/**after all decades information are printed, we have to write situation for last decade*/
			if(i == NDECADES -2) {
				
				/**as has been noted there are two cases too*/
				if(list.get(i+1) == 0) {
					label= new GLabel(name+"*");
				} else {
					label = new GLabel(name+ " "+list.get(i+1));
				}
				/**setting label colour and adding label*/
				label.setColor(coloring(colors.get(name)));
				add(label, x1, y1);
			}
			
			/**painting lines*/
			GLine line = new GLine(x, y, x1, y1);
			line.setColor(coloring(colors.get(name)));
			add(line);
		}
	}
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/	
	
	/**this void provide graphical painting*/
	public void update() {
		/**when new name is entered, we remove everything and then paint graphics*/
		removeAll();
		lines();
		graphic();
	}
	
	/**this void paints lines which separates decades from each other*/
	private void lines() {
		int stDec = START_DECADE;
		GLine line = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		add(line);
		
		GLine line2 = new GLine(0, getHeight()-GRAPH_MARGIN_SIZE, getWidth(), getHeight() -GRAPH_MARGIN_SIZE);
		add(line2);
		
		for(int i = 0 ; i < NDECADES; i ++) {
			GLine line3 = new GLine(i*(getWidth()/NDECADES), 0,i*(getWidth()/NDECADES),getHeight());
			add(line3);
			
			GLabel decades = new GLabel(""+stDec);
			add(decades, (i)*(getWidth()/NDECADES) + (getWidth()/NDECADES)/15 ,getHeight()-GRAPH_MARGIN_SIZE/4);
			stDec += 10;
		}
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
