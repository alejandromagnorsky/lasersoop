package tests;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import logic.Vector2D;
import logic.laser.Laser;
import logic.tile.Goal;
import logic.tile.Origin;
import logic.tile.SimpleTile;
import logic.tile.Trap;
import logic.tile.Wall;

import org.junit.Before;
import org.junit.Test;

public class TestTiles {

	private Goal goal;
	private Origin origin;
	private SimpleTile simpleT;
	private Trap trap;
	private Wall wall;
	private Laser l1;
	private Laser l2;
	private Laser l3;
	private Laser l4;
	
	
	@Before
	public void initialize(){
		goal = new Goal(new Vector2D(0,0), new Color(0,250,200));
		origin = new Origin(new Vector2D(0,0), 0, new Color(0,250,200));
		simpleT = new SimpleTile(new Vector2D(0,0));
		trap = new Trap(new Vector2D(0,0));
		wall = new Wall(new Vector2D(0,0));
		l1 = new Laser(new Vector2D(1, 0), new Color(100, 200, 100));
		l2 = new Laser(new Vector2D(0, 1), new Color(50, 200, 0));
		l3 = new Laser(new Vector2D(-1, 0), new Color(0, 250, 200));
		l4 = new Laser(new Vector2D(0, -1), new Color(100, 200, 100));
	}
	
	@Test
	public void TestGoal(){
		goal.addLaser(l1);
		assertEquals(goal.getPos().add(l1.getDir()),goal.nextPosition());
		goal.addLaser(l2);
		assertEquals(goal.getPos().add(l2.getDir()),goal.nextPosition());
		goal.addLaser(l3);
		assertEquals(goal.getPos().add(l3.getDir()),goal.nextPosition());
		goal.addLaser(l4);
		assertEquals(goal.getPos().add(l4.getDir()),goal.nextPosition());
		assertEquals(true, goal.laserHasReached());
		assertEquals(1, Goal.countGoals());
		assertEquals(4,goal.countLasers());
	}
	
	@Test
	public void TestOrigin(){
		origin.addLaser(l1);
		assertEquals(origin.getPos().add(origin.getFirstLaser().getDir()),origin.nextPosition());
		origin.addLaser(l2);
		assertEquals(origin.getPos().add(l2.getDir()),origin.nextPosition());
		origin.addLaser(l3);
		assertEquals(origin.getPos().add(l3.getDir()),origin.nextPosition());
		origin.addLaser(l4);
		assertEquals(origin.getPos().add(l4.getDir()),origin.nextPosition());
	}
	
	@Test
	public void TestSimpleT(){
		simpleT.addLaser(l1);
		assertEquals(simpleT.getPos().add(l1.getDir()),simpleT.nextPosition());
		simpleT.addLaser(l2);
		assertEquals(simpleT.getPos().add(l2.getDir()),simpleT.nextPosition());
		simpleT.addLaser(l3);
		assertEquals(simpleT.getPos().add(l3.getDir()),simpleT.nextPosition());
		simpleT.addLaser(l4);
		assertEquals(simpleT.getPos().add(l4.getDir()),simpleT.nextPosition());
	}
	
	@Test
	public void TestTrap(){
		trap.addLaser(l1);
		assertEquals(trap.getPos(),trap.nextPosition());
		trap.addLaser(l2);
		assertEquals(trap.getPos(),trap.nextPosition());
		trap.addLaser(l3);
		assertEquals(trap.getPos(),trap.nextPosition());
		trap.addLaser(l4);
		assertEquals(trap.getPos(),trap.nextPosition());
	}
	
	@Test
	public void TestWall(){
		wall.addLaser(l1);
		assertEquals(wall.getPos(),wall.nextPosition());
		wall.addLaser(l2);
		assertEquals(wall.getPos(),wall.nextPosition());
		wall.addLaser(l3);
		assertEquals(wall.getPos(),wall.nextPosition());
		wall.addLaser(l4);
		assertEquals(wall.getPos(),wall.nextPosition());
	}
}
