package logic.tile;

import logic.Vector2D;
import logic.laser.LaserController;

public abstract class Tile extends LaserController {

	protected Vector2D pos;

	public Tile(Vector2D pos) {
		super();
		this.pos = pos;
	}

	public Vector2D getPos() {
		return pos;
	}
}
