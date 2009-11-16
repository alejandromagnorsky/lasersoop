package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Player;

public class SidebarPanel extends JPanel {

	private PlayerPanel playerPanel;

	public SidebarPanel(final GameFrame gameFrame, final Player player) {
		this.playerPanel = new PlayerPanel(player);

		setBounds(gameFrame.getWidth() - 140, 0, 140, gameFrame.getHeight());

		setLayout(null);

		playerPanel.setBounds(0, 0, getWidth(), 100);

		SaveButton saveButton = new SaveButton(gameFrame.getTileSet());
		saveButton.setBounds(5, 80, getWidth() - 30, 20);

		LoadButton loadButton = new LoadButton(gameFrame);
		loadButton.setBounds(5, 110, getWidth() - 30, 20);
		
		
		JButton menuButton = new JButton("Menu");
		menuButton.setBounds(5, 140, getWidth() - 30, 20);

		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameFrame.openMenu();
			}
		});
		
		
		JButton highscoreButton = new JButton("Puntajes");
		highscoreButton.setBounds(5, 170, getWidth() - 30, 20);
		
		highscoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					HighScoresWindow hsw = new HighScoresWindow(player, gameFrame.getCurrentLevel().getName());
					hsw.setVisible(true);
				} catch (IOException exc) {
					System.out.println(exc);
					JOptionPane.showMessageDialog(null,
							"Archivo incorrecto o corrupto.");
				}
			}
		});
		
		JButton exitButton = new JButton("Salir");
		exitButton.setBounds(5, 200, getWidth() - 30, 20);
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		add(playerPanel);
		add(highscoreButton);
		add(exitButton);
		add(menuButton);
		add(saveButton);
		add(loadButton);
	}

}