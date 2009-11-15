package logic.io;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import logic.Vector2D;
import logic.mirror.DoubleMirror;
import logic.mirror.SemiMirror;
import logic.mirror.SimpleMirror;
import logic.tile.Goal;
import logic.tile.Origin;
import logic.tile.Trap;
import logic.tile.Wall;
import logic.tileset.TileSet;

public class LevelLoader {
	private String filename;
	
	private static final int DIMLINE = 2;
	private static final int TILELINE = 7;
	
	public LevelLoader(String filename){
		this.filename = filename;
	}
	
	public String getFilename(){
		return filename;
	}
	
	public TileSet loader() throws IOException{
		BufferedReader input = null;
		try {
			File file = new File(filename);
			input = new BufferedReader(new FileReader(file));
			TileSet tileSet;
			String line, levelName;
			if ((line = input.readLine()) == null) return null;
			levelName = this.loadName(line);
			if ((line = input.readLine()) == null) return null;
			Integer[] dim = this.loadDimensions(line);
			if (dim[0] < 5 || dim[1] < 5 || dim[1] > 20 || dim[0] > 20) {
				System.out.println("TEMP|-----| fil o cols mayores a 20 o menores a 5");
				return null;
			}
			tileSet = new TileSet(dim[0], dim[1]);
			tileSet.setLevelName(levelName);
			
			while ( (line = input.readLine()) != null ) {
				if (!line.equals("") && line.charAt(0) != '#') {
					Integer[] tile = this.loadTile(line);
					if (tile[0] > tileSet.getRows() || tile[1] > tileSet.getCols()
							|| tile[2] > 7|| tile[2] < 1) {
						/* Valido los parámetros en general */
						System.out.println(line + "\n" + "TEMP|-----| incorrectos 1");
						return null;
					} else if (((tile[2] == 1 || tile[2] == 3) && (tile[3] < 0 || tile[3] > 3))
							|| ((tile[2] == 4 || tile[2] == 5) && tile[3] != 0 && tile[3] != 1)
							|| ( (tile[2] == 6 || tile[2] == 7) && tile[3] != 0)) {
						/* Valido los parámetros de la rotación */
						System.out.println(line + "\n" + "TEMP|-----| incorrectos 2");
						return null;
					} else if (((tile[2] == 1 || tile[2] == 2) && (tile[4] < 0
							|| tile[4] > 255 || tile[5] < 0 || tile[5] > 255
							|| tile[6] < 0 || tile[6] > 255))
							|| (tile[2] != 1 && tile[2] != 2 && tile[4] != 0)) {
						/* Valido los parámetros del color */
						System.out.println(line + "\n" + "TEMP|-----| incorrectos 3");
						return null;
					}
					Vector2D pos = new Vector2D(tile[0], tile[1]);
					switch (tile[2]) {
					case 1:
						Color lc1 = new Color(tile[4], tile[5], tile[6]);
						tileSet.add(new Origin(pos, tile[3], lc1));
						break;
					case 2:
						Color lc2 = new Color(tile[4], tile[5], tile[6]);
						tileSet.add(new Goal(pos, lc2));
						break;
					case 3:
						tileSet.add(new SimpleMirror(pos, tile[3]));
						break;
					case 4:
						tileSet.add(new DoubleMirror(pos, tile[3]));
						break;
					case 5:
						tileSet.add(new SemiMirror(pos, tile[3]));
						break;
					case 6:
						tileSet.add(new Wall(pos));
						break;
					case 7:
						tileSet.add(new Trap(pos));
						break;
					}
				}
			}
			return tileSet;
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
	
	public Integer[] parser(String line, int lineType){
		/* El parámetro lineType hace referencia a la cantidad de valores que el parser
		 * tiene que levantar por línea. Si la línea tiene información acerca de:
		 * 		- la dimensión del tablero: lineType = 2.
		 * 		- una celda: lineType = 7.
		 */
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
					 * 		- "0,0,0,0# blaba"
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

	public String loadName(String line) {
		String s = "";
		for (int i = 0; i < line.length() && line.charAt(i) != '#'; i++) {
			s += line.charAt(i);
		}
		return s;
	}

	public Integer[] loadDimensions(String line) {
		return parser(line, DIMLINE);
		
	}

	public Integer[] loadTile(String line) {
		return parser(line, TILELINE);
	}
}
