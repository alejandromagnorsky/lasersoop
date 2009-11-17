package logic.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import logic.Level;
import logic.tile.SimpleTile;
import logic.tile.Tile;

public class LevelSaver {
	private String filename;
	
	/**
	 * Crea una nueva instancia de LevelSaver, a partir de una ruta de archivo.
	 * 
	 * @param filename
	 * 			Ruta de archivo.
	 */
	public LevelSaver(String filename){
		this.filename = filename;
	}
	
	public String getFilename(){
		return filename;
	}
	
	/**
	 * Almacena un nivel en la ruta que contienes su variable de instancia.
	 * 
	 * @param level
	 * 			Nivel a almacenar
	 * @throws IOException
	 */
	public void saver(Level level) throws IOException {
		BufferedWriter output = null;
		try {
			File file = new File(filename);
			if (file.exists() && !file.isFile()) {
				System.out.println("TEMP |----| NO INGRESASTE UN ARCHIVO COMO PARAMETRO");
				return;
			} else {
				file.createNewFile();
			}
			output = new BufferedWriter(new FileWriter(file));
			output.write(level.getPlayer().getName() + "\n");
			output.write(level.getName() + "\n");
			output.write(level.getTileset().getRows() + "," + level.getTileset().getCols() + "\n");

			for (Tile t : level.getTileset()) {
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
