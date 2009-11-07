package logic.tile;

import logic.Vector2D;

public abstract class MovableTile extends Tile {

	public MovableTile(Vector2D pos) {
		super(pos);
	}

	public void setPos(Vector2D pos) {
		this.pos = pos;
	}
}
