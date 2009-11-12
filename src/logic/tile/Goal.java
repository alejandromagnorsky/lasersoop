package logic.tile;

import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.LaserColor;
import logic.laser.Vector2DStack;
import messages.GameMessage;
import messages.GoalAchievedMessage;
import messages.NullMessage;

public class Goal extends StaticTile {

	private LaserColor color;

	public Goal(Vector2D pos, LaserColor color) {
		super(pos);
		this.color = color;
	}

	public LaserColor getColor() {
		return color;
	}

	@Override
	public GameMessage action(Tile t) {
		t.addLaser(new Laser(getLastLaser()));
		if (color.equals(getLastLaser()))
			return new GoalAchievedMessage();
		return new NullMessage();
	}

	@Override
	public Vector2DStack nextPosition() {
		return new Vector2DStack(getPos().add(getLastLaser().getDir()));
	}
}
