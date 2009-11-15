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

public class Loader {
	private String filename;
	
	private static final int DIMLINE = 2;
	private static final int TILELINE = 7;
	
	public Loader(String filename){
		this.filename = filename;
	}
	
	public String getFilename(){
		return filename;
	}
	
	public void loader(TileSet tileSet) throws IOException{
		BufferedReader input = null;
		try {
			File file = new File(filename);
			input = new BufferedReader(new FileReader(file));
			String line;

			for (int i = 0; (line = input.readLine()) != null; i++) {
				if (!line.equals("") && line.charAt(0) != '#') {
					if (i == 0) {
						this.loadName(tileSet, line);
					} else if (i == 1) {
						this.loadDimensions(tileSet, line);
					} else {
						this.loadTile(tileSet, line);
					}
				} else {
					i--;
				}
			}
		} catch (FileNotFoundException exc) {
			/* VER BIEN QUE ONDA ESTO */
			System.out.println("TEMP|-----|HUBO UNA EXCEPCIÓN");
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}
	
	
	public void loadGeneral(TileSet tileSet, String line, int lineType) {
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
						return;
				} else {
					strData[quantComas] += auxChar;
				}
			}
		}
		Integer[] data = new Integer[7];
		for (int i = 0; i < lineType; i++) {
			data[i] = new Integer(strData[i]);
		}
		if (lineType == 2) {
			if (data[0] < 5 || data[1] < 5 || data[1] > 20 || data[0] > 20) {
				System.out.println("TEMP|-----| fil o cols mayores a 20 o menores a 5");
				return;
			}
			tileSet = new TileSet(data[0], data[1]);
		} else {
			if (data[0] > tileSet.getRows() || data[1] > tileSet.getCols()
					|| data[2] > 7|| data[2] < 1) {
				/* Valido los parámetros en general */
				System.out.println(line + "\n" + "TEMP|-----| incorrectos 1");
				return;
			} else if (((data[2] == 1 || data[2] == 3) && (data[3] < 0 || data[3] > 3))
					|| ((data[2] == 4 || data[2] == 5) && data[3] != 0 && data[3] != 1)
					|| ( (data[2] == 6 || data[2] == 7) && data[3] != 0)) {
				/* Valido los parámetros de la rotación */
				System.out.println(line + "\n" + "TEMP|-----| incorrectos 2");
				return;
			} else if (((data[2] == 1 || data[2] == 2) && (data[4] < 0
					|| data[4] > 255 || data[5] < 0 || data[5] > 255
					|| data[6] < 0 || data[6] > 255))
					|| (data[2] != 1 && data[2] != 2 && data[4] != 0)) {
				/* Valido los parámetros del color */
				System.out.println(line + "\n" + "TEMP|-----| incorrectos 3");
				return;
			}
			Vector2D pos = new Vector2D(data[0], data[1]);
			switch (data[2]) {
			case 1:
				Color lc1 = new Color(data[4], data[5], data[6]);
				tileSet.add(new Origin(pos, data[3], lc1));
				break;
			case 2:
				Color lc2 = new Color(data[4], data[5], data[6]);
				tileSet.add(new Goal(pos, lc2));
				break;
			case 3:
				tileSet.add(new SimpleMirror(pos, data[3]));
				break;
			case 4:
				tileSet.add(new DoubleMirror(pos, data[3]));
				break;
			case 5:
				tileSet.add(new SemiMirror(pos, data[3]));
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

	public void loadName(TileSet tileSet, String line) {
		String s = "";
		for (int i = 0; i < line.length() && line.charAt(i) != '#'; i++) {
			s += line.charAt(i);
		}
		tileSet.setLevelName(s);
	}

	public void loadDimensions(TileSet tileSet, String line) {
		loadGeneral(tileSet, line, DIMLINE);
	}

	public void loadTile(TileSet tileSet, String line) {
		loadGeneral(tileSet, line, TILELINE);
	}
}
