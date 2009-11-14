package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import logic.Player;

public class HighScoresPanel extends JFrame{
	
	private String levelName;
	private Player player;
	
	public HighScoresPanel(Player player, String levelName){
		this.player = player;
		this.levelName = levelName;
		
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2
				- getHeight() / 2);

	}
		
}
