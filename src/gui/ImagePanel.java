package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel que contiene una imagen 
 *
 */
public class ImagePanel extends JPanel {

	private String path;
	protected Image img;

	/**
	 * Constructor que requiere de un String para cargar la imagen. 
	 * @param path
	 * @throws IOException
	 */
	public ImagePanel(String path) throws IOException {
		this.path = path;
		img = ImageIO.read(new File(path));
	}

	/**
	 * Metodo para dibujar la imagen en el panel.
	 */
	public void paint(Graphics g) {
		if (img != null)
			g.drawImage(img, 0, 0, this);
	}
}
