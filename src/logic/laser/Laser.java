package logic.laser;

import logic.Vector2D;

public class Laser {

	private Vector2D dir;
	private LaserColor color;
	private int angle;
	
	public static void main(String args[]) {
		Laser l1 = new Laser(new Vector2D(0, 1), new LaserColor(100, 200, 100));
		System.out.println(l1);
		System.out.println(l1.angle);
	}
	
	public Laser( Laser l ){
		this(l.dir,l.color);
	}

	/**
	 * -> : 0
	 * <- : 180
	 * Hacia arriba: 90
	 * Hacia abajo: 270
	 */
	public Laser(Vector2D dir, LaserColor color) {
		this.dir = dir;
		this.color = color;
		if( dir.equals(new Vector2D(1,0)) )
			this.angle = 0;
		else if( dir.equals(new Vector2D(0,1)) )
			this.angle = 90;
		else if( dir.equals(new Vector2D(-1,0)) )
			this.angle = 180;
		else if( dir.equals(new Vector2D(0,-1)) )
			this.angle = 270;
	}
	
	public Vector2D getDir() {
		return dir;
	}
	
	public int getAngle(){
		return angle;
	}

	public void setColor(LaserColor color) {
		this.color = color;
	}

	public LaserColor getColor() {
		return color;
	}

	public String toString() {
		return "Direction: " + dir + ", Color: " + color;
	}
}