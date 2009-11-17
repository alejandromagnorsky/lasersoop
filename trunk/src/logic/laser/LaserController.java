package logic.laser;

import java.util.Vector;
import logic.Vector2D;
import logic.tile.Tile;
import messages.GameMessage;
import messages.NullMessage;

public abstract class LaserController {

	Vector<Laser> lasers = null;

	/**
	 * Crea un nuevo vector de lasers.
	 */
	public LaserController() {
		lasers = new Vector<Laser>();
	}

	/**
	 * Representa la accion que debe tomar cada celda cuando un laser "pasa" por
	 * ella.
	 * 
	 * @param t
	 *            Celda que puede llegar a ser modificada.
	 * @return Un mensaje que indica el estado del juego.
	 */
	public abstract GameMessage action(Tile t);

	/**
	 * Devuelve la siguiente posicion a la que "ira" el laser en el tablero.
	 */
	public abstract Vector2D nextPosition();

	/**
	 * Devuelve los lasers que se encuentran en la celda.
	 */
	public Vector<Laser> getLasers() {
		return lasers;
	}

	/**
	 * @return Verdadero en el caso que tenga uno o mas lasers y falso si no
	 *         tiene ninguno.
	 */
	public boolean hasLasers() {
		return !lasers.isEmpty();
	}

	/**
	 * Devuelve la cantidad de lasers que tiene la celda.
	 */
	public int countLasers() {
		return lasers.size();
	}

	/**
	 * Añade un laser en la celda.
	 */
	public GameMessage addLaser(Laser laser) {
		lasers.add(laser);
		return new NullMessage();
	}

	/**
	 * Devuelve el ultimo laser.
	 * 
	 * @return
	 */
	public Laser getLastLaser() {
		return lasers.lastElement();
	}

	/**
	 * Devuelve el primer laser.
	 * 
	 * @return
	 */
	public Laser getFirstLaser() {
		return lasers.firstElement();
	}

	/**
	 * Borra todos los lasers que posee la celda.
	 */
	public void eraseLasers() {
		lasers.clear();
	}

	/**
	 * Borra el ultimo laser de la celda.
	 */
	public void eraseLastLaser() {
		lasers.removeElementAt(countLasers() - 1);
	}
}
