package logic.mirror;

import logic.Vector2D;
import logic.tile.MovableTile;

public abstract class Mirror extends MovableTile {
	/**
	 * Contiene la orientacion del espejo representada por un numero entre 0 y 3
	 */
	protected int orientation;
	/** Indica el angulo de inclinacion */
	protected int degree;

	public Mirror(Vector2D pos, int orientation) {
		super(pos);
		this.orientation = orientation;
		this.degree = 45 + orientation * 90;
	}

	public void translate(Vector2D dest) {
		setPos(dest);
	}

	/**
	 * Rota en sentido antihorario
	 */
	public abstract void rotate();
}
