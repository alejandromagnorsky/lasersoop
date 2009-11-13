package logic.laser;

import java.util.Vector;
import logic.Vector2D;
import logic.tile.Tile;
import messages.GameMessage;

public abstract class LaserController {

	Vector<Laser> lasers = null;

	public abstract GameMessage action(Tile t);

	public abstract Vector2D nextPosition();

	public LaserController() {
		lasers = new Vector<Laser>();
	}
	
	public Vector<Laser> getLasers(){
		return lasers;
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
	
	public void eraseLastLaser() {
		lasers.removeElementAt(countLasers()-1);
	}
}
