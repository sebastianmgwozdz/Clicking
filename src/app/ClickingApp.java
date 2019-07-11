package app;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ClickingApp {
	// TODO: Add readme and gifs to github 
	
	public static void main(String[] args) {
		ClickingApp game = new ClickingApp();
		game.initialize();
	}
	
	// Create the main menu, set it up (size/closing operation)
	public void initialize() {
		JFrame menuFrame = new JFrame("Menu");
		JPanel pane = new JPanel();
		setupPanel(pane, menuFrame);
		
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.getContentPane().add(pane);
		menuFrame.pack();
		menuFrame.setVisible(true);
		
		// Move the window to the center of the user's screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		menuFrame.setLocation(dim.width / 2 - menuFrame.getSize().width / 2, dim.height / 2 - menuFrame.getSize().height / 2);
	}
	
	// Create the panel that holds the buttons for the two different game modes
	private void setupPanel(JPanel pane, JFrame menu) {
		pane.setPreferredSize(new Dimension(1000, 650));
		pane.setLayout(new GridBagLayout());
		ReactionTest rt = new ReactionTest(menu);
		
		// Add spacing in between the buttons
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 10);
		
		pane.add(getButton(rt, "Test your reaction time!"), gc);
		AccuracyTest at = new AccuracyTest(menu);
		pane.add(getButton(at, "Test your mouse accuracy!"));
	}
	
	// Creates the button for a given game mode and set up the on press action
	public JButton getButton(Game g, String label) {
		JButton button = new JButton(label);
		
		button.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		    	 g.performRound();
		     }
		   }
		 );
		
		return button;
	}
}
