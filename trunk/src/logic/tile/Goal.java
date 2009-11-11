package logic.tile;

import logic.GameMessage;
import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.LaserColor;

public class Goal extends StaticTile {

	private LaserColor color;

	public Goal(Vector2D pos, LaserColor color) {
		super(pos);
		this.color = color;

	}
	
	public LaserColor getColor(){
		return color;
	}
	
	public int getRed(){
		return this.getColor().getRed();
	}
	
	public int getGreen(){
		return this.getColor().getGreen();
	}
	
	public int getBlue(){
		return this.getColor().getBlue();
	}

	@Override
	public GameMessage action(Tile t) {
		if (this.hasLasers()) {
			t.addLaser(new Laser(getLastLaser()));
			if (color.equals(getLastLaser()))
				return new GameMessage("GoalAcchieved");
		}
		return null;
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(this.getLastLaser().getDir());
	}
}
