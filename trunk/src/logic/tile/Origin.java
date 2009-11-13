package logic.tile;

import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.LaserColor;
import messages.GameMessage;
import messages.NullMessage;

public class Origin extends StaticTile {

	private int orientation;

	public Origin(Vector2D pos, int orientation, LaserColor color) {
		super(pos);
		this.orientation = orientation;
		Vector2D dir = new Vector2D((360 - orientation * 90) % 360);
		Laser l = new Laser(dir, color);
		addLaser(l);
	}

	public int getOrientation() {
		return this.orientation;
	}

	public LaserColor getColor() {
		return this.getLastLaser().getColor();
	}

	public void eraseLasers() {
		Laser tmp = getFirstLaser();
		super.eraseLasers();
		super.addLaser(tmp);
	}

	@Override
	public GameMessage action(Tile t) {
		t.addLaser(new Laser(getLastLaser()));
		return new NullMessage();
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(getLastLaser().getDir());
	}

}
