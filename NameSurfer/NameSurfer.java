/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;
//Program implements NameSurferConstants 
public class NameSurfer extends Program implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	/**import NameSurferDataBase calss*/
	private NameSurferDataBase racxa = new NameSurferDataBase(NAMES_DATA_FILE);
	
	/** import graph class*/
	private NameSurferGraph graph; 
	
	/**field for entering text */
	private JTextField field;
	
	/**creating nameSurfer*/
	public void init() {
		interactors();
	    // You fill this in, along with any helper methods //
		
		graph = new NameSurferGraph(); 
		add(graph);
		addActionListeners();
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */	
	/** this void contains buttons and TextFields*/
	private void interactors() {
		JLabel label = new JLabel("name");
		add(label, SOUTH);
		
		field = new JTextField(10);
		add(field, SOUTH);
		field.addActionListener(this);
		
		/**this button paints a graph when name is entered */
		JButton graph = new JButton("Graph");
		add(graph, SOUTH);
		
		/**this button removes all graph which are painted */
		JButton clear = new JButton("Clear");
		add(clear, SOUTH);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		/**if we press graph we should call graph class methods 
		 * like: addEntry and update to paint graphic on canvas*/	
		if(e.getActionCommand().equals("Graph")) {	
			if(racxa.findEntry(field.getText())!=null) {
				graph.addEntry(racxa.findEntry(field.getText()));
				graph.update();
			}
			field.setText("");
			
		/**clear button calls graph class methods: clear and update, to 
		 * remove graphics and then paint lines. */
		}else if(e.getActionCommand().equals("Clear")) {
			graph.clear();
			graph.update();
		}
	}
}
