package gui;

import logic.Vector2D;
import logic.mirror.Mirror;
import logic.tile.Tile;

public class GameListener implements BoardPanelListener {

	private GameFrame gm;

	public GameListener(GameFrame gm) {
		this.gm = gm;
	}

	public void cellClicked(int row, int column) {

		Tile t = gm.getTileSet().at(new Vector2D(row, column));
		if (t instanceof Mirror) {
			((Mirror) t).rotate();
			if (t.countLasers() != 0)
				gm.getCurrentLevel().update();

			gm.updateScreen();
			gm.repaint();
		}
	}

	public void cellDragged(int sourceRow, int sourceColumn, int targetRow,
			int targetColumn) {
		Tile t = gm.getTileSet().at(new Vector2D(sourceRow, sourceColumn));
		if (t instanceof Mirror) {

			gm.getTileSet().swapTiles(new Vector2D(sourceRow, sourceColumn),
					new Vector2D(targetRow, targetColumn));

			gm.getCurrentLevel().update();

			gm.updateScreen();
			gm.repaint();

			System.out.println("Celda arrastrada desde " + sourceRow + ", "
					+ sourceColumn + " hasta " + targetRow + ", "
					+ targetColumn);
		} else {
			System.out.println("Accion incorrecta: imposible mover la celda.");

		}

	}
}
