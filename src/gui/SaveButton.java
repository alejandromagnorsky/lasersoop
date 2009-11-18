package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import logic.Level;
import logic.io.LevelLoader;
import logic.io.LevelSaver;

/**
 * Boton que permite al usuario seleccionar un archivo para guardar un nivel.
 */
public class SaveButton extends JButton {

	private JFileChooser saveDialog;

	/**
	 * Constructor. Requiere un TileSet para poder guardar toda la informacion
	 * del nivel en el archivo que el usuario elija.
	 * 
	 * @param tileset
	 */
	public SaveButton(final Level level) {
		super("Guardar");
		saveDialog = new JFileChooser();

		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = saveDialog.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String filename = saveDialog.getSelectedFile().getPath();
					LevelSaver save = new LevelSaver(filename);
					if (!LevelLoader.isInLevels(new File(filename))) {
						try {
							save.saver(level);
						} catch (IOException e1) {
							System.out.println(e1);
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"No se puede guardar en la carpeta levels");
					}
				}
			}
		});

	}

}
