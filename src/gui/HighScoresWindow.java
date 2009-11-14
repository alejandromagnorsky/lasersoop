package gui;

import javax.swing.JFrame;
import logic.Player;

public class HighScoresWindow extends JFrame{
	
	public HighScoresWindow(Player player, String name) {
		super("High Scores: " + name);
	}
		
}
