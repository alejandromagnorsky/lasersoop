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
				if (itr instanceof Origin)
					while (!(status.getName().equals("StopLaser"))
							&& !(status.getName().equals("GameOver"))) {
						Tile next = tileSet.at(itr.nextPosition());
						status = itr.action(next);
						System.out.println("Status:" + status);
						System.out.println("Next:" + next.getPos());
						itr = next;
						// MODIFICAR CON UN METODO QUE SE FIJE SI ESTA DENTRO
						if (itr.nextPosition().getX() >= tileSet.getRows()
								|| itr.nextPosition().getX() < 0
								|| itr.nextPosition().getY() >= tileSet.getCols()
								|| itr.nextPosition().getY() < 0)
							break;
					}
				if (status.getName().equals("StopLaser"))
					status = new GameMessage("");
				System.out.println(new Vector2D(i, j));
			}
	}
}
