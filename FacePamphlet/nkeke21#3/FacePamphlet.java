
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {
	/**creating private variables */
	
	/**Jtextfield where we enter person's name */
	private JTextField field;

	/**fields for change picture, change status and adding friends*/
	private JTextField changeStatus;
	private JTextField changePicture;
	private JTextField addFriend;
	private String entName = "";

	private FacePamphletDatabase base = new FacePamphletDatabase();
	private FacePamphletCanvas canvas;
	
	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */

	public void init() {
		// You fill this in
		/** adding canvas*/
		canvas = new FacePamphletCanvas();
		add(canvas);

		/** creating interactors */
		interactors();
		addActionListeners();
	}

	private void interactors() {
		// TODO Auto-generated method stub
		/** interactors located north */
		northInteractors();
		
		/**interactors located west */
		westInteractors();
	}

	/**void for the northInteractors*/
	private void northInteractors() {
		add(new JLabel("Name"), NORTH);

		field = new JTextField(TEXT_FIELD_SIZE);
		add(field, NORTH);
		field.addActionListener(this);

		/** creating buttons*/
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("LookUp"), NORTH);
	}

	/** void for the west interactors */
	private void westInteractors() {
		/**creating JtextField and button for the change status */
		changeStatus = new JTextField(TEXT_FIELD_SIZE);
		changeStatus.setActionCommand("Change Status");
		add(changeStatus, WEST);
		changeStatus.addActionListener(this);

		add(new JButton("Change Status"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		/**creating JtextField and button for the change picture */
		changePicture = new JTextField(TEXT_FIELD_SIZE);
		changePicture.setActionCommand("Change Picture");
		add(changePicture, WEST);
		changePicture.addActionListener(this);

		add(new JButton("Change Picture"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		/**creating JtextField and button for the add friend */
		addFriend = new JTextField(TEXT_FIELD_SIZE);
		addFriend.setActionCommand("Add Friend");
		add(addFriend, WEST);
		addFriend.addActionListener(this);

		add(new JButton("Add Friend"), WEST);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		
		/**in the next lines make our program do some tasks when we want to add friends, look up
		 * friends, deleting profiles and so on .... the best way to do this is to use
		 * decomposition and write private voids for each task */
		if (e.getActionCommand().equals("Add")) {
			Add();
		}
		if (e.getActionCommand().equals("LookUp")) {
			LookUp();
		}
		if (e.getActionCommand().equals("Delete")) {
			Delete();
		}
		if (e.getActionCommand().equals("Change Status")) {
			changeStatus();
		}
		if (e.getActionCommand().equals("Change Picture")) {
			changePicture();
		}
		if (e.getActionCommand().equals("Add Friend")) {
			addFriend();
		}
	}
	
	/** this void is responsible for adding new profile */
	private void Add() {
		
		/**if field is clear, we are not allowed to do something. so field must be filled */
		if (!field.getText().equals("")) {
			
			/**if entered account already exists we should move to this profile and tell a costumer about it*/
			if (base.containsProfile(field.getText())) {
				canvas.showMessage("A profile with the name " + field.getText() + " already exists");
				canvas.displayProfile(base.getProfile(field.getText()));
			} else {
			
				/**if entered profile doesn't exists, we should add it and tell a costumer about it*/
				FacePamphletProfile ragac = new FacePamphletProfile(field.getText());
				base.addProfile(ragac);
				canvas.showMessage("New profile created");
				canvas.displayProfile(base.getProfile(field.getText()));
			}
			
			/**we should remember which profiled is open now */ 
			entName = field.getText();
		}
	}
	/** void for deleting profile */
	private void Delete() {
		
		/**we should do task when field is filled */
		if (!field.getText().equals("")) {
			
			/**if profile doesn't exist we should inform a costumer about it */
			if (!base.containsProfile(field.getText())) {
				canvas.showMessage("A profile with the name "+ field.getText()+ " already exists");
				entName = "";
			} else {
				
				/**if profile exists we should remove profile and inform a costumer about it */
				canvas.removeAll();
				base.deleteProfile(field.getText());
				canvas.showMessage("Profile of " + field.getText()+ " deleted");
			}
		}
	}
	
	/** void for searching people */
	private void LookUp() {
		/**we should do tasks when field is filled */
		if (!field.getText().equals("")) {
			
			/** if profile doesn't exists we should tell a costumer about it*/
			if (!base.containsProfile(field.getText())) {
				canvas.showMessage("A profile with the name " +field.getText()+ " already exists");
			} else {
				
				/**if profile exists we should move to this profile */
				canvas.showMessage("Displaying " + field.getText());
				canvas.displayProfile(base.getProfile(field.getText()));
			}
		}
		
		/** we have to remember last searched account */
		entName = field.getText();
	}

	/**void for changing picture */
	private void changeStatus() {
		
		/** we should perform task when field of change status is filled */
		if (!changeStatus.getText().equals("")) {
			
			/** if profile exists we should add new status on this profile and tell a 
			 * costumer that status updated  */
			if (base.containsProfile(entName)) {
				base.getProfile(entName).setStatus(changeStatus.getText());
				canvas.displayProfile(base.getProfile(entName));
				canvas.showMessage("Status updated to "+ base.getProfile(entName).getStatus());
			} else {
				
				/**if profile doesn't exist we should tell a costumer to select a profile */
				canvas.showMessage("Please select a profile to change status");
			}
		}
		changeStatus.setText("");
	}

	/** void for changing picture */
	private void changePicture() {
		
		/** we should perform tasks when field of change picture is filled */
		if (!changePicture.getText().equals("")) {
			
			/**to add picture it is necessary that profile must be exists */
			if (base.containsProfile(entName)) {
				
				/** changing picture */
				GImage image = null;
				try {
					image = new GImage(changePicture.getText());
				} catch (ErrorException ex) {
					
				}
				if (image != null) {
					base.getProfile(entName).setImage(image);
					canvas.displayProfile(base.getProfile(entName));
					canvas.showMessage("Picture updated");
				} else {
					
					/** if image can't be added we should tell it to a costumer */
					canvas.showMessage("Unable to open image file");
				}
			} else {
				
				/** if profile doesn't exist we should tell it to a costumer*/
				canvas.showMessage("Please select a profile to change picture");
			}
			changePicture.setText("");
		}
	}

	/** void for adding friend */
	private void addFriend() {
		
		/** task should be performed when field of addFriend is filled */
		if (!addFriend.getText().equals("")) {
			
			/**for adding friend it is necessary that the profile must exist */
			if (base.containsProfile(entName)) {
				if (base.containsProfile(addFriend.getText())) {
					if(entName.equals(addFriend.getText())) {
						
						/**profile can not add itself */
						canvas.showMessage("you can not add yourself");
					}  else if (base.getProfile(entName).addFriend(addFriend.getText())) {
						
						/**if exact profile haven't got entered friend as a friend we should add it and 
						 * tell a costumer that new friend is added */
						base.getProfile(entName).addFriend(addFriend.getText());
						base.getProfile(addFriend.getText()).addFriend(entName);
						canvas.showMessage(addFriend.getText() +" added as a friend"); 
						canvas.displayProfile(base.getProfile(entName));
					} else {
						/**if exact profile have got entered friend we shouldn't add it and also
						 * we have to tell a costumer that he/she has already got this friend */ 
						canvas.showMessage(entName+ " already has " + addFriend.getText()+" as a friend");

					}
				} else {
					
					/**if entered friend doesn't exist we should tell it to a costumer */
					canvas.showMessage(addFriend.getText() + " does not exist.");
				}
			} else {
				
				/**if exact profile doesn't exists we should tell a costumer to select a profile */
				canvas.showMessage("Please select a profile to add friend");
			}
		}
		addFriend.setText("");
	}
}
