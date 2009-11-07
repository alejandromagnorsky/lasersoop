package logic.tile;

import logic.GameMessage;
import logic.Vector2D;
import logic.laser.Laser;

public class Wall extends StaticTile {

	public Wall(Vector2D pos) {
		super(pos);
	}

	@Override
	public GameMessage action(Tile t) {
		return null;
	}
	
	/**
	 * Walls have no lasers
	 */
	@Override
	public void addLaser(Laser laser){}


	public Vector2D nextPosition() {
		return new Vector2D(0,0);
	}

}
