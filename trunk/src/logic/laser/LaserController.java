package logic.laser;

import java.util.Vector;
import logic.tile.Tile;
import messages.GameMessage;

public abstract class LaserController {

	Vector<Laser> lasers = null;

	public abstract GameMessage action(Tile t);

	public abstract Vector2DStack nextPosition();

	public LaserController() {
		lasers = new Vector<Laser>();
	}

	public boolean hasLasers() {
		return !lasers.isEmpty();
	}

	public int countLasers() {
		return lasers.size();
	}

	public void addLaser(Laser laser) {
		lasers.add(laser);
	}

	public Laser getLastLaser() {
		return lasers.lastElement();
	}

	public Laser getFirstLaser() {
		return lasers.firstElement();
	}

	public void eraseLasers() {
		lasers.clear();
	}
}