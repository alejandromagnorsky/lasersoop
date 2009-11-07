package logic;

import gui.GameFrame;

import java.io.IOException;

import logic.tile.GameOver;
import logic.tile.Origin;
import logic.tile.StopLaser;
import logic.tile.Tile;
import logic.tileset.TileSet;

public class Level {
	private TileSet tileSet;

	public Level(String filename) throws IOException {
		
		tileSet = new TileSet();
		tileSet.loader(filename);
		
		GameFrame game = new GameFrame(tileSet);
			
		//start();
	}

	public static void main(String args[]){
		try {
			Level level= new Level("levelTest.txt");
		} catch( Exception e){
			System.out.println(e);
		}
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
