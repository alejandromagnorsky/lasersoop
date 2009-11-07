package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameMenu extends JFrame {

	private static final long serialVersionUID = 1L;

	private ImagePanel background;
	private ImagePanel title;


	public GameMenu() {
		super("Menu Principal");
		
		setBounds(100, 100, 900, 725);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton start = new JButton("Start game");
		start.setBounds(getWidth()/2-100, getHeight()/3+50, 200, 50);

		JButton load = new JButton("Load");
		load.setBounds(getWidth()/2-100, getHeight()/2, 200, 50);

		JButton exit = new JButton("Exit");
		exit.setBounds(getWidth()/2-100, getHeight()-50-getHeight()/3, 200, 50);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		try {
			background = new ImagePanel("bg.jpg");
			//title = new ImagePanel("title.jpg");
	
		} catch (Exception e) {
			// ERROR! (create an error frame)
			System.out.println(e);
		}
		
		background.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				System.out.println("El mouse entró al bg");
			}
			public void mouseExited(MouseEvent e) {
				System.out.println("El mouse salió del bg");
			}
		});


		add(start);
		add(exit);
		add(load);
		add(background);
	}
}
