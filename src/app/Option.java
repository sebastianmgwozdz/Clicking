package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Option extends JPanel implements ActionListener {
	private static String smallString = "Small";
	private static String mediumString = "Medium";
    private static String largeString = "Large";
    public String selectedItem;
    private JLabel picture;
    
    public Option() throws IOException {
        super(new GridBagLayout());
 
        //Create the radio buttons.
        JRadioButton smallButton = new JRadioButton(smallString);
        smallButton.setMnemonic(KeyEvent.VK_B);
        smallButton.setActionCommand(smallString);
 
        JRadioButton mediumButton = new JRadioButton(mediumString);
        mediumButton.setMnemonic(KeyEvent.VK_C);
        mediumButton.setActionCommand(mediumString);
        mediumButton.setSelected(true);
        selectedItem = mediumString;
 
        JRadioButton largeButton = new JRadioButton(largeString);
        largeButton.setMnemonic(KeyEvent.VK_D);
        largeButton.setActionCommand(largeString);
        
        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(smallButton);
        group.add(mediumButton);
        group.add(largeButton);
 
        //Register a listener for the radio buttons.
        smallButton.addActionListener(this);
        mediumButton.addActionListener(this);
        largeButton.addActionListener(this);
 
        //Set up the picture label.
        BufferedImage bullseye = ImageIO.read(new File("images/Medium.png"));
        picture = new JLabel(new ImageIcon(bullseye));
 
        //The preferred size is hard-coded to be the width of the
        //widest image and the height of the tallest image.
        picture.setPreferredSize(new Dimension(150, 150));
 
 
        //Put the radio buttons in a column in a panel.
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(smallButton);
        radioPanel.add(mediumButton);
        radioPanel.add(largeButton);
 
        add(radioPanel);
        
        GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(0, 10, 0, 10);
        add(picture, gc);
    }
    
    public void actionPerformed(ActionEvent e) {
        picture.setIcon(new ImageIcon("images/" + e.getActionCommand() + ".png"));
        selectedItem = e.getActionCommand();
    }
    
    public String getSelectedItem() {
    	return selectedItem;
    }
    
}
