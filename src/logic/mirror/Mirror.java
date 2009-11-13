package logic.mirror;

import logic.Vector2D;
import logic.laser.Laser;
import logic.tile.MovableTile;

public abstract class Mirror extends MovableTile {
	/*
	 * Contiene la orientacion del espejo representada por un numero entre 0 y 3
	 */
	protected int orientation;
	/* Indica el angulo de inclinacion */
	protected int degree;
	protected Vector2D newLaserDir = null;

	public Mirror(Vector2D pos, int orientation) {
		super(pos);
		this.orientation = orientation;
		setDegree(orientation);
	}

	public void setDegree(int orientation) {
		switch (orientation) {
		case 0:
			degree = 135;
			break;
		case 1:
			degree = 45;
			break;
		case 2:
			degree = 315;
			break;
		case 3:
			degree = 225;
			break;
		}
	}

	public int getDegree() {
		return degree;
	}

	public int getOrientation() {
		return orientation;
	}

	public void translate(Vector2D dest) {
		setPos(dest);
	}
	
	public abstract boolean reflects(Laser laser);

	/**
	 * Rota en sentido horario
	 */
	public abstract void rotate();
}