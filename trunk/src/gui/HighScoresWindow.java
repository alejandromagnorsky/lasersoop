package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import logic.Player;

public class HighScoresWindow extends JFrame{
	
	public HighScoresWindow(Player player, String levelName) throws IOException{
		super("Highscores: " + levelName);
		
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setBounds(size.width / 2 - getWidth()/2, size.height / 2
				- getHeight()/2, 500, 500);
		
		setContentPane(new HighScoresPanel(levelName));
	}
		
}
