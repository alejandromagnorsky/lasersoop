package logic.tile;

import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;

public class Wall extends StaticTile {

	public Wall(Vector2D pos) {
		super(pos);
	}

	@Override
	public GameMessage action(Tile t) {
		return new GameMessage("StopLaser");
	}

	/**
	 * Walls have no lasers
	 */
	@Override
	public void addLaser(Laser laser) {
	}

	public Vector2D nextPosition() {
		return this.getPos();
	}

}
