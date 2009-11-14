package gui;

import java.awt.Color;
import javax.swing.JFrame;
import logic.Level;
import logic.Vector2D;
import logic.mirror.Mirror;
import logic.tile.SimpleTile;
import logic.tile.Tile;
import logic.tileset.TileSet;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int CELL_SIZE = 50;
	private BoardPanel bp;
	private TileSet tileset;
	private Level currentLevel;
	private TileManager tileManager;

	public void clearScreen() {
		for (Tile itr : tileset)
			new SimpleTile(itr.getPos()).drawTile(tileManager, bp);
	}

	public GameFrame(TileSet tileset, Level currentLevel) {

		this.tileset = tileset;
		this.currentLevel = currentLevel;
		setLayout(null);
		setSize(tileset.getCols() * CELL_SIZE + 40, tileset.getRows()
				* CELL_SIZE + 40);

		bp = new BoardPanel(tileset.getCols(), tileset.getRows(), CELL_SIZE);
		bp.setBackground(Color.WHITE);
		bp.setGridColor(Color.GRAY);

		tileManager = new TileManager();

		tileManager.loadTiles();
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

				if (checkTileAt(new Vector2D(sourceRow, sourceColumn))) {

					getTileSet().swapTiles(
							new Vector2D(sourceRow, sourceColumn),
							new Vector2D(targetRow, targetColumn));

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
		this.setResizable(false);

	}

	private TileSet getTileSet() {
		return tileset;
	}

	private Level getCurrentLevel() {
		return currentLevel;
	}

	private void updateScreen() {
		clearScreen();
		for (Tile itr : tileset)
			itr.drawTile(tileManager, bp);
	}

	private boolean checkTileAt(Vector2D pos) {
		if (tileset.at(pos) instanceof Mirror)
			return true;
		else
			return false;
	}

}
