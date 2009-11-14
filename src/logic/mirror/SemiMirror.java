package logic.mirror;

import gui.ImageUtils;

import java.awt.Color;
import logic.Vector2D;
import logic.tile.Tile;
import messages.GameMessage;

public class SemiMirror extends DoubleMirror {

	private Color color;

	/**
	 * Crea un nuevo semi-espejo.
	 * 
	 * @param pos
	 *            Indica la posicion en el tablero.
	 * @param orientation
	 *            Contiene la orientacion del espejo representada por un numero
	 *            entre 0 y 3.
	 */
	public SemiMirror(Vector2D pos, int orientation) {
		super(pos, orientation);
		color = new Color(0, 0, 0);
	}

	/**
	 * Ademas de agregar un laser en la celda recibida, mezcla los colores de
	 * los lasers que tiene.
	 * 
	 * @param t
	 *            Celda destino.
	 */
	@Override
	public GameMessage action(Tile t) {
		color = ImageUtils.mix(color, getLastLaser().getColor());
		return super.action(t);
	}

}