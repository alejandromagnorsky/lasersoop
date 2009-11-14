package logic.mirror;

import logic.Vector2D;
import logic.laser.Laser;
import logic.tile.Tile;
import messages.GameMessage;
import messages.LaserBounceMessage;

public class DoubleMirror extends Mirror {

	/**
	 * Crea un nuevo espejo doble.
	 * 
	 * @param pos
	 *            Indica la posicion en el tablero.
	 * @param orientation
	 *            Contiene la orientacion del espejo representada por un numero
	 *            entre 0 y 3.
	 */
	public DoubleMirror(Vector2D pos, int orientation) {
		super(pos, orientation);
	}

	@Override
	public void rotate() {
		orientation = (orientation + 1) % 2;
		setDegree(orientation);
	}

	/**
	 * Agrega un laser a la celda que recibe.
	 * 
	 * @param t
	 *            Celda destino.
	 */
	@Override
	public GameMessage action(Tile t) {
		t.addLaser(new Laser(newLaserDir, getLastLaser().getColor()));
		return new LaserBounceMessage();
	}

	/**
	 * Devuelve la siguiente posicion en el tablero usando el laser que tiene.
	 */
	@Override
	public Vector2D nextPosition() {
		newLaserDir = new Vector2D(getLastLaser().getDir());
		int angle = getLastLaser().getAngle();
		if (angle == 0 || angle == 180) {
			newLaserDir.changeDirection((int) Math.pow(-1, orientation) * -90);
		} else {
			newLaserDir.changeDirection((int) Math.pow(-1, orientation) * 90);
		}
		return getPos().add(newLaserDir);
	}

	@Override
	public boolean reflects(Laser laser) {
		return true;
	}

}
