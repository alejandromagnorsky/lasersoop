package gui;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFrame;

import logic.tile.Origin;
import logic.tile.Tile;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int CELL_SIZE = 30;

	private BoardPanel bp;
	private Image img1, img2;

	private Vector<Image> tileImages;

	public void loadTiles() {
		try {
			// Load misc. images
			tileImages.add(ImageUtils.loadImage("resources/source.png"));
			tileImages.add(ImageUtils.loadImage("resources/trap.png"));
			tileImages.add(ImageUtils.loadImage("resources/wall.png"));
			tileImages.add(ImageUtils.loadImage("resources/target.png"));

			// Load laser image
			tileImages.add(ImageUtils.loadImage("resources/laser.png"));

			// Load mirror images
			tileImages.add(ImageUtils.loadImage("resources/half-laser.png"));
			tileImages.add(ImageUtils.loadImage("resources/double-mirror.png"));
			tileImages.add(ImageUtils.loadImage("resources/simple-mirror.png"));
			tileImages.add(ImageUtils.loadImage("resources/split-mirror.png"));

		} catch (Exception e) {
			System.out.println("Error loading images.");
		}
	}
	/*
	public void clearScreen(){
		
		for(int i=0;i<bp.get)
		
		bp.setImage(sourceRow, sourceColumn, tileImages.elementAt(3));
	}
*/
	public GameFrame() {

		setLayout(null);
		setSize(10 * CELL_SIZE + 40, 10 * CELL_SIZE + 40);

		tileImages = new Vector<Image>();

		this.loadTiles();

		bp = new BoardPanel(10, 10, CELL_SIZE);
		bp.setBackground(Color.WHITE);
		bp.setGridColor(Color.GRAY);

		bp.setListener(new BoardPanelListener() {
			public void cellClicked(int row, int column) {
				System.out.println("Clic en " + row + ", " + column);
				bp.setImage(row, column, tileImages.elementAt(0));
				bp.repaint();
			}

			public void cellDragged(int sourceRow, int sourceColumn,
					int targetRow, int targetColumn) {
				
				System.out.println("Celda arrastrada desde " + sourceRow + ", "
						+ sourceColumn + " hasta " + targetRow + ", "
						+ targetColumn);
				
				bp.setImage(sourceRow, sourceColumn, tileImages.elementAt(3));
				bp.setImage(targetRow, targetColumn, tileImages.elementAt(4));
				bp.repaint();
			}

		});

		add(bp);

	}
}
