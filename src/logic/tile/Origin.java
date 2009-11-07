package logic.tile;

import logic.GameMessage;
import logic.Vector2D;
import logic.laser.Laser;

public class Origin extends StaticTile {
	
	private int orientation;
	
	public Origin(Vector2D pos) {
		super(pos);
		Laser l;
		/* switch (orientation)
		case 0: l = new Laser (1,0, ) El color debe proporcionarlo loader mediante un parametro mas */
		 
	}

	/**
	 * Origins have only one laser, defined by its constructor
	 */
	@Override
	public void addLaser(Laser laser) {
	}

	@Override
	public GameMessage action(Tile t) {
		t.addLaser(new Laser(getLastLaser()));
		return null;
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(this.getLastLaser().getDir());
	}

}
