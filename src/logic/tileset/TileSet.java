package logic.tileset;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import logic.Vector2D;
import logic.mirror.DoubleMirror;
import logic.mirror.SemiMirror;
import logic.mirror.SimpleMirror;
import logic.tile.Goal;
import logic.tile.Origin;
import logic.tile.SimpleTile;
import logic.tile.Tile;
import logic.tile.Trap;
import logic.tile.Wall;

public class TileSet implements Iterable<Tile> {
	private String levelName = "";
	private Tile[][] tileSet;

	private static final int DIMLINE = 2;
	private static final int TILELINE = 7;

	public Tile[][] getTileSet() {
		return tileSet;
	}

	public TileSet() {
	}

	public TileSet(int fil, int col) {
		tileSet = new Tile[fil][col];
		for (int i = 0; i < fil; i++) {
			for (int j = 0; j < col; j++) {
				tileSet[i][j] = new SimpleTile(new Vector2D(i, j));
			}
		}
	}

	public int getRows() {
		return tileSet.length;
	}

	public int getCols() {
		return tileSet[0].length;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Tile at(Vector2D pos) {
		return tileSet[pos.getX()][pos.getY()];
	}

	public void set(TileSet ts) {
		this.levelName = ts.levelName;
		this.tileSet = ts.tileSet;
	}

	public void add(Tile t) {
		tileSet[t.getPos().getX()][t.getPos().getY()] = t;
	}

	public void swapTiles(Vector2D v1, Vector2D v2) {
		if (at(v2).canSwap()) {
			at(v1).translate(v2);
			add(at(v1));
			add(new SimpleTile(v1));
		}
	}

	public boolean contains(Vector2D pos) {
		return pos.getX() < getRows() && pos.getX() >= 0
				&& pos.getY() < getCols() && pos.getY() >= 0;
	}

	public void saver(String filename) throws IOException {
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
			output.write(levelName + "\n");
			output.write(this.getRows() + "," + this.getCols() + "\n");

			for (Tile t : this) {
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

	public void loader(String filename) throws IOException {
		BufferedReader input = null;
		try {
			File file = new File(filename);
			input = new BufferedReader(new FileReader(file));
			String line;

			for (int i = 0; (line = input.readLine()) != null; i++) {
				if (!line.equals("") && line.charAt(0) != '#') {
					if (i == 0) {
						this.loadName(line);
					} else if (i == 1) {
						this.loadDimensions(line);
					} else {
						this.loadTile(line);
					}
				} else {
					i--;
				}
			}
		} catch (FileNotFoundException exc) {
			System.out.println("TEMP|-----|HUBO UNA EXCEPCIÓN");
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

	/*
	 * -----------------------------------------------------------
	 * FALTA VER QUE DEVUELVE EN CASO DE ARCHIVO INVALIDO
	 * -----------------------------------------------------------
	 */
	public void loadGeneral(String line, int lineType) {
		/* El parámetro lineType hace referencia a la cantidad de valores que el
		 * parser tiene que levantar por línea. Si la línea tiene información
		 * acerca de: - la dimensión del tablero: lineType = 2. - una celda:
		 * lineType = 7.
		 */
		String[] strData = new String[lineType];
		char auxChar;
		int quantComas = 0;
		boolean flagComment = false;
		strData[0] = "";
		for (int i = 0; i < line.length() && !flagComment; i++) {
			if ((auxChar = line.charAt(i)) != ' ' && auxChar != '\t') {
				if (auxChar == ',' && quantComas < lineType - 1
						&& strData[quantComas] != "") {
					/* Este IF valida que si se levantó una ',', sea considerado
					 * como coma si y sólo si la cantidad de comas es menor a la
					 * que tiene que levantarse, y que el número detrás de esa
					 * coma no sea "". O sea, evita líneas como estas:
					 * 		- ",0,0,0..."
					 * 		- "0,0,,0,0..."
					 * 		- "0,0,0,0,0,0,0,,,,,,,,,"
					 */
					strData[++quantComas] = "";
				} else if (auxChar == '#' && quantComas == lineType - 1
						&& strData[lineType - 1] != "") {
					/* Este IF hace que si se levantó un '#', sea considerado
					 * como inicio de comentarios si y sólo si la cantidad de
					 * comas que se levantaron es la correcta y que el último número
					 * levantado no sea distinto de "". O sea, evita líneas como estas:
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
			this.set(new TileSet(data[0], data[1]));
			
		} else if (lineType == 7){
			if (data[0] > this.getRows() || data[1] > this.getCols()
					|| data[2] > 7 || data[2] < 1) {
				/* Valido los parámetros en general */
				System.out.println(line + "\n" + "TEMP|-----| incorrectos 1");
				return;
			} else if (((data[2] == 1 || data[2] == 3) && (data[3] < 0 || data[3] > 3))
					|| ((data[2] == 4 || data[2] == 5) && data[3] != 0 && data[3] != 1)
					|| ((data[2] == 6 || data[2] == 7) && data[3] != 0)) {
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
				add(new Origin(pos, data[3], lc1));
				break;
			case 2:
				Color lc2 = new Color(data[4], data[5], data[6]);
				add(new Goal(pos, lc2));
				break;
			case 3:
				add(new SimpleMirror(pos, data[3]));
				break;
			case 4:
				add(new DoubleMirror(pos, data[3]));
				break;
			case 5:
				add(new SemiMirror(pos, data[3]));
				break;
			case 6:
				add(new Wall(pos));
				break;
			case 7:
				add(new Trap(pos));
				break;
			}
		}
	}

	public void loadName(String line) {
		for (int i = 0; i < line.length() && line.charAt(i) != '#'; i++) {
			levelName += line.charAt(i);
		}
	}

	public void loadDimensions(String line) {
		loadGeneral(line, DIMLINE);
	}

	public void loadTile(String line) {
		loadGeneral(line, TILELINE);
	}

	/* IMPLEMENTACIÓN DE ITERATOR E ITERABLE */
	public class TileIterator implements Iterator<Tile> {
		private int index;

		@Override
		public boolean hasNext() {
			return index < getRows() * getCols();
		}

		@Override
		public Tile next() {
			return at(new Vector2D(index / getCols(), index++ % getCols()));
		}

		@Override
		public void remove() {
		}

	}

	@Override
	public Iterator<Tile> iterator() {
		return new TileIterator();
	}

}
