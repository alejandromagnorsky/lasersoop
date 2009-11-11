package logic.tileset;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	
	private static final int DIMLINE = 2;
	private static final int TILELINE = 7;

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
		if (set(tmp)){
			tileSet[v1.getX()][v1.getY()] = new SimpleTile(v1);
			return true;
		}
		else{
			tmp.translate(v1);
			return false;	
		}
	}

	public void initializer() {
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				tileSet[i][j] = new SimpleTile(new Vector2D(i,j));
			}
		}
	}
	
	public boolean contains(Vector2D pos){
		return pos.getX() < getRows() && pos.getX() >= 0 && pos.getY() < getCols() && pos.getY() >= 0;
	}

	public void saver(String filename) throws IOException{
		BufferedWriter output = null;
		try {
			File file = new File(filename);
			if (file.exists()){
				if (!file.isFile()){
					System.out.println("TEMP |----| NO INGRESASTE UN ARCHIVO COMO PARAMETRO");
					return;
				}
			} else {
				file.createNewFile();
			}
			output = new BufferedWriter(new FileWriter(file));
			output.write(levelName);
			output.newLine();
			output.write(this.getRows() + "," + this.getCols());
			output.newLine();
			
			String aux = "";
			for (int i=0; i < this.getRows(); i++){
				for (int j=0; j < this.getCols(); j++){
					Vector2D pos = new Vector2D(i,j);
					if (!(this.at(pos) instanceof SimpleTile)){
						if ( this.at(pos) instanceof Origin ){
							aux = ",1," + ((Origin)this.at(pos)).getOrientation() + ","
							+ ((Origin)this.at(pos)).getColor().getRed() + "," 
							+ ((Origin)this.at(pos)).getColor().getGreen() + ","
							+ ((Origin)this.at(pos)).getColor().getBlue();
							
						} else if( this.at(pos) instanceof Goal ) {
							aux = ",2,0," + ((Goal)this.at(pos)).getColor().getRed() + ","
							+ ((Goal)this.at(pos)).getColor().getGreen() + ","
							+ ((Goal)this.at(pos)).getColor().getBlue();
							
						} else if( this.at(pos) instanceof SimpleMirror ) {
							aux = ",3," + ((Mirror)this.at(pos)).getOrientation() + ",0,0,0";
						} else if( this.at(pos) instanceof DoubleMirror ) {
							aux = ",4," + ((Mirror)this.at(pos)).getOrientation() + ",0,0,0";
							
						/*} else if( this.at(pos) instanceof SplitMirror ) {
							aux = ",5," + ((SplitMirror)this.at(pos)).getOrientation() + ",0,0,0";*/
						}else if( this.at(pos) instanceof Wall ) {
							aux = ",6,0,0,0,0";
							
						}else if( this.at(pos) instanceof Trap ) {
							aux = ",7,0,0,0,0";
						}
						output.write(i + "," + j + aux);
						output.newLine();
					}
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
	public void loadGeneral(String line, int lineType) {
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
		for (int i = 0, j = 0; i < line.length() && !flagComment; i++, j++) {
			if ((auxChar = line.charAt(i)) != ' ' && auxChar != '\t') {
				if (auxChar == ',' && quantComas < lineType-1) {
					strData[++quantComas] = "";
					j = 0;
				} else if (auxChar == '#' && quantComas == lineType-1 && strData[lineType-1] != "") {
					flagComment = true;
				} else {
					if ((quantComas == 1 && i == 1) 
							|| (quantComas < lineType-1 && quantComas > lineType-3)
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
		for (int i = 0; i < lineType; i++) {
			data[i] = new Integer(strData[i]);
		}
		if (lineType == 2) {
			if (data[0] < 5 || data[1] < 5 || data[1] > 20 || data[0] > 20) {
				System.out.println("TEMP|-----| fil o cols mayores a 20 o menores a 5");
				return;
			}
			tileSet = new Tile[data[0]][data[1]];
			this.initializer();
		} else {
			if (data[0] > this.getRows() || data[1] > this.getCols()
					|| data[2] > 7|| data[2] < 1) {
				/* Valido los parámetros en general */
				System.out.println("TEMP|-----| incorrectos 1");
				return;
			} else if (((data[2] == 1 || data[2] == 3) && (data[3] < 0 || data[3] > 3))
					|| ((data[2] == 4 || data[2] == 5) && data[3] != 0 && data[3] != 1)
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
				LaserColor lc1 = new LaserColor(data[4], data[5], data[6]);
				this.getTileSet()[data[0]][data[1]] = new Origin(pos, data[3], lc1);
				break;
			case 2:
				LaserColor lc2 = new LaserColor(data[4], data[5], data[6]);
				this.getTileSet()[data[0]][data[1]] = new Goal(pos, lc2);
				break;
			case 3:
				this.getTileSet()[data[0]][data[1]] = new SimpleMirror(pos, data[3]);
				break;
			case 4:
				this.getTileSet()[data[0]][data[1]] = new DoubleMirror(pos, data[3]);
				break;
			case 5:
				/* El case 5 lo tengo que hacer para el semiespejo, estoy esperando a que
				 * terminen de definir la clase, mientras está el simpleMirror a modo de prueba
				 */
				this.getTileSet()[data[0]][data[1]] = new SimpleMirror(pos, data[3]);
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
		levelName = "";
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
}
