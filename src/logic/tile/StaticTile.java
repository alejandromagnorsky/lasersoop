package logic.tile;

import java.awt.Color;
import java.awt.Image;
import java.util.Vector;

import gui.BoardPanel;
import gui.HueController;
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

	public void translate(Vector2D dest) {
	}

	public boolean canSwap() {
		return false;
	}

	public void drawLasers(TileManager tm, BoardPanel bp) {
		Vector<Laser> lasers = getLasers();

		for (Laser l : lasers) {
			// Nº of rotations to the left.
			int times = l.getAngle() / 90;

			Image tmpLaser = ImageUtils.rotateImage(tm.getLaser(), times);
			tmpLaser = HueController.changeHue(tmpLaser, l.getColor());

			bp.appendImage(getPos().getX(), getPos().getY(), tmpLaser);
		}
	}

}
