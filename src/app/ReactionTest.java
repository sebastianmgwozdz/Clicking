package app;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;

// Test reaction times, user must click the button once it changes color to green.
public class ReactionTest implements Game {
	private long startTime;
	private JButton button;
	private JFrame currentFrame;
	private JFrame menuFrame;
	
	public ReactionTest(JFrame menuFrame) {
		this.currentFrame = new JFrame();
		this.menuFrame = menuFrame;
	}
	
	public void performRound() {
		menuFrame.setVisible(false);
		JButton button = createButton();
		
		currentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		currentFrame.getContentPane().add(button);
		currentFrame.pack();
		currentFrame.setVisible(true);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		currentFrame.setLocation(dim.width / 2 - currentFrame.getSize().width / 2, dim.height / 2 - currentFrame.getSize().height / 2);
		
		waitDelay();
	}
	
	public JButton createButton() {
		button = new JButton();
		
		button.setPreferredSize(new Dimension(1000, 650));
		button.setEnabled(false);
		
		// When clicked, reaction time is recorded and displayed as a dialog 
		button.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		    	 long reactionTime = System.currentTimeMillis() - startTime;
		         JOptionPane.showMessageDialog(currentFrame, "Reaction time: " + reactionTime + " ms");
		         
		         // Force window close to allow for next round
		         currentFrame.dispatchEvent(new WindowEvent(currentFrame, WindowEvent.WINDOW_CLOSING));
		         menuFrame.setVisible(true);
		     }
		   }
		 );
		
		return button;
	}
	
	// After a random amount of time (2.5 - 6 seconds), enables the button and turns it green
	public void waitDelay() {
		long delay = ThreadLocalRandom.current().nextLong(2500, 6000);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		button.setBackground(Color.green);
		button.setEnabled(true);
		startTime = System.currentTimeMillis();
	}
	
	
}
