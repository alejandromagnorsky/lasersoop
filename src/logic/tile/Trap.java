package logic.tile;

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
	
	/**
	 * Devuelve su propia posicion ya detiene al laser.
	 */
	@Override
	public Vector2D nextPosition() {
		return this.getPos();
	}

}
