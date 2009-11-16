package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import logic.io.LevelSaver;
import logic.tileset.TileSet;

public class SaveButton extends JButton {

	private JFileChooser saveDialog;

	public SaveButton(final TileSet tileset) {
		super("Guardar");
		saveDialog = new JFileChooser();

		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = saveDialog.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String filename = saveDialog.getSelectedFile().getPath();
					LevelSaver save = new LevelSaver(filename);
					try {
						save.saver(tileset);
					} catch (IOException e1) {
						System.out.println(e1);
					}
				}
			}
		});

	}

}
