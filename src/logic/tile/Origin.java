package logic.tile;

import gui.BoardPanel;
import gui.ImageUtils;
import gui.TileManager;
import java.awt.Color;
import java.awt.Image;
import logic.Vector2D;
import logic.laser.Laser;
import messages.GameMessage;

public class Origin extends StaticTile {

	private int orientation;

	/**
	 * Crea un nuevo origen con un determinado color y una orientacion, en la
	 * posicion recibida.
	 */
	public Origin(Vector2D pos, int orientation, Color color) {
		super(pos);
		this.orientation = orientation;
		Vector2D dir = new Vector2D((360 - orientation * 90) % 360);
		Laser l = new Laser(dir, color);
		addLaser(l);
	}

	public int getOrientation() {
		return this.orientation;
	}

	public void drawTile(TileManager tm, BoardPanel bp) {

		// Draw lasers first
		drawLasers(tm, bp);

		// Draw image
		Image image = ImageUtils.rotateImage(tm.getOrigin(), getOrientation());
		bp.appendImage(getPos().getX(), getPos().getY(), image);

	}

	public Color getColor() {
		return this.getLastLaser().getColor();
	}

	@Override
	public boolean shootLaser(){
		return true;
	}
	
	/**
	 * Borra todos los lasers menos el primero que es el que emite.
	 */
	public void eraseLasers() {
		Laser tmp = getFirstLaser();
		super.eraseLasers();
		super.addLaser(tmp);
	}

	@Override
	public GameMessage action(Tile t) {
		GameMessage status = t.addLaser(new Laser(getLastLaser()));
		if (countLasers() >= 2)
			eraseLastLaser();
		return status;
	}

	@Override
	public Vector2D nextPosition() {
		return this.getPos().add(getLastLaser().getDir());
	}
	
	public String toString(){
		String pos = getPos().getX() + "," + getPos().getY();
		String color = getColor().getRed() + ","  + getColor().getGreen() + "," + getColor().getBlue();
		return pos + ",1," + orientation + "," + color;
	}

}
