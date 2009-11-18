package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Ventana que contiene las instrucciones del juego
 *
 */
public class InstructionsFrame extends JFrame {

	public InstructionsFrame() {
		super("Instrucciones");

		setLayout(null);
		setBounds(100, 100, 850, 855);

		setResizable(false);

		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2,  size.height / 2
				- getHeight() / 2);

		ImagePanel instructions = null;
		try {
			instructions = new ImagePanel("src/resources/instrucciones.jpg");
			instructions.setBounds(0, 0, 900, 900);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error cargando recursos.");
		}
		
		
		add(instructions);
	}
}
