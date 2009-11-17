package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
import logic.Level;

public class HighScoresWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public HighScoresWindow(Level level) throws IOException{
		super("Highscores: " + level.getName());
		
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setBounds(- getWidth()/2 , - getHeight()/2, 250, 500);
		
		setContentPane(new HighScoresPanel(level.getName()));
	}
		
}
