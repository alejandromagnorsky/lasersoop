package logic.tile;

import gui.BoardPanel;
import gui.TileManager;
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

	public boolean stopLaser() {
		return false;
	}

	public boolean shootLaser() {
		return false;
	}
	
	public abstract boolean canSwap();

	public abstract void translate(Vector2D dest);

	public abstract void drawLasers(TileManager tm, BoardPanel bp);

	public abstract void drawTile(TileManager tm, BoardPanel bp);
}
