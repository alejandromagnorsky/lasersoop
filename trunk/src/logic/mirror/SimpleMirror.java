package logic.mirror;

import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.Vector2DStack;
import logic.tile.Tile;
import messages.GameMessage;
import messages.LaserBounceMessage;
import messages.LaserStopMessage;

public class SimpleMirror extends Mirror {

	public SimpleMirror(Vector2D pos, int orientation) {
		super(pos, orientation);
	}

	@Override
	public void rotate() {
		orientation = (orientation + 1) % 4;
		setDegree(orientation);
	}

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
	 * la orientacion del espejo y la dirrecion que tiene el laser que posee
	 */
	@Override
	public Vector2DStack nextPosition() {
		Laser l = new Laser(getLastLaser());
		int angle = l.getAngle();
		Vector2D next;

		if (angle <= degree && angle >= degree - 180
				|| (angle == 270 && degree == 45)) {
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
		return new Vector2DStack(next);
	}
}
