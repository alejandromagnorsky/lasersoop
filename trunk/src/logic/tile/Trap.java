package logic.tile;

import gui.BoardPanel;
import gui.TileManager;

import java.awt.Image;

import logic.Vector2D;
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

		// Draw lasers first
		drawLasers(tm, bp);

		// Draw image
		Image image = tm.getTrap();
		bp.appendImage(getPos().getX(), getPos().getY(), image);

	}

	
	/**
	 * Devuelve su propia posicion ya detiene al laser.
	 */
	@Override
	public Vector2D nextPosition() {
		return this.getPos();
	}

}
