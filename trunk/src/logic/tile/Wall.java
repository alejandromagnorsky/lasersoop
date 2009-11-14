package logic.tile;

import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;
import messages.LaserStopMessage;

public class Wall extends StaticTile {

	/**
	 * Crea una pared en la posicion recibida.
	 */
	public Wall(Vector2D pos) {
		super(pos);
	}
	
	/**
	 * Devuelve un mensaje que indica que el laser se detuvo.
	 */
	@Override
	public GameMessage action(Tile t) {
		return new LaserStopMessage();
	}

	/**
	 * Las paredes no tienen lasers.
	 */
	@Override
	public void addLaser(Laser laser) {
	}

	/**
	 * Devuelve su propia posicion ya detiene al laser.
	 */
	public Vector2D nextPosition() {
		return this.getPos();
	}

}
