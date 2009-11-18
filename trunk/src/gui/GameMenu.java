package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import logic.Level;

/**
 * Menu principal del jeugo. Contiene botones para comenzar un juego, cargar una
 * partida, ver los puntajes maximos o salir.
 * 
 */
public class GameMenu extends JFrame implements LevelStarter {

	private static final long serialVersionUID = 1L;

	private ImagePanel background;
	GameFrame game;

	public GameMenu() {
		super("Menu Principal");

		setLayout(null);
		setBounds(100, 100, 900, 725);

		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2
				- getHeight() / 2);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LoadButton start = new LoadButton(this, Level.getFirstLevel(),
				"Comenzar juego");
		start.setBounds(getWidth() / 2 + 125, getHeight() / 4, 200, 50);

		LoadButton loadButton = new LoadButton(this);
		loadButton
				.setBounds(getWidth() / 2 + 125, getHeight() / 4 + 75, 200, 50);

		JButton hs = new JButton("Ver mejores puntajes");
		hs.setBounds(getWidth() / 2 + 125, getHeight() / 4 + 150, 200, 50);
		hs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					HighScoresWindow hsw = new HighScoresWindow(Level
							.getFirstLevel());
					hsw.setVisible(true);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(null,
							"Archivo incorrecto o corrupto.");
				}
			}
		});
		
		final InstructionsFrame instructions = new InstructionsFrame();
		

		JButton showInstructions = new JButton("Ver instrucciones");
		showInstructions.setBounds(getWidth() / 2 + 125, getHeight() / 4 + 225, 200, 50);
		showInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructions.setVisible(true);
			}
		});

		JButton exit = new JButton("Finalizar");
		exit.setBounds(getWidth() / 2 + 125, getHeight() / 4 + 300, 200, 50);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		try {
			background = new ImagePanel("src/resources/bg.jpg");
			background.setBounds(0, 0, 900, 725);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error cargando recursos.");
		}

		add(showInstructions);
		add(start);
		add(loadButton);
		add(hs);
		add(exit);

		add(background);

	}

	public void startLevel(String filename) {
		setVisible(false);
		game = new GameFrame(this, filename);
		game.setVisible(true);
	}
}
