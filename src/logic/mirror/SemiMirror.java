package logic.mirror;

import logic.Vector2D;
import logic.laser.Vector2DStack;

public class SemiMirror extends DoubleMirror{

	public SemiMirror(Vector2D pos, int orientation) {
		super(pos, orientation);
	}
	
	public Vector2DStack nextPosition() {
		Vector2DStack outStack = super.nextPosition();
		outStack.add(getPos().add(getLastLaser().getDir()));
		return outStack;
	}
}
