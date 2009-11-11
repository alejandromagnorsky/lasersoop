package logic.tile;

import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;

public class SimpleTile extends StaticTile {

	public SimpleTile(Vector2D pos) {
		super(pos);
	}

	@Override
	public GameMessage action(Tile t) {
		t.addLaser(new Laser(getLastLaser()));
		return new GameMessage("SimpleTileOK");
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(this.getLastLaser().getDir());
	}

}
