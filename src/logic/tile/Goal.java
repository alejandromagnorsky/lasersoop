package logic.tile;

import gui.BoardPanel;
import gui.HueController;
import gui.TileManager;
import java.awt.Color;
import java.awt.Image;
import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;

public class Goal extends StaticTile {

	private Color color;
	private static int countGoals = 0;

	/**
	 * Crea un nuevo destino con un determinado color en la posicion recibida.
	 */
	public Goal(Vector2D pos, Color color) {
		super(pos);
		this.color = color;
		countGoals++;
	}

	public Color getColor() {
		return color;
	}

	public void drawTile(TileManager tm, BoardPanel bp) {

		// Draw lasers first
		drawLasers(tm, bp);

		// Draw image
		Image image = tm.getGoal();
		image = HueController.changeHue(image, color);
		bp.appendImage(getPos().getX(), getPos().getY(), image);

	}

	/**
	 * Agrega un laser en la celda que recibe.
	 * 
	 * @param t
	 *            Celda destino.
	 */
	@Override
	public GameMessage action(Tile t) {
		return t.addLaser(new Laser(getLastLaser()));
	}

	/**
	 * Devuelve la siguiente posicion en el tablero usando la direccion del
	 * laser que tiene.
	 */
	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(this.getLastLaser().getDir());
	}

	/**
	 * Si algun laser que posee tiene el mismo color que si mismo, devuelve
	 * verdadero. En caso contrario, devuelve falso.
	 * 
	 */
	public boolean laserHasReached() {
		for (Laser l : getLasers())
			if (color.equals(l.getColor()))
				return true;
		return false;
	}
	
	public static int countGoals(){
		return countGoals;
	}
	
	public String toString() {
		String pos = getPos().getX() + "," + getPos().getY();
		String color = getColor().getRed() + "," + getColor().getGreen() + ","
				+ getColor().getBlue();
		return pos + ",2,0," + color;
	}
}
