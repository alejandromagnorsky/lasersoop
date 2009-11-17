package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
import logic.Level;

public class HighScoresWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public HighScoresWindow(String levelName) throws IOException{
		super("Highscores: " + levelName);
		
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		
		setBounds(size.width / 2 - getWidth() / 2 + 280, size.height / 2 - getHeight() / 2 - 213, 250, 325);
		
		setContentPane(new HighScoresPanel(levelName));
	}
		
}
