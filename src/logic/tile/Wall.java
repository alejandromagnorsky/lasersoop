package logic.tile;

import gui.BoardPanel;
import gui.ImageUtils;
import gui.TileManager;
import java.awt.Image;
import java.util.Vector;

import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;
import messages.LaserStopMessage;

public class Wall extends StaticTile {

	/**
	 * Crea una pared en la posicion recibida.
	 */
	public Wall(Vector2D pos) {
		super(pos);
	}

	/**
	 * Devuelve un mensaje que indica que el laser se detuvo.
	 */
	@Override
	public GameMessage action(Tile t) {
		return new LaserStopMessage();
	}

	public void drawTile(TileManager tm, BoardPanel bp) {

		// Draw lasers first
		drawLasers(tm, bp);

		// Draw image
		Image image = tm.getWall();
		bp.appendImage(getPos().getX(), getPos().getY(), image);

	}

	public void drawLasers(TileManager tm, BoardPanel bp) {
		Vector<Laser> lasers = getLasers();

		for (Laser l : lasers) {
			// Nº of rotations to the left.
			int times = -l.getAngle() / 90;
			Image tmpLaser = ImageUtils.rotateImage(tm.getHalfLaser(), times);
			bp.appendImage(getPos().getX(), getPos().getY(), tmpLaser);
		}
	}

	/**
	 * Devuelve su propia posicion ya detiene al laser.
	 */
	public Vector2D nextPosition() {
		return this.getPos();
	}
	
	public String toString(){
		String pos = getPos().getX() + "," + getPos().getY();
		return pos + ",6,0,0,0,0\n";
	}

}
