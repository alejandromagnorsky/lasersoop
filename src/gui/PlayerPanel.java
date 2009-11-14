package gui;

import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logic.Player;

public class PlayerPanel extends JPanel{
	
	private Player player;
	private JLabel scoreLabel;
	
	public PlayerPanel(Player player){
		this.player = player;
		setLayout(null);
		setOpaque(false);
		add(getNameLabel());
		add(scoreLabel = getScoreLabel());
	}
	
	private JLabel getNameLabel(){
		JLabel label = new JLabel(player.getName());
		label.setBounds(5, 20, 100, 30);
		return label;
	}
	
	private JLabel getScoreLabel(){
		JLabel label = new JLabel("Puntaje: " + String.valueOf(player.getScore()));
		label.setBounds(5, 150, 100, 30);
		return label;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		this.scoreLabel.setText("Puntaje: "+ String.valueOf(player.getScore()));
	}	
		
}
