package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import logic.Level;

public class GameMenu extends JFrame implements LevelStarter {

	private static final long serialVersionUID = 1L;

	private ImagePanel background;

	public GameMenu() {
		super("Menu Principal");

		setLayout(null);
		setBounds(100, 100, 900, 725);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LoadButton start = new LoadButton(this, "levelTest.txt", "Comenzar juego");
		start.setBounds(getWidth() / 2 - 100, getHeight() / 3 + 50, 200, 50);

		LoadButton loadButton = new LoadButton(this);
		loadButton.setBounds(getWidth() / 2 - 100, getHeight() / 2, 200, 50);

		JButton exit = new JButton("Finalizar");
		exit.setBounds(getWidth() / 2 - 100,
				getHeight() - 50 - getHeight() / 3, 200, 50);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		try {
			background = new ImagePanel("bg.jpg");
			background.setBounds(0, 0, 900, 725);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error cargando recursos.");
		}

		add(start);
		add(exit);
		add(loadButton);
		add(background);

	}

	public void startLevel(String filename) {
		try {
			System.out.println(filename);
			Level level = new Level(filename);
			setVisible(false);
		} catch (Exception exc) {
			System.out.println(exc);
			JOptionPane.showMessageDialog(null,
					"Archivo incorrecto o corrupto.");
		}
	}
}
