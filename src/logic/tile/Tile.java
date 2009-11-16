package logic.tile;

import gui.BoardPanel;
import gui.TileManager;
import logic.Player;
import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.LaserController;
import messages.GameMessage;

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
	
	
	/**
	 * Indica si un laser con el color correspondiente llego a un destino.
	 */
	public boolean laserHasReached() {
		return false;
	}
	
	@Override
	public GameMessage addLaser(Laser laser) {
		if( !hasLasers() )
			return super.addLaser(laser);
		// Si el laser que recibe es igual en direccion a alguno que ya tiene, no lo agrega.
		else if (laser.getDir().equals(getLastLaser().getDir())){
			eraseLastLaser();
			return super.addLaser(laser);
		}
		return super.addLaser(laser);
	}
	
	/**
	 * Si contiene por lo menos un laser, suma un punto al puntaje del jugador.
	 */
	public void changeScore(Player player){
		if( hasLasers() )
			player.incrementScore(1);
	}
	
	public abstract boolean canSwap();

	public abstract void translate(Vector2D dest);

	public abstract void drawLasers(TileManager tm, BoardPanel bp);

	public abstract void drawTile(TileManager tm, BoardPanel bp);
}
