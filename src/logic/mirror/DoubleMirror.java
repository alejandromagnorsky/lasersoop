package logic.mirror;

import logic.GameMessage;
import logic.Vector2D;
import logic.laser.Laser;
import logic.tile.Tile;

public class DoubleMirror extends Mirror {

	public DoubleMirror(Vector2D pos, int orientation) {
		super(pos, orientation);
	}

	@Override
	public void rotate() {
		orientation = (orientation + 1) % 2;
	}

	@Override
	public GameMessage action(Tile t) {
		Vector2D nextDir = nextPosition();
		t.addLaser(new Laser(nextDir, getLastLaser().getColor()));
		return null;
	}

	@Override
	public Vector2D nextPosition() {
		Vector2D nextDir = getLastLaser().getDir();
		int angle = getLastLaser().getAngle();
		if (angle == 0 || angle == 180)
			nextDir.changeDirection((int)Math.pow(-1, orientation) * -90);
		else
			nextDir.changeDirection((int)Math.pow(-1, orientation) * 90);
		return nextDir;
	}

}
