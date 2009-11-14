package logic.tile;

import gui.BoardPanel;
import gui.TileManager;
import java.awt.Color;
import java.awt.Image;
import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;
import messages.GoalAchievedMessage;


public class Goal extends StaticTile {

	private Color color;

	/**
	 * Crea un nuevo destino con un determinado color en la posicion recibida.
	 */
	public Goal(Vector2D pos, Color color) {
		super(pos);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void drawTile(TileManager tm, BoardPanel bp) {

		// Draw lasers first
		drawLasers(tm, bp);

		// Draw image
		Image image = tm.getGoal();
		bp.appendImage(getPos().getX(), getPos().getY(), image);

	}

	/**
	 * Agrega un laser en la celda que recibe. Si el laser que posee tiene el
	 * mismo color que si mismo, devuelve un mensaje que avisa que se alcanzo un
	 * destino.
	 * 
	 * @param t
	 *            Celda destino.
	 */
	@Override
	public GameMessage action(Tile t) {
		GameMessage status = t.addLaser(new Laser(getLastLaser()));
		if (color.equals(getLastLaser()))
			return new GoalAchievedMessage();
		return status;
	}

	/**
	 * Devuelve la siguiente posicion en el tablero usando la direccion del
	 * laser que tiene.
	 */
	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(this.getLastLaser().getDir());
	}
	
	public String toString(){
		String pos = getPos().getX() + "," + getPos().getY();
		String color = getColor().getRed() + ","  + getColor().getGreen() + "," + getColor().getBlue();
		return pos + ",2,0," + color;
	}
}
