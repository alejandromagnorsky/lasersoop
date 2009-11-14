package gui;

import java.awt.Image;
import java.util.Vector;

public class TileManager {

	private Vector<Image> tileImages;

	public TileManager() {
		tileImages = new Vector<Image>();
	}

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

	public Image getEmpty() {
		return tileImages.elementAt(0);
	}

	public Image getOrigin() {
		return tileImages.elementAt(1);
	}

	public Image getDoubleMirror() {
		return tileImages.elementAt(10);
	}

	public Image getSimpleMirror() {
		return tileImages.elementAt(11);
	}

	public Image getSemiMirror() {
		return tileImages.elementAt(12);
	}

	public Image getWall() {
		return tileImages.elementAt(3);
	}

	public Image getGoal() {
		return tileImages.elementAt(4);
	}

	public Image getTrap() {
		return tileImages.elementAt(2);
	}

	public Image getLaser() {
		return tileImages.elementAt(5);
	}

	public Image getHalfLaser() {
		return tileImages.elementAt(6);
	}

	public Image getCornerLaser() {
		return tileImages.elementAt(7);
	}

	public Image getTLaser() {
		return tileImages.elementAt(8);
	}

	public Image getCrossLaser() {
		return tileImages.elementAt(9);
	}

}
