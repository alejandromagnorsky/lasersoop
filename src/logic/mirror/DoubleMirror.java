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

	public void drawTile(TileManager tm, BoardPanel bp) {

		int times = 0;

		// Just for easy understanding
		switch (getDegree()) {
		case 135:
			times = 0;
			break;
		case 45:
			times = 1;
			break;
		case 315:
			times = 2;
			break;
		case 225:
			times = 3;
			break;
		}

		// Draw lasers first
		drawLasers(tm, bp);

		// Draw image
		Image image = ImageUtils.rotateImage(tm.getDoubleMirror(), times);

		if (hasLasers())
			image = HueController.changeHue(image, getFirstLaser().getColor());
		bp.appendImage(getPos().getX(), getPos().getY(), image);

	}

	public void drawLasers(TileManager tm, BoardPanel bp) {
		Vector<Laser> lasers = getLasers();

		for (Laser l : lasers) {

			int angle = l.getAngle() / 90;
			int direction = angle;

			if (angle % 2 == 1)
				direction += getOrientation() * 3 - 1;
			else
				direction += getOrientation();

			Image tmpLaser = ImageUtils.rotateImage(tm.getCornerLaser(),
					direction);
			tmpLaser = HueController.changeHue(tmpLaser, l.getColor());
			bp.appendImage(getPos().getX(), getPos().getY(), tmpLaser);
		}
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
		GameMessage status = t.addLaser(new Laser(newLaserDir, getLastLaser().getColor()));
		return status;
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
	
	public String toString(){
		String pos = getPos().getX() + "," + getPos().getY();
		return pos + ",4," + orientation + ",0,0,0";
	}
}
