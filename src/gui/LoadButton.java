package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class LoadButton extends JButton {

	private JFileChooser saveDialog;

	public LoadButton(final LevelStarter ls, final String filename, String label) {
		super(label);
		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				ls.startLevel(filename);
			}

		});
	}

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