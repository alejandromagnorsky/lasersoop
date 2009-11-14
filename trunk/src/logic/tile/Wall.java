package logic.tile;

import gui.BoardPanel;
import gui.TileManager;
import java.awt.Image;
import logic.Vector2D;
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


	/**
	 * Devuelve su propia posicion ya detiene al laser.
	 */
	public Vector2D nextPosition() {
		return this.getPos();
	}

}
