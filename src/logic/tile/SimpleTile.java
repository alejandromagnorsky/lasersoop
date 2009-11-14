package logic.tile;

import gui.BoardPanel;
import gui.TileManager;
import java.awt.Image;
import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;

public class SimpleTile extends StaticTile {

	/**
	 * Crea una celda vacia en la posicion recibida.
	 */
	public SimpleTile(Vector2D pos) {
		super(pos);
	}

	@Override
	public GameMessage action(Tile t) {
		GameMessage status = t.addLaser(new Laser(getLastLaser()));
		return status;
	}

	public void drawTile(TileManager tm, BoardPanel bp) {

		// Draw image
		Image image = tm.getEmpty();
		bp.setImage(getPos().getX(), getPos().getY(), image);

		// Draw lasers first
		drawLasers(tm, bp);
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(this.getLastLaser().getDir());
	}

}
