package logic.tile;

import gui.BoardPanel;
import gui.ImageUtils;
import gui.TileManager;

import java.awt.Image;
import java.util.Vector;

import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;
import messages.GameOverMessage;

public class Trap extends StaticTile {

	/**
	 * Crea una nueva trampa en la posicion recibida.
	 */
	public Trap(Vector2D pos) {
		super(pos);
	}

	/**
	 * Devuelve un mensaje que indica que termino el juego.
	 */
	public GameMessage action(Tile t) {
		return new GameOverMessage();
	}

	public void drawTile(TileManager tm, BoardPanel bp) {
		// Draw image first
		Image image = tm.getTrap();
		bp.appendImage(getPos().getX(), getPos().getY(), image);

		// Draw lasers
		drawLasers(tm, bp);
	}

	public void drawLasers(TileManager tm, BoardPanel bp) {
		Vector<Laser> lasers = getLasers();

		for (Laser l : lasers) {
			// N� of rotations to the left.
			int times = -l.getAngle() / 90;
			Image tmpLaser = ImageUtils.rotateImage(tm.getHalfLaser(), times);
			bp.appendImage(getPos().getX(), getPos().getY(), tmpLaser);
		}
	}

	/**
	 * Devuelve su propia posicion ya detiene al laser.
	 */
	@Override
	public Vector2D nextPosition() {
		return this.getPos();
	}

}
