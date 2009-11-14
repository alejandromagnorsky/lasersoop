package logic.tile;

import logic.Vector2D;
import logic.laser.LaserController;
/**
 * Representa una celda en el tablero.
 *
 */
public abstract class Tile extends LaserController {

	protected Vector2D pos;

	protected Tile(Vector2D pos) {
		super();
		this.pos = pos;
	}

	public Vector2D getPos() {
		return pos;
	}
}
