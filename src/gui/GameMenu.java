package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import logic.Level;

public class GameMenu extends JFrame {

	private static final long serialVersionUID = 1L;

	private ImagePanel background;

	private JFileChooser openDialog;

	private JButton loadButton;

	private String filename;

	public GameMenu() {
		super("Menu Principal");

		setLayout(null);

		setBounds(100, 100, 900, 725);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton start = new JButton("Start game");
		start.setBounds(getWidth() / 2 - 100, getHeight() / 3 + 50, 200, 50);

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startLevel();
			}
		});

		// Default lvl
		filename = "levelTest.txt";

		JButton loadButton = new JButton("Load");
		loadButton.setBounds(getWidth() / 2 - 100, getHeight() / 2, 200, 50);

		openDialog = new JFileChooser(new File(""));

		openDialog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				filename = openDialog.getSelectedFile().getPath();
				startLevel();
			}

		});

		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOpenDialog();
			}
		});

		JButton exit = new JButton("Exit");
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
			// ERROR! (create an error frame)
			System.out.println(e);
		}

		add(start);
		add(exit);
		add(loadButton);
		add(background);
		// add(start2);

	}

	private void startLevel() {
		try {
			Level level = new Level(filename);
			setVisible(false);
		} catch (Exception exc) {
			System.out.println(exc);
			JOptionPane.showMessageDialog(null,
					"Archivo incorrecto o corrupto.");
		}
	}

	private void showOpenDialog() {
		openDialog.showOpenDialog(loadButton);
	}
}
