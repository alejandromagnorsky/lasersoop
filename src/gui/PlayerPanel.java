package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import logic.Player;

public class PlayerPanel extends JFrame{
	
	private Player player;
	
	public PlayerPanel(Player player){
		this.player = player;
				
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2
				- getHeight() / 2);

	}
		
}
