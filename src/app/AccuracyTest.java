package app;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AccuracyTest implements Game {
	private JFrame menuFrame;
	private long timeSince;
	private long timeLastPressed;
	private int buttonCount = 0;
	private myMouseListener mml;
	
	public AccuracyTest(JFrame menu) {
		this.menuFrame = menu;
	}
	
	public void performRound() {
		createAndShowMenu(menuFrame);
	}

	public void run(String bullseyeSize) {
		menuFrame.setVisible(false);
		
		JFrame currentFrame = new JFrame();
		JOptionPane.showMessageDialog(currentFrame, "Click the bullseye as it moves around. The game will end once you take longer than 1.5 seconds to click the bullseye.");
		
		JPanel pane = new JPanel();
		mml = new myMouseListener();

		System.out.println(bullseyeSize);
		JButton button = initializeButton(currentFrame, bullseyeSize);
		
		pane.add(button);
		pane.addMouseListener(mml);
		configureFrame(currentFrame, pane);
	
		
		timeSince = 0;
		timeLastPressed = System.currentTimeMillis();
	}

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
	
	private JButton getBullseye(BufferedImage image, String size) {
		JButton button = new JButton();
		int width, height;
		System.out.println(size);
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
	
	public void onButtonPress(JButton button, JFrame current) {
		Random rand = new Random();
		
		if (System.currentTimeMillis() - timeLastPressed > 1500) {
			if (buttonCount == 0) {
				JOptionPane.showMessageDialog(current, "Total accuracy: 0 / 0 (0%)");
			}
			else {
				BigDecimal percentage = new BigDecimal(buttonCount / ((double) mml.getClickCount() + buttonCount) * 100);
			    percentage = percentage.setScale(2, RoundingMode.HALF_UP);
				JOptionPane.showMessageDialog(current, "Total accuracy: " + buttonCount + " / " + (mml.getClickCount() + buttonCount) + " (" + percentage + "%" + ")");
			}
			current.dispatchEvent(new WindowEvent(current, WindowEvent.WINDOW_CLOSING));
			menuFrame.setVisible(true);
			buttonCount = 0;
			mml.resetClickCount();
		}
		buttonCount++;
		int x = rand.nextInt(1200);
		int y = rand.nextInt(700);
		button.setLocation(x, y);
		timeLastPressed = System.currentTimeMillis();
	}
	
	public void configureFrame(JFrame frame, JPanel pane) {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1300, 800));
		frame.getContentPane().add(pane);
		frame.pack();
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
	}
	
	public void createAndShowMenu(JFrame main) {
		JFrame menu = new JFrame("Options");
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			Option newContentPane = new Option();
			newContentPane.setOpaque(true); 
			menu.setContentPane(newContentPane);
			JButton button = new JButton("Done");
			
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

	}
}
