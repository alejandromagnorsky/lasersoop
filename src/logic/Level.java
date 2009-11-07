package logic;

import gui.GameFrame;
import java.io.IOException;
import logic.tile.Origin;
import logic.tile.Tile;
import logic.tileset.TileSet;

public class Level {
	private TileSet tileSet;

	public Level(String filename) throws IOException {

		tileSet = new TileSet();
		tileSet.loader(filename);

		GameFrame game = new GameFrame(tileSet, this);
		game.setVisible(true);
	}

	public void update() {
		GameMessage status = new GameMessage("");
		for (int i = 0; i < tileSet.getRows()
				&& !(status.getName().equals("GameOver")); i++)
			for (int j = 0; j < tileSet.getCols()
					&& !(status.getName().equals("GameOver")); j++) {
				Tile itr = tileSet.at(new Vector2D(i, j));
				if (itr instanceof Origin) {
					while (!(status.getName().equals("StopLaser"))
							&& !(status.getName().equals("GameOver"))
							&& itr.getPos().getX() < tileSet.getCols()) {
						Tile next = tileSet.at(itr.nextPosition());
						status = itr.action(next);
						System.out.println("Status:" + status);
						System.out.println("Next:" + next.getPos());
						itr = next;
					}
				}
			}
	}
}
