package gui;

import java.awt.Color;
import java.awt.Image;
import java.util.Vector;

import javax.swing.JFrame;

import logic.Vector2D;
import logic.mirror.*;
import logic.tile.*;
import logic.tileset.TileSet;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int CELL_SIZE = 50;
	private BoardPanel bp;
	private Vector<Image> tileImages;
	private TileSet tileset;

	public void loadTiles() {
		try {

			// Load empty tile
			tileImages.add(ImageUtils.loadImage("resources/empty-tile.png"));

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

	public void clearScreen() {

		for (int i = 0; i < tileset.getCols(); i++)
			for (int j = 0; j < tileset.getRows(); j++)
				bp.setImage(i, j, this.tileImages.elementAt(0));
	}

	public GameFrame(TileSet tileset) {

		this.tileset = tileset;

		setLayout(null);
		setSize(tileset.getCols() * CELL_SIZE + 40, tileset.getRows()
				* CELL_SIZE + 40);

		tileImages = new Vector<Image>();
		bp = new BoardPanel(tileset.getCols(), tileset.getRows(), CELL_SIZE);
		bp.setBackground(Color.WHITE);
		bp.setGridColor(Color.GRAY);

		loadTiles();
		clearScreen();
		updateLevel();

		bp.setListener(new BoardPanelListener() {
			public void cellClicked(int row, int column) {

				// Crear un alt text para mostrar que tile es
				// if( checkTileAt(new Vector2D(row, column)) )
				// bp.setImage(row, column, tileImages.elementAt(0));
				// bp.appendImage(row, column, tileImages.elementAt(4));
				// bp.repaint();
			}

			public void cellDragged(int sourceRow, int sourceColumn,
					int targetRow, int targetColumn) {

				if (checkTileAt(new Vector2D(sourceRow, sourceColumn))
						&& getTileSet().moveTile(
								new Vector2D(sourceRow, sourceColumn),
								new Vector2D(targetRow, targetColumn))) {

					updateLevel();

					bp.repaint();

					System.out.println("Celda arrastrada desde " + sourceRow
							+ ", " + sourceColumn + " hasta " + targetRow
							+ ", " + targetColumn);

				} else {
					System.out
							.println("Accion incorrecta: imposible mover la celda.");

				}

			}

		});

		add(bp);
		this.setResizable(true);

	}

	private TileSet getTileSet() {
		return tileset;
	}

	private void updateLevel() {
		clearScreen();
		for (int i = 0; i < tileset.getCols(); i++)
			for (int j = 0; j < tileset.getRows(); j++){
				addTile(tileset.at(new Vector2D(i, j)));
			}
				
	}

	private boolean checkTileAt(Vector2D pos) {
		if (tileset.at(pos) instanceof Mirror)
			return true;
		else
			return false;
	}

	public void addTile(Tile t) {
		// Later check which tile to print
		System.out.println("dngan");
		int i;
		if (t instanceof SimpleTile)
			i = 0;
		if (t instanceof Origin)
			i = 1;
		else if (t instanceof Trap)
			i = 2;
		else if (t instanceof Wall)
			i = 3;
		else if (t instanceof Goal)
			i = 4;
		else if (t instanceof DoubleMirror)
			i = 7;
		else if (t instanceof SimpleMirror)
			i = 8;
		else
			i = 0;
		System.out.println("cacsdasd");
		bp.appendImage(t.getPos().getX(), t.getPos().getY(), tileImages
				.elementAt(i));
		System.out.println("Hola");
	}
}
