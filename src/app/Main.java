package app;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		initializeMenu();
	}
	
	public static void initializeMenu() {
		JFrame menuFrame = new JFrame("Menu");
		JPanel pane = new JPanel();
		setupPanel(pane, menuFrame);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.getContentPane().add(pane);
		menuFrame.pack();
		menuFrame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		menuFrame.setLocation(dim.width / 2 - menuFrame.getSize().width / 2, dim.height / 2 - menuFrame.getSize().height / 2);
	}
	
	private static void setupPanel(JPanel pane, JFrame menu) {
		pane.setPreferredSize(new Dimension(1000, 650));
		pane.setLayout(new GridBagLayout());
		ReactionTest rt = new ReactionTest(menu);
		pane.add(getButton(rt, "Test your reaction time!"), new GridBagConstraints());
		AccuracyTest at = new AccuracyTest(menu);
		pane.add(getButton(at, "Test your mouse accuracy!"));
	}
	
	public static JButton getButton(Game g, String label) {
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
