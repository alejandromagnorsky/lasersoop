package logic.mirror;

import logic.Vector2D;
import logic.laser.Laser;
import logic.tile.MovableTile;

public abstract class Mirror extends MovableTile {

	protected int orientation;
	protected int degree;
	protected Vector2D newLaserDir = null;

	/**
	 * Crea un nuevo espejo.
	 * 
	 * @param pos
	 *            Indica la posicion en el tablero.
	 * @param orientation
	 *            Contiene la orientacion del espejo representada por un numero
	 *            entre 0 y 3.
	 */
	protected Mirror(Vector2D pos, int orientation) {
		super(pos);
		this.orientation = orientation;
		setDegree(orientation);
	}

	/**
	 * Cambia el angulo de orientacion.
	 * El criterio es:
	 * \. : 135.
	 * ./ : 45.
	 * .\ : 315.
	 * /. : 225.
	 * Donde el punto representa la pared del espejo simple.
	 * Para los dobles y semis se utilizan solo los dos primeros angulos.
	 * 
	 * @param orientation
	 *            Contiene la orientacion del espejo representada por un numero
	 *            entre 0 y 3.
	 */
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

	/**
	 * Indica si un laser rebota en el espejo.
	 *
	 * @param laser
	 *            Laser "dentro" del espejo.
	 * @return True, si lo refleja. False, en caso contrario.
	 */
	public boolean reflects(Laser laser){
		int angle = laser.getAngle();
		return angle <= degree && angle >= degree - 180
					|| (angle == 270 && degree == 45);
	}

}
