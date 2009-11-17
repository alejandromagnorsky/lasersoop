package gui;

import logic.Vector2D;
import logic.mirror.Mirror;
import logic.tile.Tile;

/**
 * Listener para el juego. Controla los dos eventos que pueden haber en el
 * juego.
 * 
 */
public class GameListener implements BoardPanelListener {

	private GameFrame gm;

	public GameListener(GameFrame gm) {
		this.gm = gm;
	}

	/**
	 * Cada vez que el usuario clickea un tile, si es un espejo, rota. Este
	 * método está optimizado para no actualizar a menos que haya algun cambio
	 * de direccion de laser.
	 */
	public void cellClicked(int row, int column) {

		Tile t = gm.getTileSet().at(new Vector2D(row, column));
		if (t instanceof Mirror) {
			((Mirror) t).rotate();
			if (t.countLasers() != 0)
				gm.getCurrentLevel().update();

			gm.updateScreen();
		}
	}

	/**
	 *	Permite al usuario arrastrar tiles de un punto A a un punto B en la pantalla.
	 */
	public void cellDragged(int sourceRow, int sourceColumn, int targetRow,
			int targetColumn) {
		Tile t = gm.getTileSet().at(new Vector2D(sourceRow, sourceColumn));
		if (t instanceof Mirror) {

			gm.getTileSet().swapTiles(new Vector2D(sourceRow, sourceColumn),
					new Vector2D(targetRow, targetColumn));

			gm.getCurrentLevel().update();

			gm.updateScreen();

			System.out.println("Celda arrastrada desde " + sourceRow + ", "
					+ sourceColumn + " hasta " + targetRow + ", "
					+ targetColumn);
		} else {
			System.out.println("Accion incorrecta: imposible mover la celda.");

		}

	}
}
