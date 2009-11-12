package gui;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ImageButton extends ImagePanel {

	private static final long serialVersionUID = 1L;
	private ImagePanel hoverImage;
	private boolean hover;

	public ImageButton(String path, String hoverPath) throws IOException {
		super(path);
		hoverImage = new ImagePanel(hoverPath);
		hover = false;
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				hover = true;
				repaint();
			}
			public void mouseExited(MouseEvent e) {
				hover = false;
				repaint();
			}
		});

	}

	public void paint(Graphics g) {
		if (hover)
			hoverImage.paint(g);
		else
			super.paint(g);
	}
}
