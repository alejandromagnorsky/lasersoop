package logic.tileset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import logic.Vector2D;
import logic.laser.LaserColor;
import logic.mirror.DoubleMirror;
import logic.mirror.Mirror;
import logic.mirror.SimpleMirror;
import logic.tile.Goal;
import logic.tile.Origin;
import logic.tile.SimpleTile;
import logic.tile.Tile;
import logic.tile.Trap;
import logic.tile.Wall;

public class TileSet {
	private String levelName;
	private Tile[][] tileSet;

	public Tile[][] getTileSet() {
		return tileSet;
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

	public Tile at(Vector2D pos) {
		return tileSet[pos.getX()][pos.getY()];
	}

	private boolean set(Tile t) {
		// OJO: CAMBIAR NULL POR INSTANCEOF SIMPLE TILE
		Vector2D pos = t.getPos();
		if (this.at(pos) instanceof SimpleTile) {
			tileSet[pos.getX()][pos.getY()] = t;
			return true;
		} else
			return false;
	}

	public boolean moveTile(Vector2D v1, Vector2D v2) {
		Mirror tmp = (Mirror) at(v1);
		tmp.translate(v2);
		if (set(tmp))
			return true;
		else
			return false;
	}
	
	public boolean initializer(){
		for (int i = 0; i<this.getRows(); i++){
			for (int j = 0; j<this.getCols(); j++){
				if (!this.set(new SimpleTile(new Vector2D(i,j)))){
					return false;
				}
			}
		}
		return true;
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
			/* VER BIEN QUE ONDA ESTO */
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
	public void loadGeneral(String line, int n) {
		String[] strData = new String[n];
		char auxChar;
		int quantComas = 0;
		boolean flagComment = false;
		strData[0] = "";
		for (int i = 0, j = 0; i < line.length() && !flagComment; i++, j++) {
			if ((auxChar = line.charAt(i)) != ' ' && auxChar != '\t') {
				if (auxChar == ',' && quantComas < n - 1) {
					strData[++quantComas] = "";
					j = 0;
				} else if (auxChar == '#' && quantComas == n - 1
						&& strData[n - 1] != "") {
					flagComment = true;
				} else {
					if ((quantComas == 1 && i == 1)
							|| (quantComas < n - 1 && quantComas > n - 3)
							&& j > 2 || !Character.isDigit(auxChar)) {
						System.out.println(line + "TEMP|-----| INVALIDO 1");
						return;
					}
				}
				if (auxChar != ',' && auxChar != '#') {
					strData[quantComas] += auxChar;
				}
			} else {
				j--;
			}
		}
		Integer[] data = new Integer[7];
		for (int i = 0; i < n; i++) {
			data[i] = new Integer(strData[i]);
		}
		if (n == 2) {
			if (data[0] < 5 || data[1] < 5 || data[1] > 20 || data[0] > 20) {
				System.out
						.println("TEMP|-----| fil o cols mayores a 20 o menores a 5");
				return;
			}
			tileSet = new Tile[data[0]][data[1]];
			this.initializer();
		} else {
			if (data[0] > this.getRows() || data[1] > this.getCols()
					|| data[2] > 7 || data[2] < 1) {
				/* Valido los parámetros en general */
				System.out.println("TEMP|-----| incorrectos 1");
				return;
			} else if (((data[2] == 1 || data[2] == 3) && (data[3] < 0 || data[3] > 3))
					|| (data[2] == 4 && data[3] != 0 && data[3] != 1)
					|| (data[2] != 1 && data[2] != 3 && data[2] != 4 && data[3] != 0)) {
				/* Valido los parámetros de la rotación */
				System.out.println("TEMP|-----| incorrectos 2");
				return;
			} else if (((data[2] == 1 || data[2] == 2) && (data[4] < 0
					|| data[4] > 255 || data[5] < 0 || data[5] > 255
					|| data[6] < 0 || data[6] > 255))
					|| (data[2] != 1 && data[2] != 2 && data[4] != 0)) {
				/* Valido los parámetros del color */
				System.out.println("TEMP|-----| incorrectos 3");
				return;
			}
			Vector2D pos = new Vector2D(data[0], data[1]);
			switch (data[2]) {
			case 1:
				LaserColor lc = new LaserColor(data[4], data[5], data[6]);
				this.getTileSet()[data[0]][data[1]] = new Origin(pos, data[3],
						lc);
				break;
			case 2:
				this.getTileSet()[data[0]][data[1]] = new Goal(pos);
				break;
			case 3:
				this.getTileSet()[data[0]][data[1]] = new SimpleMirror(pos,
						data[3]);
				break;
			case 4:
				this.getTileSet()[data[0]][data[1]] = new DoubleMirror(pos,
						data[3]);
				break;
			case 5:
				this.getTileSet()[data[0]][data[1]] = new SimpleMirror(pos,
						data[3]);
				break;
			case 6:
				this.getTileSet()[data[0]][data[1]] = new Wall(pos);
				break;
			case 7:
				this.getTileSet()[data[0]][data[1]] = new Trap(pos);
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
		loadGeneral(line, 2);
	}

	public void loadTile(String line) {
		loadGeneral(line, 7);
	}

	public String toString() {
		return tileSet.toString();
	}
}
