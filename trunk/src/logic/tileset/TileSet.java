/*
 * SI ACTUALIZARON EL REPOSITORIO Y DE CASUALIDAD VEN ESTO, LEAN:
 * 	SON LAS 4 Y PICO DE LA MAÑANA, PERO POR LO MENOS DEJÉ EL PARSER DE MIERDA ESTE ANDANDO.
 * POR LO MENOS CON LOS CASOS QUE PROBÉ. IGUAL VOY A SEGUIR PROBANDO, Y TRATANDO DE REDUCIR
 * 
 * 1) ESTE PARSER CAPO ME SIRVIÓ PARA DESCUBRIR 2 ERRORES EN EL NIVEL DEL EJEMPLO QUE VIENE EN LAS
 * CONSIGNAS DEL TP.  FIJENSE LA ANTEULTIMA Y ANTEPENULTIMA LINEAS, LAS QUE SON 1,1,5,1,0,0,0 Y LA
 * DE ABAJO DE ESTA.  BUENO, LA CELDAS DE TIPO 5 TIENEN QUE TENER ROTACIÓN CERO PORQUE LO DICE LA
 * CONSIGNA. sIN EMBARGO, SEGUN ESA LINEA, LA ROTACIÓN ES 1. POR LO TANTO, ESE CODIGO DE NIVEL NO
 * SE DEBERIA PODER IMPRIMIR, POR ESO EL PARSER ESTE TIRA CODIGO INCORRECTO. O SEA, LA FOTO QUE
 * PUSIERON EN LAS CONSIGNAS DEL TPE ES UNA ILUSION
 * 
 * 2) ES UN PARSER, O SEA, LO MENOS OBJETOS QUE HAY. AUN ASI, FIJENSE QUE ONDA, PORQUE ME QUEDÓ RE 
 * IMPERATIVO, AUNQUE SUPONOG QUE ES NORMAL, PERO BUE, ME DA ALGO DE MIEDO.
 * 
 * 3) SI LE AGREGAN UN TOSTRING ASÍ BIEN CASERO A LA CLASE LASERCONTROLLER, YA PUEDEN VER COMO QUEDA
 * IMPRESO EN LA CONSOLA EL TABLERO CARGADO.
 * 
 * 4) FIJENSE LOS COMENTARIOS QUE PUSE A LO LARGO DEL CODIGO, SOBRETODO EL DE AGREGAR EL METODO
 * ROTACION EN VECTOR2D, PORQUE NO ESTAMOS CUMPLIENDO LA CONSIGNA DE QUE LA ROTACION TIENE QUE SER
 * UN ENTERO, COMO LA ORIENTACION.
 * 
 * 5) COMMITEO Y ME VOY A DORMIR
 * 
 * 6) AHHHH ME OLVIDABA, SE ME OCURRIO HACIENDO ESTO, QUE SI HACEMOS QUE GAMEOVER, GAMEMESSAGE, 
 * Y ESE TIPO DE COSAS SEAN EXCEPCIONES, NOS AHORRAMOS BANDA DE PROBLEMAS. o SEA, CUANDO PASO ALGO DE
 * ESO SOLO BASTA CON TIRAR UNA EXCEPCION (TODAS HEREDAN DE RUNTIME) Y LISTO
 * 
 * POR EJEMPLO, SE CUELGA EL JUEGO PORQUE CARGO UN NIVEL INVALIDO, ENTONCES HACEMOS:
 * 				throw new GameOver("se colgó el juego");
 * EN CAMBIO, GANASTE EL JUEGO, Y HACEMOS:
 * 				throw new GameOver("ganaste");
 * 
 * O SEA NI SERIA NECESARIO CAMBIAR LO QUE DEVUELVE CADA MÉTODO, EN LOS 2 CASOS QUE PUSE AHI ARRIBA, 
 * TANTO EL LOADER QUE HICE COMO EL METODO QUE SE ENCARGUE DE JUGAR, PUEDEN DEVOLVER COSAS TOTALMENTE
 * DISTINTAS, PERO ARROJAN LA EXCEPCION GAMEMESSAGE
 */

package logic.tileset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import logic.Vector2D;
import logic.laser.Laser;
import logic.laser.LaserColor;
import logic.mirror.SimpleMirror;
import logic.tile.Goal;
import logic.tile.Origin;
import logic.tile.Tile;
import logic.tile.Trap;
import logic.tile.Wall;

public class TileSet{
	private String levelName;
	private Tile[][] tileSet;
	
	public Tile[][] getTileSet(){
		return tileSet;
	}
	
	public int getRows(){
		return tileSet.length;
	}
	
	public int getCols(){
		return tileSet[0].length;
	}
	
	public String getLevelName(){
		return levelName;
	}
	
	public Tile at(Vector2D pos){
		return tileSet[pos.getX()][pos.getY()];
	}	
	
