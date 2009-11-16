package logic.tile;

import gui.BoardPanel;
import gui.HueController;
import gui.ImageUtils;
import gui.TileManager;

import java.awt.Image;
import java.util.Vector;

import logic.Player;
import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;
import messages.GameOverMessage;

public class Trap extends StaticTile {

	/**
	 * Crea una nueva trampa en la posicion recibida.
	 */
	public Trap(Vector2D pos) {
		super(pos);
	}

	/**
	 * Devuelve un mensaje que indica que termino el juego.
	 */
	public GameMessage action(Tile t) {
		return new GameOverMessage();
	}

	public void drawTile(TileManager tm, BoardPanel bp) {
		// Draw image first
		Image image = tm.getTrap();
		bp.appendImage(getPos().getX(), getPos().getY(), image);

		// Draw lasers
		drawLasers(tm, bp);
	}

	public void drawLasers(TileManager tm, BoardPanel bp) {
		Vector<Laser> lasers = getLasers();

		for (Laser l : lasers) {
			// Nº of rotations to the left.
			int times = -l.getAngle() / 90;
			Image tmpLaser = ImageUtils.rotateImage(tm.getHalfLaser(), times);
			tmpLaser = HueController.changeHue(tmpLaser, l.getColor());
			bp.appendImage(getPos().getX(), getPos().getY(), tmpLaser);
		}
	}

	/**
	 * Devuelve su propia posicion ya detiene al laser.
	 */
	@Override
	public Vector2D nextPosition() {
		return this.getPos();
	}
	
	/**
	 * Las trampas setean al puntaje en 0.
	 */
	@Override
	public void changeScore(Player player){
		if ( hasLasers() )
			player.setScore(-1);
	}
	
	public String toString(){
		String pos = getPos().getX() + "," + getPos().getY();
		return pos + ",7,0,0,0,0";
	}

}
