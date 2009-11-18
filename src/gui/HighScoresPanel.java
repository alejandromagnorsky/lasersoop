package gui;

import java.io.IOException;
import java.util.TreeSet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logic.Player;
import logic.io.Highscores;

public class HighScoresPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private String levelName;
	
	public HighScoresPanel(String levelName) throws IOException{
		this.levelName = levelName;
		Highscores hs = new Highscores(levelName);
		hs.loader();
		setLayout(null);
		JLabel label = new JLabel("TOP10 Players");
		label.setBounds(30, 30, 100, 30);
		
		add(getLevelLabel());
		add(label);
		
		JLabel l;
		TreeSet<Player> scores = hs.getScores();
		int i = 1;
		for (Player p: scores){
			l = new JLabel(i++ + ". " + p.getName() + " - " + p.getScore());
			l.setBounds(10, 20 + i*20, 10*(p.getName().length()+p.getScore()), 30);
			add(l);
		}		
	}
	
	public JLabel getLevelLabel(){
		JLabel l = new JLabel(levelName.toUpperCase());
		l.setBounds(10, 10, 100, 30);
		return l;
	}		
}
