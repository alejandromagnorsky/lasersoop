package logic.mirror;

import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.LaserColor;

public class TestMirror {
	public static void main(String args[]){
		Vector2D pos = new Vector2D(200,200);
		SimpleMirror m = new SimpleMirror(pos, 0);
		System.out.println(m.degree);
		Laser l1 = new Laser(new Vector2D(-1, 0), new LaserColor(100, 200, 100));
		m.addLaser(l1);
		System.out.println("NewDir: " + m.nextPosition());
		Laser l2 = new Laser(new Vector2D(1, 0), new LaserColor(100, 200, 100));
		m.addLaser(l2);
		System.out.println("NewDir: " + m.nextPosition());
		Laser l3 = new Laser(new Vector2D(0, -1), new LaserColor(100, 200, 100));
		m.addLaser(l3);
		System.out.println("NewDir: " + m.nextPosition());
		Laser l4 = new Laser(new Vector2D(0, 1), new LaserColor(100, 200, 100));
		m.addLaser(l4);
		System.out.println("NewDir: " + m.nextPosition());
	}
}
