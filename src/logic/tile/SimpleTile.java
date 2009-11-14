package logic.tile;

import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;
import messages.NullMessage;

public class SimpleTile extends StaticTile {

	/**
	 * Crea una celda vacia en la posicion recibida.
	 */
	public SimpleTile(Vector2D pos) {
		super(pos);
	}

	@Override
	public GameMessage action(Tile t) {
		t.addLaser(new Laser(getLastLaser()));
		return new NullMessage();
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(this.getLastLaser().getDir());
	}

}
