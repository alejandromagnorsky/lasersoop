package logic.tile;

import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.Vector2DStack;
import messages.GameMessage;
import messages.NullMessage;

public class SimpleTile extends StaticTile {

	public SimpleTile(Vector2D pos) {
		super(pos);
	}

	@Override
	public GameMessage action(Tile t) {
		t.addLaser(new Laser(getLastLaser()));
		return new NullMessage();
	}

	@Override
	public Vector2DStack nextPosition() {
		return new Vector2DStack(getPos().add(getLastLaser().getDir()));
	}
}
