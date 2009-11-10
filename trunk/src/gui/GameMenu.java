package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import logic.Level;

public class GameMenu extends JFrame {

	private static final long serialVersionUID = 1L;

	private ImagePanel background;
	private ImagePanel title;


	public GameMenu() {
		super("Menu Principal");
		
		setLayout(null);
		
		setBounds(100, 100, 900, 725);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton start = new JButton("Start game");
		start.setBounds(getWidth()/2-100, getHeight()/3+50, 200, 50);
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Level level = new Level("levelTest.txt");
					setVisible(false);
				} catch (Exception exc) {
					System.out.println(exc);
				}

			}
		});

		JButton load = new JButton("Load");
		load.setBounds(getWidth()/2-100, getHeight()/2, 200, 50);

		JButton exit = new JButton("Exit");
		exit.setBounds(getWidth()/2-100, getHeight()-50-getHeight()/3, 200, 50);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		ImageButton start2 = null;
		
		try {
			background = new ImagePanel("bg.jpg");
			start2 = new ImageButton("simple-mirror.png", "empty-tile.png");
			start2.setBounds(200, 200, 230, 230);
			background.setBounds(0, 0, 900, 725);
			
		} catch (Exception e) {
			// ERROR! (create an error frame)
			System.out.println(e);
		}
		
		add(start);
		add(exit);
		add(load);
		add(background);
		//add(start2);
	
	}
}
