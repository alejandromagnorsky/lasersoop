package logic.tile;

import logic.GameMessage;
import logic.Vector2D;
import logic.laser.Laser;

public class Origin extends StaticTile {

	public Origin(Vector2D pos, Laser laser) {
		super(pos);
		this.addLaser(laser);
	}

	/**
	 * Origins have only one laser, defined by its constructor
	 */
	@Override
	public void addLaser(Laser laser) {
	}

	@Override
	public GameMessage action(Tile t) {
		t.addLaser(new Laser(getLastLaser()));
		return null;
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(this.getLastLaser().getDir());
	}

}
