package logic.mirror;

import logic.Vector2D;
import logic.laser.Laser;
import logic.tile.Tile;
import messages.GameMessage;
import messages.LaserBounceMessage;
import messages.LaserStopMessage;

public class SimpleMirror extends Mirror {

	/**
	 * Crea un nuevo espejo simple.
	 * 
	 * @param pos
	 *            Indica la posicion en el tablero.
	 * @param orientation
	 *            Contiene la orientacion del espejo representada por un numero
	 *            entre 0 y 3.
	 */
	public SimpleMirror(Vector2D pos, int orientation) {
		super(pos, orientation);
	}

	@Override
	public void rotate() {
		orientation = (orientation + 1) % 4;
		setDegree(orientation);
	}

	/**
	 * Agrega un laser en la celda que recibe si reboto el laser o actua como
	 * pared en caso contrario.
	 * 
	 * @param t
	 *            Celda destino.
	 */
	@Override
	public GameMessage action(Tile t) {
		if (t.getPos().equals(this.getPos())) {
			return new LaserStopMessage();
		}
		t.addLaser(new Laser(newLaserDir, getLastLaser().getColor()));
		return new LaserBounceMessage();
	}

	/**
	 * Retorna la posicion de la siguiente celda utilizando la posicion actual,
	 * la orientacion del espejo y el angulo que tiene el laser que posee.
	 */
	@Override
	public Vector2D nextPosition() {
		Laser l = new Laser(getLastLaser());
		int angle = l.getAngle();
		Vector2D next;

		if (reflects(l)) {
			newLaserDir = l.getDir();
			if (angle + 45 == degree) {
				newLaserDir.changeDirection(90);
			} else {
				newLaserDir.changeDirection(-90);
			}
			next = getPos().add(newLaserDir);
		} else {
			/*
			 * El mensaje action verifica la posicion del tile que recibe. Si es
			 * la misma que la del espejo actua como pared para dicho laser.
			 */
			next = this.getPos();
		}
		return next;
	}

	@Override
	public boolean reflects(Laser laser) {
		int angle = laser.getAngle();
		return angle <= degree && angle >= degree - 180
				|| (angle == 270 && degree == 45);
	}

}
