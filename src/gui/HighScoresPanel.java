package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.Player;
import logic.io.Highscores;

public class HighScoresPanel extends JPanel{
	
	private String levelName;
	
	public HighScoresPanel(String levelName) throws IOException{
		this.levelName = levelName;
		Highscores hs = new Highscores(levelName);
		/* VER BIEN ESTE TEMA DE QUE DEVUELVE EN CASO DE FALSE */
		if ( !hs.loader() ){
			System.out.println("TEMP----- tira una excepcion onda archivo de scorsinvalido");
		}
		
		setLayout(null);
		JLabel label = new JLabel("TOP10 Players");
		label.setBounds(30, 30, 100, 30);
		
		add(getLevelLabel());
		add(label);
		
		JLabel l;
		Player[] scores = hs.getScores();
		for (int i=0; i<10 && scores[i] != null; i++){
			l = new JLabel(i+1 + ". " + scores[i].getName() + " - " + scores[i].getScore());
			l.setBounds(10, 40 + i*20, 100, 30);
			add(l);
		}
		
		
	}
	
	public JLabel getLevelLabel(){
		JLabel l = new JLabel(levelName.toUpperCase());
		l.setBounds(10, 10, 100, 30);
		return l;
	}
	
	public JLabel getScoreLabel(Player p){
		JLabel l = new JLabel(p.getName() + " - " + p.getScore());
		l.setBounds(10, 10, 100, 30);
		return l;
	}
		
}
