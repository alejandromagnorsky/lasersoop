package logic.tile;

import java.awt.Color;
import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;
import messages.GoalAchievedMessage;
import messages.NullMessage;

public class Goal extends StaticTile {

	private Color color;

	public Goal(Vector2D pos, Color color) {
		super(pos);
		this.color = color;
	}

	public Color getColor() {
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
	public Vector2D nextPosition() {
		return this.getPos().add(this.getLastLaser().getDir());
	}
}
