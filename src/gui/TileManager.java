package gui;

import java.awt.Image;
import java.util.Vector;

/**
 * Utilidad para administrar todas las imagenes necesarias para el juego.
 * 
 */
public class TileManager {

	private Vector<Image> tileImages;

	/**
	 * Constructor default. Inicializa un vector de Imagenes.
	 */
	public TileManager() {
		tileImages = new Vector<Image>();
	}

	/**
	 * Metodo que carga todos los recursos necesarios para el juego. En caso de
	 * una excepcion, avisa al usuario.
	 */
	public void loadTiles() {
		try {

			// Load empty tile
			tileImages.add(ImageUtils.loadImage("resources/empty-tile.png"));

			// Load misc. images
			tileImages.add(ImageUtils.loadImage("resources/source.png"));
			tileImages.add(ImageUtils.loadImage("resources/trap.png"));
			tileImages.add(ImageUtils.loadImage("resources/wall.png"));
			tileImages.add(ImageUtils.loadImage("resources/target.png"));

			// Load laser image
			tileImages.add(ImageUtils.loadImage("resources/laser.png"));
			tileImages.add(ImageUtils.loadImage("resources/half-laser.png"));
			tileImages.add(ImageUtils.loadImage("resources/corner-laser.png"));
			tileImages.add(ImageUtils.loadImage("resources/t-laser.png"));
			tileImages.add(ImageUtils.loadImage("resources/cross-laser.png"));

			// Load mirror images
			tileImages.add(ImageUtils.loadImage("resources/double-mirror.png"));
			tileImages.add(ImageUtils.loadImage("resources/simple-mirror.png"));
			tileImages.add(ImageUtils.loadImage("resources/split-mirror.png"));

		} catch (Exception e) {
			System.out.println("Error loading images.");
		}
	}

	/**
	 * Retorna la imagen del tile vacio
	 */
	public Image getEmpty() {
		return tileImages.elementAt(0);
	}

	/**
	 * Retorna la imagen del tile Origin
	 */
	public Image getOrigin() {
		return tileImages.elementAt(1);
	}

	/**
	 * Retorna la imagen del tile DoubleMirror
	 */
	public Image getDoubleMirror() {
		return tileImages.elementAt(10);
	}

	/**
	 * Retorna la imagen del tile SimpleMirror
	 */
	public Image getSimpleMirror() {
		return tileImages.elementAt(11);
	}

	/**
	 * Retorna la imagen del tile SemiMirror
	 */
	public Image getSemiMirror() {
		return tileImages.elementAt(12);
	}

	/**
	 * Retorna la imagen del tile Wall
	 */
	public Image getWall() {
		return tileImages.elementAt(3);
	}

	/**
	 * Retorna la imagen del tile Goal
	 */
	public Image getGoal() {
		return tileImages.elementAt(4);
	}

	/**
	 * Retorna la imagen del tile Trap
	 */
	public Image getTrap() {
		return tileImages.elementAt(2);
	}

	/**
	 * Retorna la imagen del Laser
	 */
	public Image getLaser() {
		return tileImages.elementAt(5);
	}

	/**
	 * Retorna la imagen del Half-Laser
	 */
	public Image getHalfLaser() {
		return tileImages.elementAt(6);
	}

	/**
	 * Retorna la imagen del Corner Laser
	 */
	public Image getCornerLaser() {
		return tileImages.elementAt(7);
	}

	/**
	 * Retorna la imagen del T Laser
	 */
	public Image getTLaser() {
		return tileImages.elementAt(8);
	}

	/**
	 * Retorna la imagen del Cross Laser
	 */
	public Image getCrossLaser() {
		return tileImages.elementAt(9);
	}

}
