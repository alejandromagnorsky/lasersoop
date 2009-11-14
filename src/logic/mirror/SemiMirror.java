package logic.mirror;

import gui.BoardPanel;
import gui.HueController;
import gui.ImageUtils;
import gui.TileManager;

import java.awt.Color;
import java.awt.Image;
import java.util.Vector;

import logic.Vector2D;
import logic.laser.Laser;
import logic.tile.Tile;
import messages.GameMessage;
import messages.LaserStopMessage;

public class SemiMirror extends DoubleMirror {

	private Color color;

	/**
	 * Crea un nuevo semi-espejo.
	 * 
	 * @param pos
	 *            Indica la posicion en el tablero.
	 * @param orientation
	 *            Contiene la orientacion del espejo representada por un numero
	 *            entre 0 y 3.
	 */
	public SemiMirror(Vector2D pos, int orientation) {
		super(pos, orientation);
		color = new Color(0, 0, 0);
	}
	

	public void eraseLasers() {
		super.eraseLasers();
		color = new Color(0,0,0);
	}

	
	@Override
	public GameMessage addLaser(Laser laser) {
		// Si el laser que recibe es igual al ultimo que recibio, no lo agrega y
		// devuelve que el laser se detuvo.
		if (countLasers() > 1 && laser.getDir().equals(getLastLaser().getDir()))
			return new LaserStopMessage();
		return super.addLaser(laser);
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
		Image image = ImageUtils.rotateImage(tm.getSemiMirror(), times);

		if (hasLasers())
			image = HueController.changeHue(image, color);
		bp.appendImage(getPos().getX(), getPos().getY(), image);

	}

	public void drawLasers(TileManager tm, BoardPanel bp) {
		Vector<Laser> lasers = getLasers();

		for (Laser l : lasers) {
			color = ImageUtils.mix(color, l.getColor());
		}

		for (Laser l : lasers) {

			// Nº of rotations to the left.
			int angle = l.getAngle() / 90;
			int direction = getOrientation() * 2 + angle;

			Image tmpLaser = ImageUtils.rotateImage(tm.getTLaser(), direction);
			tmpLaser = HueController.changeHue(tmpLaser, color);
			bp.appendImage(getPos().getX(), getPos().getY(), tmpLaser);
		}
	}

	/**
	 * Ademas de agregar un laser en la celda recibida, mezcla los colores de
	 * los lasers que tiene.
	 * 
	 * @param t
	 *            Celda destino.
	 */
	@Override
	public GameMessage action(Tile t) {
		color = ImageUtils.mix(color, getLastLaser().getColor());
		GameMessage status = t.addLaser(new Laser(newLaserDir, color));
		return status;	}

	public String toString() {
		String pos = getPos().getX() + "," + getPos().getY();
		return pos + ",5," + orientation + ",0,0,0";
	}

}