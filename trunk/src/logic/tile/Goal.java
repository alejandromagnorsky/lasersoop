package logic.tile;

import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.LaserColor;
import messages.GameMessage;

public class Goal extends StaticTile {

	private LaserColor color;

	public Goal(Vector2D pos, LaserColor color) {
		super(pos);
		this.color = color;
	}
	
	public LaserColor getColor(){
		return color;
	}	
	
	@Override
	public GameMessage action(Tile t) {
		t.addLaser(new Laser(getLastLaser()));
		if (color.equals(getLastLaser()))
			return new GameMessage("GoalAcchieved");
		return new GameMessage("GoalOK");
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(this.getLastLaser().getDir());
	}
}
