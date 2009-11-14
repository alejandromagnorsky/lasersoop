package logic.laser;

import java.awt.Color;

import logic.Vector2D;

public class Laser {

	private Vector2D dir;
	private Color color;
	private int angle;

	/**
	 * Crea una copia del laser recibido.
	 */
	public Laser(Laser l) {
		this(new Vector2D(l.dir.getX(), l.dir.getY()), l.color);
	}

	/**
	 * Crea un nuevo laser con direccion, color y angulo.
	 * Para el angulo el criterio es:
	 * -> : 0 .
	 * <- : 180 .
	 * Hacia arriba: 90 .
	 * Hacia abajo: 270.
	 */
	public Laser(Vector2D dir, Color color) {
		this.dir = dir;
		this.color = color;
		if (dir.equals(new Vector2D(1, 0)))
			this.angle = 0;
		else if (dir.equals(new Vector2D(0, 1)))
			this.angle = 90;
		else if (dir.equals(new Vector2D(-1, 0)))
			this.angle = 180;
		else if (dir.equals(new Vector2D(0, -1)))
			this.angle = 270;
	}

	public Vector2D getDir() {
		return dir;
	}

	public int getAngle() {
		return angle;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public String toString() {
		return "Direction: " + dir + ", Color: " + color;
	}
}
