package logic.tile;

import logic.Vector2D;

/**
 * Representa las celdas que no tienen movimiento.
 *
 */
public abstract class StaticTile extends Tile {

	public StaticTile(Vector2D pos) {
		super(pos);
	}

}
