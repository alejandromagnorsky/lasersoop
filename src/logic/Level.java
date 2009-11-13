package logic;

import gui.GameFrame;

import java.io.IOException;

import logic.laser.Laser;
import logic.mirror.SemiMirror;
import logic.tile.Origin;
import logic.tile.Tile;
import logic.tile.Wall;
import logic.tileset.TileSet;
import messages.GameMessage;
import messages.GameOverMessage;
import messages.LaserStopMessage;
import messages.NullMessage;

public class Level {
	private TileSet tileSet;

	public Level(String filename) throws IOException {
		tileSet = new TileSet();
		tileSet.loader(filename);

		GameFrame game = new GameFrame(tileSet, this);
		game.setVisible(true);
	}

	public void cleanLevel() {
		for (Tile itr : tileSet)
			itr.eraseLasers();
	}

	public void update() {
		GameMessage status = new NullMessage();
		cleanLevel();
		for (Tile itr : tileSet)
			if (itr instanceof Origin)
				walk(itr, status);
	}

	private void walk(Tile t, GameMessage status) {
		if (status instanceof LaserStopMessage
				|| status instanceof GameOverMessage 
				|| (t instanceof SemiMirror && t.countLasers() >= 2)) //Para el caso del loop
			return;
		Vector2D nextPos = t.nextPosition();
		Tile next;
	
		// Borders are walls
		if (!tileSet.contains(nextPos))
			next = new Wall(nextPos);
		else
			next = tileSet.at(nextPos);
				
		if (t instanceof SemiMirror) {
			nextPos = t.getPos().add(t.getLastLaser().getDir());
			if (tileSet.contains(nextPos)) {
				Laser l = new Laser(t.getLastLaser().getDir(), t.getLastLaser()
						.getColor());
				tileSet.at(nextPos).addLaser(l);
				walk(tileSet.at(nextPos), status);
			}
		}
		status = t.action(next);
		t = next;
		walk(t, status);
	}
}