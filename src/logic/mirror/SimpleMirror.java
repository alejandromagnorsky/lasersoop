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
			System.out.println("Paso por un mirror");
			return new GameMessage("StopLaser");
		}
		t.addLaser(new Laser(newLaserDir, getLastLaser().getColor()));
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
			/**
			 * El mensaje action verifica la posicion del tile que recibe. Si es
			 * la misma que la del espejo actua como pared para dicho laser.
			 */
			next = this.getPos();
		}
		return next;
	}
}
