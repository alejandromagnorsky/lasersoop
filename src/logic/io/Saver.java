package logic.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import logic.tile.SimpleTile;
import logic.tile.Tile;
import logic.tileset.TileSet;

public class Saver {
	private String filename;
	
	public Saver(String filename){
		this.filename = filename;
	}
	
	public String getFilename(){
		return filename;
	}
	
	public void saver(TileSet tileSet) throws IOException {
		BufferedWriter output = null;
		try {
			File file = new File(filename);
			if (file.exists()) {
				if (!file.isFile()) {
					System.out
							.println("TEMP |----| NO INGRESASTE UN ARCHIVO COMO PARAMETRO");
					return;
				}
			} else {
				file.createNewFile();
			}
			output = new BufferedWriter(new FileWriter(file));
			output.write(tileSet.getLevelName() + "\n");
			output.write(tileSet.getRows() + "," + tileSet.getCols() + "\n");

			for (Tile t : tileSet) {
				if (!(t instanceof SimpleTile)) {
					output.write(t.toString() + "\n");
				}
			}
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}
}
