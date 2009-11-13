package gui;

import java.awt.Image;
import java.util.Vector;

import logic.laser.Laser;
import logic.mirror.Mirror;
import logic.mirror.SemiMirror;
import logic.mirror.SimpleMirror;
import logic.tile.Goal;
import logic.tile.Origin;
import logic.tile.SimpleTile;
import logic.tile.Tile;
import logic.tile.Trap;
import logic.tile.Wall;

public class TileManager {

	private BoardPanel bp;
	private Vector<Image> tileImages;

	public TileManager(BoardPanel bp) {
		this.bp = bp;
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

			// Load mirror images
			tileImages.add(ImageUtils.loadImage("resources/double-mirror.png"));
			tileImages.add(ImageUtils.loadImage("resources/simple-mirror.png"));
			tileImages.add(ImageUtils.loadImage("resources/split-mirror.png"));

		} catch (Exception e) {
			System.out.println("Error loading images.");
		}
	}

	public void addTile(Tile t) {
		// Later check which tile to print
		int i;
		if (t instanceof SimpleTile)
			i = 0;
		else if (t instanceof Origin)
			i = 1;
		else if (t instanceof Trap)
			i = 2;
		else if (t instanceof Wall)
			i = 3;
		else if (t instanceof Goal)
			i = 4;
		else if (t instanceof SemiMirror)
			i = 10;
		else if (t instanceof SimpleMirror)
			i = 9;
		else
			i = 8;

		Image tileImage;

		if (t instanceof Mirror) {
			Mirror m = (Mirror) t;
			int times = 0;

			switch (m.getDegree()) {
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

			tileImage = ImageUtils.rotateImage(tileImages.elementAt(i), times);

		} else if (t instanceof Origin) {

			int times = ((Origin) t).getOrientation();
			tileImage = ImageUtils.rotateImage(tileImages.elementAt(i), times);

		} else
			tileImage = tileImages.elementAt(i);

		// First, fill with empty tiles.
		if (t instanceof SimpleTile)
			bp.setImage(t.getPos().getX(), t.getPos().getY(), tileImage);

		addLasers(t);

		// Hide lasers behind mirrors
		if (!(t instanceof SimpleTile))
			bp.appendImage(t.getPos().getX(), t.getPos().getY(), tileImage);
	}

	private void addLasers(Tile t) {

		int halfLaser = 0;
		int count = t.countLasers();

		Vector<Laser> lasers = t.getLasers();

		if (count > 0 && !(t instanceof Origin)) {

			for (Laser l : lasers) {
				int times = l.getAngle() / 90;

				if (t instanceof Mirror)
					halfLaser = ((Mirror) t).reflects(l) ? 2 : 1;

				Image tmpLaser = ImageUtils.rotateImage(tileImages
						.elementAt(5 + halfLaser), times + 1);

				bp.appendImage(t.getPos().getX(), t.getPos().getY(), tmpLaser);

			}
		}
	}
}
