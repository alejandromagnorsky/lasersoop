package logic.tile;

import logic.GameMessage;
import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.LaserColor;

public class Origin extends StaticTile {
	
	private int orientation;

	public Origin(Vector2D pos, int orientation, LaserColor color) {
		super(pos);
		this.orientation = orientation;
		Vector2D dir = new Vector2D((360 - orientation * 90) % 360);
		Laser l = new Laser(dir, color);
		super.addLaser(l);
		/**
		 * El addLaser de la clase esta para que no se puedan crear lasers extra
		 * en el origin
		 */
	}
	
	public int getOrientation(){
		return this.orientation;
	}
	
	public LaserColor getColor(){
		return this.getLastLaser().getColor();
	}
	
	/**
	 * Origins have only one laser, defined by its constructor
	 */

	public void eraseLasers() {
	}

	// ARREGLAR EL ORIGIN PARA QUE DISPARE EL LASER ORIGINAL CUANDO LE DEJAN DE PASAR MAS DE UNO
	@Override
	public GameMessage action(Tile t) {
		t.addLaser(new Laser(getLastLaser()));
		return new GameMessage("EmisionOK");
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(getLastLaser().getDir());
	}

}
