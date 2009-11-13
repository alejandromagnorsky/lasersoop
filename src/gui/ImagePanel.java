package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private String path;
	protected Image img;

	public ImagePanel(String path) throws IOException {
		this.path = path;
		img = ImageIO.read(new File(path));
	}

	public void paint(Graphics g) {
		if (img != null)
			g.drawImage(img, 0, 0, this);
	}
}
