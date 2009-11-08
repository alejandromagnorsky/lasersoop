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
	
	public void cleanLevel(){
		for (int i = 0; i < tileSet.getRows();i++)
			for(int j=0;j<tileSet.getCols();j++)
					tileSet.at(new Vector2D(j,i)).eraseLasers();				
	}

	public void update() {
		GameMessage status = new GameMessage("");
		cleanLevel();
		for (int i = 0; i < tileSet.getRows()
				&& !(status.getName().equals("GameOver")); i++)
			for (int j = 0; j < tileSet.getCols()
					&& !(status.getName().equals("GameOver")); j++) {
				Tile itr = tileSet.at(new Vector2D(i, j));

				if (itr instanceof Origin){
					Vector2D aux = itr.nextPosition();  // YA QUE PARA ALGUNOS TILE
														// IMPORTA LA CANTIDAD DE
													    // VECES QUE SE LLAMA A
														// NEXTPOSITION
					while (!(status.getName().equals("StopLaser")) && !(status.getName().equals("GameOver"))) {
						if (!tileSet.contains(aux))
							break;
						Tile next = tileSet.at(aux);
						status = itr.action(next);
						System.out.println("Status:" + status);
						System.out.println("Next:" + next.getPos());
						itr = next;
						aux = itr.nextPosition();
					}
					if (status.getName().equals("StopLaser"))
					status = new GameMessage("");
				}
				System.out.println(new Vector2D(i, j));		
			}
	}
}
