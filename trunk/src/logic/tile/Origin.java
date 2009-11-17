package logic.tile;

import gui.BoardPanel;
import gui.HueController;
import gui.ImageUtils;
import gui.TileManager;

import java.awt.Color;
import java.awt.Image;
import java.util.Vector;

import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;
import messages.LaserStopMessage;

public class Origin extends StaticTile {

	private int orientation;

	/**
	 * Crea un nuevo origen con un determinado color y una orientacion, en la
	 * posicion recibida.
	 */
	public Origin(Vector2D pos, int orientation, Color color) {
		super(pos);
		this.orientation = orientation;
		Vector2D dir = new Vector2D((360 - orientation * 90) % 360);
		Laser l = new Laser(dir, color);
		addLaser(l);
	}

	/**
	 * Para evitar que se pise el laser del emisor, se pueden agregar solamente
	 * laseres que no tenga el mismo sentido que el que dispara.
	 */
	@Override
	public GameMessage addLaser(Laser laser) {
		if (!hasLasers())
			return super.addLaser(laser);
		if (laser.getDir().equals(getFirstLaser().getDir())) {
			return new LaserStopMessage();
		}
		return super.addLaser(laser);
	}

	public int getOrientation() {
		return this.orientation;
	}

	public void drawLasers(TileManager tm, BoardPanel bp) {
		Vector<Laser> lasers = getLasers();

		for (Laser l : lasers) {

			// Nº of rotations to the left.
			int angle = l.getAngle() / 90;

			Image tmpLaser;
			if (l.equals(getFirstLaser()))
				tmpLaser = ImageUtils.rotateImage(tm.getLaser(), angle);
			tmpLaser = ImageUtils.rotateImage(tm.getHalfLaser(), angle);
			tmpLaser = HueController.changeHue(tmpLaser, l.getColor());

			bp.appendImage(getPos().getX(), getPos().getY(), tmpLaser);

		}
	}

	public void drawTile(TileManager tm, BoardPanel bp) {

		// Draw lasers first
		drawLasers(tm, bp);

		// Draw image
		Image image = ImageUtils.rotateImage(tm.getOrigin(), getOrientation());
		image = HueController.changeHue(image, getFirstLaser().getColor());
		bp.appendImage(getPos().getX(), getPos().getY(), image);

	}

	public Color getColor() {
		return this.getLastLaser().getColor();
	}

	@Override
	public boolean shootLaser() {
		return true;
	}

	/**
	 * Borra todos los lasers menos el primero que es el que emite.
	 */
	public void eraseLasers() {
		Laser tmp = getFirstLaser();
		super.eraseLasers();
		super.addLaser(tmp);
	}

	@Override
	public GameMessage action(Tile t) {
		GameMessage status = t.addLaser(new Laser(getLastLaser()));
		if (countLasers() >= 2)
			eraseLastLaser();
		return status;
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(getLastLaser().getDir());
	}

	public String toString() {
		String pos = getPos().getX() + "," + getPos().getY();
		String color = getColor().getRed() + "," + getColor().getGreen() + ","
				+ getColor().getBlue();
		return pos + ",1," + orientation + "," + color;
	}

}
