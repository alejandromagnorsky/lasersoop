package logic;

import gui.GameFrame;

import java.io.IOException;

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
		for (int i = 0; i < tileSet.getRows(); i++)
			for (int j = 0; j < tileSet.getCols(); j++)
				tileSet.at(new Vector2D(j, i)).eraseLasers();
	}

	public void update() {
		GameMessage status = new NullMessage();
		cleanLevel();
		for (int i = 0; i < tileSet.getRows()
				&& !(status instanceof GameOverMessage); i++)
			for (int j = 0; j < tileSet.getCols()
					&& !(status instanceof GameOverMessage); j++) {
				Tile itr = tileSet.at(new Vector2D(i, j));

				if (itr instanceof Origin) {
					Vector2D nextPos = itr.nextPosition();
					Tile next;
					while (!(status instanceof LaserStopMessage)
							&& !(status instanceof GameOverMessage)) {

						// Borders are walls
						if (!tileSet.contains(nextPos)) {
							next = new Wall(nextPos);
						} else {
							next = tileSet.at(nextPos);
						}
						status = itr.action(next);

						System.out.println("Status:" + status);
						System.out.println("Next:" + next.getPos());
						itr = next;
						nextPos = itr.nextPosition();
					}
					if (status instanceof LaserStopMessage)
						status = new NullMessage();
				}
				System.out.println(new Vector2D(i, j));
			}
	}
}
