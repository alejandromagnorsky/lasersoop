package logic.mirror;

import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.Vector2DStack;
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
	public Vector2DStack nextPosition() {
		newLaserDir = new Vector2D(getLastLaser().getDir());
		int angle = getLastLaser().getAngle();
		if (angle == 0 || angle == 180) {
			newLaserDir.changeDirection((int) Math.pow(-1, orientation) * -90);
		} else {
			newLaserDir.changeDirection((int) Math.pow(-1, orientation) * 90);
		}
		return new Vector2DStack(getPos().add(newLaserDir));
	}

}
