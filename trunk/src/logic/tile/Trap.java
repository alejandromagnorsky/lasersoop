package logic.tile;

import logic.GameMessage;
import logic.Vector2D;

public class Trap extends StaticTile {

	public Trap(Vector2D pos) {
		super(pos);
	}

	public GameMessage action(Tile t) {
		if (this.hasLasers())
			return new GameOver();
		else
			return null;
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos();
	}

}
