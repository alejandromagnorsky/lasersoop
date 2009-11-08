package logic.tile;

import logic.GameMessage;
import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.LaserColor;

public class Origin extends StaticTile {

	public Origin(Vector2D pos, int orientation, LaserColor color) {
		super(pos);
		Vector2D dir = new Vector2D((360 - orientation * 90) % 360);
		Laser l = new Laser(dir, color);
		super.addLaser(l);
		/**
		 * El addLaser de la clase esta para que no se puedan crear lasers extra
		 * en el origin
		 */
	}

	/**
	 * Origins have only one laser, defined by its constructor
	 */
	@Override
	public void addLaser(Laser laser) {
	}

	public void eraseLasers() {
	}

	@Override
	public GameMessage action(Tile t) {
		if (t.countLasers() > 1)
			return new GameMessage("StopLaser");
		/** Si recibio un laser, aparte del que ya contiene, actua como pared */
		else
			t.addLaser(new Laser(getLastLaser()));
		return new GameMessage("EmisionOK");
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(getLastLaser().getDir());
	}

}