	public void loader(String filename) throws IOException{
		BufferedReader input = null;	
		try {
			File file = new File(filename);
			input = new BufferedReader(new FileReader(file));
			String line;
			
			for(int i=0; (line = input.readLine())!= null; i++){
				if (!line.equals("") && line.charAt(0) != '#'){
					if (i==0){
						this.loadName(line);
					}else if (i==1){
						this.loadDimensions(line);
					}else{
						this.loadTile(line);
					}
				}else{
					i--;
				}
			}	
		} catch(FileNotFoundException exc) {
			/* VER BIEN QUE ONDA ESTO */
			System.out.println("TEMP|-----|HUBO UNA EXCEPCIÓN");
		} finally {
			if (input != null){
				input.close();
			}
		}
	}
	
	/* -----------------------------------------------------------
	 * 		FALTA VER QUE DEVUELVE EN CASO DE ARCHIVO INVALIDO
	 * -----------------------------------------------------------
	 */
	public void loadGeneral(String line, int n){
		String[] strData = new String[n];
		char auxChar;
		int quantComas = 0;
		boolean flagComment = false;
		strData[0] = "";
		for(int i=0, j=0; i<line.length() && !flagComment; i++, j++){
			if ((auxChar=line.charAt(i)) != ' ' && auxChar != '\t'){
				if (auxChar == ',' && quantComas < n-1){
					strData[++quantComas] = "";
					j = 0;
				}else if (auxChar == '#' && quantComas == n-1 && strData[n-1] != ""){
					flagComment = true;
				}else{
					if ((quantComas == 1 && i == 1) || (quantComas < n-1 && quantComas > n-3 ) && j > 2 || !Character.isDigit(auxChar) ){
						System.out.println(line + "TEMP|-----| INVALIDO 1");
						return;
					}
				}
				if (auxChar != ',' && auxChar != '#'){
					strData[quantComas] += auxChar;
				}
			}else{
				j--;
			}
		}
		Integer[] data = new Integer[7];
		for (int i=0; i<n; i++){
			data[i] = new Integer(strData[i]);
		}
		if (n==2){
			if (data[0] < 5 || data[1] < 5 || data[1] > 20 || data[0] > 20){
				System.out.println("TEMP|-----| fil o cols mayores a 20 o menores a 5");
				return;
			}
			tileSet = new Tile[data[0]][data[1]];
		}else{
			if (data[0] > this.getRows() || data[1] > this.getCols() || data[2] > 7 || data[2] < 1){
				/* Valido los parámetros en general */
				System.out.println("TEMP|-----| incorrectos 1");
				return;
			}else if( ((data[2]== 1 || data[2]== 3) && (data[3]<0 || data[3]>3))
						|| (data[2]== 4 && data[3]!= 0 && data[3]!=1)
						|| (data[2]!= 1 && data[2]!= 3 && data[2]!= 4 && data[3]!= 0) ){
				/* Valido los parámetros de la rotación */
				System.out.println("TEMP|-----| incorrectos 2");
				return;
			}else if( ((data[2]== 1 || data[2]== 2) && (data[4]<0 || data[4]>255 || data[5]<0
					|| data[5]>255 || data[6]<0 || data[6]>255 ))
					|| (data[2]!= 1 && data[2]!= 2 && data[4]!=0) ){
				/* Valido los parámetros del color */
				System.out.println("TEMP|-----| incorrectos 3");
				return;
		}
			Vector2D pos = new Vector2D(data[0],data[1]);
			switch(data[2]){
			case 1:
				/*
				 * DEJÉ LA ROTACIÓN INICIALIZADA EN (0,0) PORQUE QUIERO AGREGAR UN MÉTODO EN
				 * LA CLASE VECTOR2D QUE RECIBA UN ENTERO ENTRE 0 Y 3 Y DEVUELVA EL VECTOR
				 * DIRECCIÓN, ASÍ NO PONGO OTRO SARPADO IF ACÁ.
				 */
			//	Vector2D dir = new Vector2D();
				Laser l = new Laser(new Vector2D(0,0), new LaserColor(data[4],data[5],data[6]));
				this.getTileSet()[data[0]][data[1]] = new Origin(pos,l);
			case 2:
				this.getTileSet()[data[0]][data[1]] = new Goal(pos);
			case 3:
				this.getTileSet()[data[0]][data[1]] = new SimpleMirror(pos, data[3]);
			case 4:
				/* ESPEJO DOBLE QUEDA POR HACER */
			case 5:
				/* ESPEJO DOBLE QUEDA POR HACER */
			case 6:
				this.getTileSet()[data[0]][data[1]] = new Wall(pos);
			case 7:
				this.getTileSet()[data[0]][data[1]] = new Trap(pos);
			}
		}
		
	}

	public void loadName(String line){
		for(int i=0; i<line.length() && line.charAt(i) != '#'; i++){
			levelName += line.charAt(i);
		}
	}

	public void loadDimensions(String line){
		/* El 2 es por que necesito leer 2 números */
		loadGeneral(line, 2);
	}
	
	
	public void loadTile(String line){
		/* El 7 es por que necesito leer 7 números */
		loadGeneral(line, 7);
	}

	public String toString(){
		return tileSet.toString();
	}	
}
