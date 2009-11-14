package logic.tile;

import logic.Vector2D;

/**
 * Representa las celdas que se pueden rotar y trasladar.
 *
 */
public abstract class MovableTile extends Tile {

	public MovableTile(Vector2D pos) {
		super(pos);
	}
	
	/**
	 * Traslasda la celda a la posicion que recibe.
	 * 
	 * @param dest
	 *            Posicion destino.
	 */
	public void translate(Vector2D dest) {
		this.pos = dest;
	}
	
	/**
	 * Rota la celda en sentido horario.
	 */
	public abstract void rotate();
}
