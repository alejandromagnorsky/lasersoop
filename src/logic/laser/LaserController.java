package logic.laser;

import java.util.Stack;

import logic.GameMessage;
import logic.Vector2D;
import logic.tile.Tile;

public abstract class LaserController {

	Stack<Laser> lasers = null;

	public abstract GameMessage action(Tile t);

	public abstract Vector2D nextPosition();

	public LaserController() {
		lasers = new Stack<Laser>();
	}

	public boolean hasLasers() {
		return !lasers.isEmpty();
	}

	public void addLaser(Laser laser) {
		lasers.add(laser);
	}

	public Laser getLastLaser() {
		return lasers.peek();
	}

	public void eraseLasers() {
		do {
			lasers.pop();
		} while (hasLasers());
	}
}
