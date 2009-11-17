package logic.io;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import logic.Level;
import logic.Vector2D;
import logic.mirror.DoubleMirror;
import logic.mirror.SemiMirror;
import logic.mirror.SimpleMirror;
import logic.tile.Goal;
import logic.tile.Origin;
import logic.tile.Trap;
import logic.tile.Wall;
import logic.tileset.TileSet;

/**
 * Clase responsable de crear un tablero a partir de un archivo de nivel.
 *
 */
public class LevelLoader {
	private String filename;
	
	private static final int DIMLINE = 2;
	private static final int TILELINE = 7;
	
	/**
	 * Crea una nueva instancia de LevelLoader con el nombre de un archivo de nivel asociado.
	 * 
	 * @param filename
	 * 					Path relativo del archivo respecto de la carpeta 'levels'.
	 */
	public LevelLoader(String filename){
		this.filename = filename;
	}
	
	public String getFilename(){
		return filename;
	}
	
	/**
	 * Levanta toda la información necesaria del archivo de nivel que almacena.
	 * 
	 * @return
	 * 		Devuelve una instancia de TileSet con los datos levantados cargados.
	 * 		'null' si hubo algún error.
	 * 
	 * @throws IOException
	 */
	public Level loader() throws IOException{
		BufferedReader input = null;
		try {
			File file = new File(filename);
			File levelsDir = new File("levels");
			input = new BufferedReader(new FileReader(file));
			Level level = new Level();
			String line, levelName;
			
			System.out.println(file.getParent());
			System.out.println(levelsDir.getPath());
			if ( file.getParent()== null || !file.getParent().equals(levelsDir.getPath())){
				if ((line = input.readLine()) == null) return null;
				while(line.equals("") || line.charAt(0) == '#'){
					line = input.readLine();
				}
				System.out.println(line);
				level.getPlayer().setName(line);
			}
					
			if ((line = input.readLine()) == null) return null;
			while(line.equals("") || line.charAt(0) == '#'){
				line = input.readLine();
			}
			levelName = this.loadName(line);
			
			if ((line = input.readLine()) == null) return null;
			while(line.equals("") || line.charAt(0) == '#'){
				line = input.readLine();
			}
			Integer[] dim;
			if ( (dim = this.loadDimensions(line)) == null ){
				return null;
			}			
			level.setTileSet(new TileSet(dim[0], dim[1]));
			level.getTileset().setLevelName(levelName);
			
			Integer[] tile;
			while ( (line = input.readLine()) != null ) {
				if (!line.equals("") && line.charAt(0) != '#') {
					if ((tile = this.loadTile(line, level.getTileset())) == null){
						return null;
					}
					Vector2D pos = new Vector2D(tile[0], tile[1]);
					switch (tile[2]) {
					case 1:
						Color lc1 = new Color(tile[4], tile[5], tile[6]);
						level.getTileset().add(new Origin(pos, tile[3], lc1));
						break;
					case 2:
						Color lc2 = new Color(tile[4], tile[5], tile[6]);
						level.getTileset().add(new Goal(pos, lc2));
						break;
					case 3:
						level.getTileset().add(new SimpleMirror(pos, tile[3]));
						break;
					case 4:
						level.getTileset().add(new DoubleMirror(pos, tile[3]));
						break;
					case 5:
						level.getTileset().add(new SemiMirror(pos, tile[3]));
						break;
					case 6:
						level.getTileset().add(new Wall(pos));
						break;
					case 7:
						level.getTileset().add(new Trap(pos));
						break;
					}
				}
			}
			return level;
			
		} catch (FileNotFoundException exc) {
			/* VER BIEN QUE ONDA ESTO */
			System.out.println("TEMP|-----|HUBO UNA EXCEPCIÓN");
			return null;
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}
	
	
	/**
	 * Parser que recorre una dada línea buscando una cantidad dada de enteros, de acuerdo
	 * con el formato prefijado para los archivos de nivel.
	 * 
	 * @param line
	 * 			String del cual el parser extraerá los datos.
	 * @param lineType
	 * 			Cantidad de enteros a levantar.
	 * 				- Para una línea con información acerca de la dimensión del tablero,
	 * 				lineType es igual a 2, ya que son 2 los enteros que tiene que levantar
	 * 				(un para la cantidad de filas y otro para la de columnas).
	 * 				- Para una línea con información acerca de una celda, lineType es igual a 7.
	 * @return
	 * 			Devuelve un arreglo con los enteros extraidos, en orden de aparición.
	 * 			'null' si la línea no cumple con el formato prefijado para los archivos de nivel.
	 */
	public Integer[] parser(String line, int lineType){
		String[] strData = new String[lineType];
		char auxChar;
		int quantComas = 0;
		boolean flagComment = false;
		strData[0] = "";
		for (int i = 0; i < line.length() && !flagComment; i++) {
			if ((auxChar = line.charAt(i)) != ' ' && auxChar != '\t') {
				if (auxChar == ',' && quantComas < lineType-1 && strData[quantComas]!="") {
					/* Este IF valida que si se levantó una ',', sea considerado como coma
					 * si y sólo si la cantidad de comas es menor a la que tiene que
					 * levantarse, y que el número detrás de esa coma no sea vacío.
					 * O sea, evita líneas como estas:
					 * 		- ",0,0,0..."
					 * 		- "0,0,,0,0..."
					 * 		- "0,0,0,0,0,0,0,,,,,,,,,"
					 */
					strData[++quantComas] = "";
				} else if (auxChar == '#' && quantComas == lineType-1
							&& strData[lineType-1] != "") {
					/* Este IF hace que si se levantó un '#', sea considerado como comentario
					 * si y sólo si la cantidad de comas que se levantaron es la correcta
					 * y que el último número levantado no sea vacío.
					 * O sea, evita líneas como estas:
					 * 		- "0,0,0,0" teniendo que levantar más de 4 enteros.
					 * 		- "0,0,0,0,0,#"
					 */
					flagComment = true;
				} else if (!Character.isDigit(auxChar)) {
						System.out.println(line + "TEMP|-----| INVALIDO 1");
						return null;
				} else {
					strData[quantComas] += auxChar;
				}
			}
		}
		Integer[] data = new Integer[7];
		for (int i = 0; i < lineType; i++) {
			data[i] = new Integer(strData[i]);
		}
		return data;
	}
	
	/**
	 * Dada una línea, devuelve todo lo que esté antes de '#' en caso de haberlo.
	 * 
	 * @param line
	 * 			Línea a leer.
	 */
	public String loadName(String line) {
		String s = "";
		for (int i = 0; i < line.length() && line.charAt(i) != '#'; i++) {
			s += line.charAt(i);
		}
		return s;
	}

	/**
	 * Levanta las dimensiones del tablero. Valida que estén en el rango de valores aceptados.
	 * 
	 * @param line
	 * 			Línea a leer. Se espera que dicha linea sea tal que contenga información
	 * 			sobre las dimensiones. Sino se considera inválida.
	 * @return
	 * 			Arreglo de 2 enteros, una para la cantidad de filas y otra para la de columnas.
	 * 			'null' sino levanta o no valida.
	 */
	public Integer[] loadDimensions(String line) {
		Integer[] dim = parser(line, DIMLINE);
		if (dim == null || dim[0] < 5 || dim[1] < 5 || dim[1] > 20 || dim[0] > 20) {
			System.out.println("TEMP|-----| fil o cols mayores a 20 o menores a 5 o coso null");
			return null;
		}
		return dim;
	}

	/**
	 * Levanta la información acerca de una celda. Valida que los valores sean coherentes
	 * entre si y con el tablero de juego.
	 * 
	 * @param line
	 * 			Línea a leer. Se espera que dicha linea sea tal que contenga información sobre
	 * 			una celda. Sino se considera inválida.
	 * @param tileSet
	 * 			Tablero al cual pertence dicha celda.
	 * @return
	 * 			Arreglo de 7 enteros. 'null' si no levanta o no valida.
	 */
	public Integer[] loadTile(String line, TileSet tileSet) {
		Integer[] tile = parser(line, TILELINE);
		
		if (tile == null){
			System.out.println(line + "\n" + "TEMP|-----| tile null");
			return null;
			
		/* Valido los parámetros en general */
		} else if (tile[0] >= tileSet.getRows() || tile[1] >= tileSet.getCols()
				|| tile[2] > 7|| tile[2] < 1) {
			
			System.out.println(line + "\n" + "TEMP|-----| incorrectos 1");
			return null;
		
		/* Valido los parámetros de la rotación */
		} else if (((tile[2] == 1 || tile[2] == 3) && (tile[3] < 0 || tile[3] > 3))
				|| ((tile[2] == 4 || tile[2] == 5) && tile[3] != 0 && tile[3] != 1)
				|| ( (tile[2] == 6 || tile[2] == 7) && tile[3] != 0)) {
			
			System.out.println(line + "\n" + "TEMP|-----| incorrectos 2");
			return null;
			
		/* Valido los parámetros del color */
		} else if (((tile[2] == 1 || tile[2] == 2) && (tile[4] < 0
				|| tile[4] > 255 || tile[5] < 0 || tile[5] > 255
				|| tile[6] < 0 || tile[6] > 255))
				|| (tile[2] != 1 && tile[2] != 2 && tile[4] != 0)) {
			
			System.out.println(line + "\n" + "TEMP|-----| incorrectos 3");
			return null;
		}
		return tile;
	}
}
