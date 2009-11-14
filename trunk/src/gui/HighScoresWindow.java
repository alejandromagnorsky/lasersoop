package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import logic.Player;

public class HighScoresWindow extends JFrame{
	
	public HighScoresWindow(Player player, String levelName) {
		super("High Scores: " + levelName);
		
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2
				- getHeight() / 2);
		
		setContentPane(new HighScoresPanel(player, levelName));
	}
		
}
