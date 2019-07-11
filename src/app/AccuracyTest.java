package app;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.*;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

// Accuracy game where user tries to click a moving bullseye as fast as possible
public class AccuracyTest implements Game {
	private JFrame menuFrame;
	private long timeLastPressed;
	private int buttonCount = 0;
	private myMouseListener mml;
	
	public AccuracyTest(JFrame menu) {
		this.menuFrame = menu;
	}
	
	public void performRound() {
		createAndShowMenu(menuFrame);
	}

	// Run a single round of the game, including an options menu and the accuracy game itself
	public void run(String bullseyeSize) {
		menuFrame.setVisible(false);
		
		JFrame currentFrame = new JFrame();
		JOptionPane.showMessageDialog(currentFrame, "Click the bullseye as it moves around. The game will end once you take longer than 1.5 seconds to click the bullseye.");
		
		JPanel pane = new JPanel();
		mml = new myMouseListener();

		JButton button = initializeButton(currentFrame, bullseyeSize);
		
		pane.add(button);
		pane.addMouseListener(mml);
		configureFrame(currentFrame, pane);
	
		timeLastPressed = System.currentTimeMillis();
	}

	// Set the button's on click action
	public JButton initializeButton(JFrame current, String size) {
		final JButton button;
		
		try {
			BufferedImage bullseye = ImageIO.read(new File("images/Medium.png"));
			
			button = getBullseye(bullseye, size);
			
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					onButtonPress(button, current);
				}
			}
					);
			return button;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// Determine the specified size of the button, assign the correctly scaled image
	private JButton getBullseye(BufferedImage image, String size) {
		JButton button = new JButton();
		
		int width, height;
		switch (size) {
			case "Small": width = height = 50;
						  break;
			case "Large": width = height = 150;
						  break;
			default: width = height = 100;
		}
		
		button.setPreferredSize(new Dimension(width, height));
		Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		button.setIcon(new ImageIcon(scaledImage));
		
		return button;
	}
	
	// Describe what happens when the bullseye is clicked by the player
	public void onButtonPress(JButton button, JFrame current) {
		Random rand = new Random();
		
		// If the player takes longer than 1.5 seconds to click, the game is over
		if (System.currentTimeMillis() - timeLastPressed > 1500) {
			// Check if the user didn't play 
			if (buttonCount == 0) {
				JOptionPane.showMessageDialog(current, "Total accuracy: 0 / 0 (0%)");
			}
			else {
				BigDecimal percentage = new BigDecimal(buttonCount / ((double) mml.getClickCount() + buttonCount) * 100);
			    percentage = percentage.setScale(2, RoundingMode.HALF_UP);
			    // Display the number of successful clicks, total nubmer of clicks, and the success rate
				JOptionPane.showMessageDialog(current, "Total accuracy: " + buttonCount + " / " + (mml.getClickCount() + buttonCount) + " (" + percentage + "%" + ")");
			}
			
			// Closes the game and resets to main menu
			current.dispatchEvent(new WindowEvent(current, WindowEvent.WINDOW_CLOSING));
			menuFrame.setVisible(true);
			buttonCount = 0;
			mml.resetClickCount();
		}
		
		buttonCount++;
		
		// Place button in random location after each click
		int x = rand.nextInt(1200);
		int y = rand.nextInt(700);
		button.setLocation(x, y);
		timeLastPressed = System.currentTimeMillis();
	}
	
	// Basic setup for game window
	public void configureFrame(JFrame frame, JPanel pane) {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1300, 800));
		frame.getContentPane().add(pane);
		frame.pack();
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
	}
	
	// Initialize the accuracy game
	public void createAndShowMenu(JFrame main) {
		JFrame menu = new JFrame("Options");
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			// Display the option menu
			Option newContentPane = new Option();
			newContentPane.setOpaque(true); 
			menu.setContentPane(newContentPane);
			JButton button = new JButton("Done");
			
			// Set game to begin when option menu is closed
			button.addActionListener(new ActionListener() {
			     public void actionPerformed(ActionEvent ae) {
			    	 menu.dispose();
			         run(newContentPane.getSelectedItem());
			     }
			   }
			 );
			
			button.setPreferredSize(new Dimension(75, 75));
			
			newContentPane.add(button);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		menu.setPreferredSize(new Dimension(500, 500));
		menu.pack();
		menu.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		menu.setLocation(dim.width / 2 - menu.getSize().width / 2, dim.height / 2 - menu.getSize().height / 2);
	}
}
