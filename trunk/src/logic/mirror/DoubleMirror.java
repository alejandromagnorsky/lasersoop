package logic.mirror;

import logic.Vector2D;
import logic.laser.Laser;
import logic.tile.Tile;
import messages.GameMessage;
import messages.LaserBounceMessage;

public class DoubleMirror extends Mirror {

	public DoubleMirror(Vector2D pos, int orientation) {
		super(pos, orientation);
	}

	@Override
	public void rotate() {
		orientation = (orientation + 1) % 2;
		setDegree(orientation);
	}

	@Override
	public GameMessage action(Tile t) {
		t.addLaser(new Laser(newLaserDir, getLastLaser().getColor()));
		return new LaserBounceMessage();
	}

	@Override
	public Vector2D nextPosition() {
		newLaserDir = new Vector2D(getLastLaser().getDir());
		int angle = getLastLaser().getAngle();
		if (angle == 0 || angle == 180) {
			newLaserDir.changeDirection((int) Math.pow(-1, orientation) * -90);
		} else {
			newLaserDir.changeDirection((int) Math.pow(-1, orientation) * 90);
		}
		return getPos().add(newLaserDir);
	}

	@Override
	public boolean reflects(Laser laser) {
		return true;
	}

}
