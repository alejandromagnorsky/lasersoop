package logic.mirror;

import logic.GameMessage;
import logic.Vector2D;
import logic.laser.Laser;
import logic.tile.Tile;

public class SimpleMirror extends Mirror {

	public SimpleMirror(Vector2D pos, int orientation) {
		super(pos, orientation);
	}

	@Override
	public void rotate() {
		orientation = (orientation + 1) % 4;
		degree = (degree - 90) % 360;
	}

	@Override
	public GameMessage action(Tile t) {
		if (t.getPos().equals(this.getPos())) {
			return new GameMessage("StopLaser");
		}
		Vector2D nextDir = nextPosition();
		t.addLaser(new Laser(nextDir, getLastLaser().getColor()));
		return new GameMessage("SimpleMirrorOK");
	}

	/**
	 * Retorna la posicion de la siguiente celda utilizando la posicion actual,
	 * la orientacion del espejo y la dirrecion que tiene el laser que posee
	 */
	@Override
	public Vector2D nextPosition() {
		Laser l = getLastLaser();
		int angle = l.getAngle();
		Vector2D next;
		/**
		 * FIJARSE SI HAY QUE HACER UN SHALLOW COPY YA QUE SINO ESTAMOS
		 * MODIFICANDO EL LASER QUE YA HABIA
		 */
		if (angle <= degree && angle >= degree - 180
				|| (angle == 270 && degree == 45)) {
			next = l.getDir();
			if (angle + 45 == degree) {
				next.changeDirection(90);
			} else {
				next.changeDirection(-90);
			}
		} else {
			/**
			 * El mensaje action verifica la posicion del tile que recibe. Si es
			 * la misma que la del espejo actua como pared para dicho laser.
			 */
			next = this.getPos();
		}
		return next;
	}
}
