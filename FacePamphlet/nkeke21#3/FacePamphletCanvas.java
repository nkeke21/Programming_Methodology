/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	private GLabel label; //GLabel for the text of the bottom
	
	public void showMessage(String msg) {
		// You fill this in
		if(label!=null) {
			remove(label);
		}
		label = new GLabel(msg);
		label.setFont(MESSAGE_FONT);
		double y = getHeight()-BOTTOM_MESSAGE_MARGIN;
		double x = (getWidth() - label.getWidth())/2;
		add(label, x, y);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
	
		/** the best way to do this task is decomposition */
		name(profile.getName());
		picture(profile.getImage());
		status(profile.getStatus(), profile.getName());
		friends(profile.getFriends());
	}
	
	/** creating this variable for the name of a user */
	private GLabel Name;

	/** void for creating GLabel for the name of a user */
	private void name(String str) {
		/** if GLabel already exist we should delete it, otherwise we create new GLabel over old one */
		if(Name!=null) {
			remove(Name);
		}
		
		/** creating new GLabel*/
		Name = new GLabel(str);
		Name.setFont(PROFILE_NAME_FONT);
		Name.setColor(Color.BLUE);
		
		/** variables for location of the GLabel*/
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN+Name.getAscent();
		
		add(Name, x, y);
	}
	
	/**creating variables for changing picture */
	private GRect rect;
	private GLabel NoImg;
	private GImage imag;
	
	
	private void picture(GImage img) {
		/** remove extra things(if something is painted on canvas it should be removed, otherwise new 
		 * GRect of GImage will be over old one */
		removing();
		
		/**if img is null we should create GRect with the GLabel("no image") */
		if(img == null) {
			double x = LEFT_MARGIN;
			double y = Name.getY()+ IMAGE_MARGIN;
			rect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(rect, x, y);
			
			NoImg = new GLabel("No Image");
			NoImg.setFont(PROFILE_IMAGE_FONT);
			double x1 = x + (rect.getWidth() - NoImg.getWidth())/2;
			double y1 = y + (rect.getHeight())/2;
			add(NoImg, x1, y1);
		
		} else {
			
			/**if img exists we should create new one */
			imag = img;
			imag.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);;
			double x = LEFT_MARGIN;
			double y = Name.getY()+ IMAGE_MARGIN;
			add(imag, x, y);
		}
	}
	
	/** remove extra things(if something is painted on canvas it should be removed, otherwise new 
	 * GRect of GImage will be over old one */
	private void removing() {
		
		if(rect != null) {
			remove(rect);
		}
		if(imag != null) {
			remove(imag);
		}
		if(NoImg != null) {
			remove(NoImg);
		}
	}
	
	/** creating GLabel for status */
	private GLabel status;
	
	/**void for changing status */
	private void status(String stat, String nam) {
		String sta ="";
		if(stat==null) {
			
			/**if no status exists we should inform a costumer about it */
			sta = "No current status" ;
		} else {
			
			/**if status exists we should inform a costumer about it */
			sta = nam +" is " + stat;
		}
		
		/**if GLabel for the status exists we should delete it, otherwise new one 
		 * will be created over old one */
		if(status != null) {
			remove(status);
		}
		
		/** creating new GLabel*/
		status = new GLabel(sta);
		status.setFont(PROFILE_STATUS_FONT);
		
		/** variables for location of the GLabel*/
		double x = LEFT_MARGIN;
		double y = rect.getY()+rect.getHeight()+STATUS_MARGIN;
		add(status, x, y);
	}
	
	/**creating variables for adding friend */
	private GLabel friends;
	private ArrayList<GLabel>gobj = new ArrayList<>();
	private GLabel lab;
	
	/** void for adding friend */
	private void friends(Iterator it) {
		
		/**if GLabels of friends exist we should delete them, otherwise new ones will be created over 
		 * old one */
		if(friends != null) {
			remove(friends);
		}
		
		/** creating GLabel ("Friends : ") */
		friends = new GLabel("Friends: ");
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends, getWidth()/2, rect.getY());
		
		/** y coordinate for GLabels of friends */
		double y = (double)friends.getY();
		
		/**if gobj is not null we should remove each element of this arrayList from the canvas */
		if(gobj!=null) {
			for(int i = 0 ; i < gobj.size(); i++) {
				remove(gobj.get(i));
			}
		}
		
		/**creating new arrayList */
		gobj = new ArrayList<>();
		
		/**reading iterator and then creating GLabels of friends on the canvas */ 
		while(it.hasNext()) {
			String line = (String)it.next();
			lab = new GLabel(line);
			lab.setFont(PROFILE_FRIEND_FONT);
			y+=lab.getAscent();
			gobj.add(lab);
			add(lab, getWidth()/2, y);
		}
	}
}
