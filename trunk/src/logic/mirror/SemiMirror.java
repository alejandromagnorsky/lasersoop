package logic.mirror;

import gui.BoardPanel;
import gui.ImageUtils;
import gui.TileManager;

import java.awt.Color;
import java.awt.Image;
import java.util.Vector;

import logic.Vector2D;
import logic.laser.Laser;
import logic.tile.Tile;
import messages.GameMessage;

public class SemiMirror extends DoubleMirror {

	private Color color;

	/**
	 * Crea un nuevo semi-espejo.
	 * 
	 * @param pos
	 *            Indica la posicion en el tablero.
	 * @param orientation
	 *            Contiene la orientacion del espejo representada por un numero
	 *            entre 0 y 3.
	 */
	public SemiMirror(Vector2D pos, int orientation) {
		super(pos, orientation);
		color = new Color(0, 0, 0);
	}
	
	/**
	 * Se utiliza para frenar el loop infinito.
	 */
	@Override
	public boolean stopLaser(){
		if( countLasers() > 2 )
			return true;
		return false;
	}
	
	public void drawTile(TileManager tm, BoardPanel bp) {

		int times = 0;

		// Just for easy understanding
		switch (getDegree()) {
		case 135:
			times = 0;
			break;
		case 45:
			times = 1;
			break;
		case 315:
			times = 2;
			break;
		case 225:
			times = 3;
			break;
		}

		// Draw lasers first
		drawLasers(tm, bp);

		// Draw image
		Image image = ImageUtils.rotateImage(tm.getSemiMirror(), times);
		bp.appendImage(getPos().getX(), getPos().getY(), image);

	}


	public void drawLasers(TileManager tm, BoardPanel bp) {
		Vector<Laser> lasers = getLasers();

		for (Laser l : lasers) {

			// Nº of rotations to the left.
			int angle = l.getAngle() / 90;
			int direction = getOrientation() * 2 +  angle;

			Image tmpLaser = ImageUtils.rotateImage(tm.getTLaser(), direction);
			bp.appendImage(getPos().getX(), getPos().getY(), tmpLaser);
		}
	}

	/**
	 * Ademas de agregar un laser en la celda recibida, mezcla los colores de
	 * los lasers que tiene.
	 * 
	 * @param t
	 *            Celda destino.
	 */
	@Override
	public GameMessage action(Tile t) {
		color = ImageUtils.mix(color, getLastLaser().getColor());
		return super.action(t);
	}

}