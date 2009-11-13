package logic.tile;

import logic.Vector2D;
import messages.GameMessage;
import messages.GameOverMessage;

public class Trap extends StaticTile {

	public Trap(Vector2D pos) {
		super(pos);
	}

	public GameMessage action(Tile t) {
		return new GameOverMessage();
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos();
	}

}