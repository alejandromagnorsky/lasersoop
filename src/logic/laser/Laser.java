package logic.laser;

import java.awt.Color;

import logic.Vector2D;

public class Laser {

	private Vector2D dir;
	private Color color;
	private int angle;

	public static void main(String args[]) {
		Laser l1 = new Laser(new Vector2D(0, 1), new Color(100, 200, 100));
		System.out.println(l1);
		System.out.println(l1.angle);
	}

	public Laser(Laser l) {
		this(new Vector2D(l.dir.getX(), l.dir.getY()), l.color);
	}

	/**
	 * -> : 0 <- : 180 Hacia arriba: 90 Hacia abajo: 270
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
