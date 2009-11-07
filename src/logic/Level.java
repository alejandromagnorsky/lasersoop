package logic;

import java.io.IOException;

import logic.tileset.TileSet;

public class Level {
	private TileSet tileSet;
	
	public Level(String filename) throws IOException{
		tileSet.loader(filename);
		start();
	}
	
	public void start(){
		
	}
}
