package logic.tile;

import logic.GameMessage;
import logic.Vector2D;
import logic.laser.Laser;

public class Goal extends StaticTile {

	public Goal(Vector2D pos) {
		super(pos);
	}

	@Override
	public GameMessage action(Tile t) {
		if (this.hasLasers()) {
			t.addLaser(new Laser(getLastLaser()));
			return new GameMessage("GoalAcchieved");
		}

		return null;
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(this.getLastLaser().getDir());
	}
}
