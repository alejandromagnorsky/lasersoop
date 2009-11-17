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
	 *            Contiene la orientacion del espejo representada por un 0 o un 1.
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
	
	
	public void drawTile(TileManager tm, BoardPanel bp) {
		// Primero dibuja los lasers.
		drawLasers(tm, bp);

		// Dibuja la imagen.
		Image image = ImageUtils.rotateImage(tm.getDoubleMirror(), orientation);

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
	
	public String toString(){
		String pos = getPos().getX() + "," + getPos().getY();
		return pos + ",4," + orientation + ",0,0,0";
	}
}
