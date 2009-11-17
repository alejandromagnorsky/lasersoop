package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 * Boton que permite al usuario elegir un nivel a cargar, o simplemente cargar
 * un archivo por default
 */

public class LoadButton extends JButton {

	private JFileChooser saveDialog;

	/**
	 * Constructor de LoadButton para cargar archivos por default. Recibe un
	 * LevelStarter para comenzar el nivel, la direccion del archivo y el titulo
	 * del boton
	 * 
	 * @param ls
	 * @param filename
	 * @param label
	 */
	public LoadButton(final LevelStarter ls, final String filename, String label) {
		super(label);
		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				ls.startLevel(filename);
			}

		});
	}

	/**
	 * Constructor de LoadButton para preguntar al usuario por el archivo.
	 * Recibe como parametro un LevelStarter, quien es el responsable de
	 * comenzar un nuevo nivel.
	 * 
	 * @param ls
	 */

	public LoadButton(final LevelStarter ls) {
		super("Cargar");
		saveDialog = new JFileChooser();

		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = saveDialog.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String filename = saveDialog.getSelectedFile().getPath();
					ls.startLevel(filename);
				}
			}
		});

	}

}