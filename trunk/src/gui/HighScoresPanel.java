package gui;

import javax.swing.JPanel;
import logic.Player;

public class HighScoresPanel extends JPanel{
	
	private String levelName;
	private Player player;
	
	public HighScoresPanel(Player player, String levelName){
		this.player = player;
		this.levelName = levelName;
		
		// ACA VA A LLAMAR AL LOADER DE LOS HIGH SCORES
	}
		
}
