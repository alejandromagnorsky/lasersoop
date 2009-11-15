package logic.tile;

import gui.BoardPanel;
import gui.TileManager;
import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.LaserController;
import messages.GameMessage;
import messages.NullMessage;

/**
 * Representa una celda en el tablero.
 * 
 */
public abstract class Tile extends LaserController {

	protected Vector2D pos;

	protected Tile(Vector2D pos) {
		super();
		this.pos = pos;
	}
	
	public Vector2D getPos() {
		return pos;
	}

	public boolean stopLaser() {
		return false;
	}

	public boolean shootLaser() {
		return false;
	}
	
	@Override
	public GameMessage addLaser(Laser laser) {
		
		if( !hasLasers() )
			return super.addLaser(laser);
		// Si el laser que recibe es igual en direccion a alguno que ya tiene, no lo agrega.
		else if (countLasers() >= 2 || laser.getDir().equals(getLastLaser().getDir())){
			eraseLasers();
			System.out.println("Superposicion: " + laser);
			super.addLaser(laser);
			return new NullMessage();
		}
		return super.addLaser(laser);
	}
	
	public abstract boolean canSwap();

	public abstract void translate(Vector2D dest);

	public abstract void drawLasers(TileManager tm, BoardPanel bp);

	public abstract void drawTile(TileManager tm, BoardPanel bp);
}
