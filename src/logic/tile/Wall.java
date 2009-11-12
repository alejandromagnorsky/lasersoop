package logic.tile;

import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.Vector2DStack;
import messages.GameMessage;
import messages.LaserStopMessage;

public class Wall extends StaticTile {

	public Wall(Vector2D pos) {
		super(pos);
	}

	@Override
	public GameMessage action(Tile t) {
		return new LaserStopMessage();
	}

	/**
	 * Walls have no lasers
	 */
	@Override
	public void addLaser(Laser laser) {
	}

	public Vector2DStack nextPosition() {
		return new Vector2DStack(getPos());
	}

}
