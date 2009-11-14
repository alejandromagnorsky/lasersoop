package logic.tile;

import java.awt.Image;
import java.util.Vector;

import gui.BoardPanel;
import gui.ImageUtils;
import gui.TileManager;
import logic.Vector2D;
import logic.laser.Laser;

/**
 * Representa las celdas que no tienen movimiento.
 * 
 */
public abstract class StaticTile extends Tile {

	public StaticTile(Vector2D pos) {
		super(pos);
	}

	public void drawLasers(TileManager tm, BoardPanel bp) {
		Vector<Laser> lasers = getLasers();

		for (Laser l : lasers) {

			// N� of rotations to the left. The +1 correction is for the laser
			// image original rotation
			int times = 1 + l.getAngle() / 90;

			Image tmpLaser;

			tmpLaser = ImageUtils.rotateImage(tm.getLaser(), times);

			bp.appendImage(getPos().getX(), getPos().getY(), tmpLaser);
		}
	}

}
