package logic.mirror;

import gui.BoardPanel;
import gui.HueController;
import gui.ImageUtils;
import gui.TileManager;
import java.awt.Image;
import java.util.Vector;
import logic.Vector2D;
import logic.laser.Laser;
import logic.tile.Tile;
import messages.GameMessage;
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
		GameMessage status = t.addLaser(new Laser(newLaserDir, getLastLaser().getColor()));
		return status;
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
	
	public void drawTile(TileManager tm, BoardPanel bp) {
		// Primero dibuja los lasers.
		drawLasers(tm, bp);

		// Dibuja las imagenes.
		Image image = ImageUtils.rotateImage(tm.getSimpleMirror(), orientation);

		if (hasLasers())
			image = HueController.changeHue(image, getFirstLaser().getColor());
		bp.appendImage(getPos().getX(), getPos().getY(), image);

	}

	public void drawLasers(TileManager tm, BoardPanel bp) {
		Vector<Laser> lasers = getLasers();

		for (Laser l : lasers) {

			// Nº of rotations to the left.
			int angle = l.getAngle() / 90;

			int direction = 0;

			Image tmpLaser;

			if (reflects(l)) {

				if (angle % 2 == 1)
					direction = angle + (getOrientation()%2) * 3 - 1;
				else
					direction = angle + getOrientation()%2;

				tmpLaser = tm.getCornerLaser();
			} else {
				direction = -angle;
				tmpLaser = tm.getHalfLaser();
			}

			tmpLaser = ImageUtils.rotateImage(tmpLaser, direction);
			tmpLaser = HueController.changeHue(tmpLaser, l.getColor());
			bp.appendImage(getPos().getX(), getPos().getY(), tmpLaser);
		}
	}

	public String toString(){
		String pos = getPos().getX() + "," + getPos().getY();
		return pos + ",3," + orientation + ",0,0,0";
	}

}