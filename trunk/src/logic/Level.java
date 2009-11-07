package logic;

import java.io.IOException;

import logic.tile.GameOver;
import logic.tile.Origin;
import logic.tile.StopLaser;
import logic.tile.Tile;
import logic.tileset.TileSet;

public class Level {
	private TileSet tileSet;

	public Level(String filename) throws IOException {
		tileSet.loader(filename);
		start();
	}

	public void start() {
		GameMessage status = null;
		while (!(status instanceof GameOver))
			for (int i = 0; i < tileSet.getRows()
					&& !(status instanceof GameOver); i++)
				for (int j = 0; j < tileSet.getCols()
						&& !(status instanceof GameOver); j++) {
					Tile itr = tileSet.at(new Vector2D(i, j));
					if (itr instanceof Origin) {
						while (!(status instanceof StopLaser)
								&& !(status instanceof GameOver))
							itr.action(tileSet.at(itr.nextPosition()));
					}
				}
	}
}
