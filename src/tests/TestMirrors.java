package tests;

import static org.junit.Assert.assertEquals;
import gui.ImageUtils;
import java.awt.Color;
import logic.Vector2D;
import logic.laser.Laser;
import logic.mirror.DoubleMirror;
import logic.mirror.SemiMirror;
import logic.mirror.SimpleMirror;

import org.junit.Before;
import org.junit.Test;

public class TestMirrors{
	
	private SimpleMirror simpleM;
	private DoubleMirror doubleM;
	private SemiMirror semiM;
	private Laser l1;
	private Laser l2;
	private Laser l3;
	private Laser l4;
	
	
	@Before
	public void initialize(){
		Vector2D pos = new Vector2D(10, 10);
		simpleM = new SimpleMirror(pos, 2);
		doubleM = new DoubleMirror(pos, 1);
		semiM = new SemiMirror(pos, 0);
		l1 = new Laser(new Vector2D(1, 0), new Color(100, 200, 100));
		l2 = new Laser(new Vector2D(0, 1), new Color(50, 200, 0));
		l3 = new Laser(new Vector2D(-1, 0), new Color(250, 200, 10));
		l4 = new Laser(new Vector2D(0, -1), new Color(100, 200, 100));
	}
	
	@Test
	public void testSimpleMirror(){
		simpleM.addLaser(l1);
		assertEquals(simpleM.getPos(),simpleM.nextPosition());
		simpleM.addLaser(l2);
		assertEquals(simpleM.getPos(),simpleM.nextPosition());
		simpleM.addLaser(l3);
		assertEquals(simpleM.getPos().add(new Vector2D(0,1)),simpleM.nextPosition());
		simpleM.addLaser(l4);
		assertEquals(simpleM.getPos().add(new Vector2D(1,0)),simpleM.nextPosition());
	}
	
	@Test
	public void testDoubleMirror(){
		doubleM.addLaser(l1);
		assertEquals(doubleM.getPos().add(new Vector2D(0,1)),doubleM.nextPosition());
		doubleM.addLaser(l2);
		assertEquals(doubleM.getPos().add(new Vector2D(1,0)),doubleM.nextPosition());
		doubleM.addLaser(l3);
		assertEquals(doubleM.getPos().add(new Vector2D(0,-1)),doubleM.nextPosition());
		doubleM.addLaser(l4);
		assertEquals(doubleM.getPos().add(new Vector2D(-1,0)),doubleM.nextPosition());
	}
	
	@Test
	public void testSemiMirror(){
		semiM.addLaser(l1);
		assertEquals(l1.getColor(),semiM.mixLasersColors());
		semiM.addLaser(l2);
		assertEquals(l2.getColor(),semiM.mixLasersColors());
		semiM.addLaser(l3);
		assertEquals(l3.getColor(),semiM.mixLasersColors());
		semiM.addLaser(l4);
		assertEquals(ImageUtils.mix(l1.getColor(), l4.getColor()),semiM.mixLasersColors());
	}
}
