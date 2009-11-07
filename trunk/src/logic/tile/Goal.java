package logic.tile;

import logic.GameMessage;
import logic.Vector2D;

public class Goal extends StaticTile{

	public Goal(Vector2D pos) {
		super(pos);
	}

	@Override
	public GameMessage action(Tile t) {
		if(this.hasLasers()) return new GoalAchieved();
		return null;
	}

	@Override
	public Vector2D nextPosition() {
		return new Vector2D(0,0);		
	}

	
}
