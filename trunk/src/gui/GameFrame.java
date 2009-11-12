package gui;

import java.awt.Color;
import java.awt.Image;
import java.util.Vector;

import javax.swing.JFrame;

import logic.Level;
import logic.Vector2D;
import logic.mirror.DoubleMirror;
import logic.mirror.Mirror;
import logic.mirror.SimpleMirror;
import logic.tile.Goal;
import logic.tile.Origin;
import logic.tile.SimpleTile;
import logic.tile.Tile;
import logic.tile.Trap;
import logic.tile.Wall;
import logic.tileset.TileSet;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int CELL_SIZE = 50;
	private BoardPanel bp;
	private Vector<Image> tileImages;
	private TileSet tileset;
	private Level currentLevel;

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

	public GameFrame(TileSet tileset, Level currentLevel) {

		this.tileset = tileset;
		this.currentLevel = currentLevel;
		setLayout(null);
		setSize(tileset.getCols() * CELL_SIZE + 40, tileset.getRows()
				* CELL_SIZE + 40);

		tileImages = new Vector<Image>();
		bp = new BoardPanel(tileset.getCols(), tileset.getRows(), CELL_SIZE);
		bp.setBackground(Color.WHITE);
		bp.setGridColor(Color.GRAY);

		loadTiles();
		clearScreen();
		currentLevel.update();
		updateScreen();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		bp.setListener(new BoardPanelListener() {
			public void cellClicked(int row, int column) {

				Tile t = getTileSet().at(new Vector2D(row, column));
				if (t instanceof Mirror) {
					((Mirror) t).rotate();
					if (t.countLasers() != 0)
						getCurrentLevel().update();

					updateScreen();

					bp.repaint();
				}
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
			
					getCurrentLevel().update();

					updateScreen();

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

	private Level getCurrentLevel() {
		return currentLevel;
	}

	private void updateScreen() {
		clearScreen();
		for (int i = 0; i < tileset.getCols(); i++)
			for (int j = 0; j < tileset.getRows(); j++) {
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
		int i;
		if (t instanceof SimpleTile)
			i = 0;
		else if (t instanceof Origin)
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
			i = 9;

		Image tileImage;
		int halfLaser = 0;

		if (t instanceof Mirror) {
			Mirror m = (Mirror) t;
			int times = 0;
			switch (m.getDegree()) {
			case 135:
				times = 0;
				break;
			case 45:
				times = 1;
				break;
			case 315:
				times = 2;
				break;
			case 225:
				times = 3;
				break;
			}
			tileImage = ImageUtils.rotateImage(tileImages.elementAt(i), times);
			halfLaser = 1;

		} else if (t instanceof Origin) {

			int times = ((Origin) t).getOrientation();
			tileImage = ImageUtils.rotateImage(tileImages.elementAt(i), times);

		} else {
			tileImage = tileImages.elementAt(i);
		}

		bp.appendImage(t.getPos().getX(), t.getPos().getY(), tileImage);

		int count = t.countLasers();

		if (count > 0 && !(t instanceof Origin)) {

			int times = t.getLastLaser().getAngle() / 90;

			Image tmpLaser = ImageUtils.rotateImage(tileImages
					.elementAt(5 + halfLaser), times + 1);

			bp.appendImage(t.getPos().getX(), t.getPos().getY(), tmpLaser);
		}
	}
}
