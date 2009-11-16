package logic.tileset;

import java.util.Iterator;
import logic.Vector2D;
import logic.tile.SimpleTile;
import logic.tile.Tile;

/**
 * Representa un tablero de juego, con un nombre de nivel asociado.
 * 
 */
public class TileSet implements Iterable<Tile> {
	private String levelName = "";
	private Tile[][] tileSet;
	
	/**
	 * Constructor default.
	 */
	public TileSet() {
	}
	
	/**
	 * Dadas las dimensiones, se crea una instancia del tablero.
	 * 
	 * @param fil
	 * 				Filas del tablero.
	 * @param col
	 *				Columnas del tablero.
	 */
	public TileSet(int fil, int col) {
		tileSet = new Tile[fil][col];
		for (int i = 0; i < fil; i++) {
			for (int j = 0; j < col; j++) {
				tileSet[i][j] = new SimpleTile(new Vector2D(i, j));
			}
		}
	}
	
	public Tile[][] getTileSet() {
		return tileSet;
	}

	/**
	 * Devuelve la cantidad de filas del tablero.
	 */
	public int getRows() {
		return tileSet.length;
	}

	/**
	 * Devuelve la cantidad de columnas
	 */
	public int getCols() {
		return tileSet[0].length;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * Devuelve la celda que ocupa una dada posición en el tablero.
	 * 
	 * @param pos
	 * 				Instancia de Vector2D, representa una posición del tablero.
	 */
	public Tile at(Vector2D pos) {
		return tileSet[pos.getX()][pos.getY()];
	}

	/**
	 * Asigna una instancia de TileSet al receptor del mensaje.
	 * 
	 * @param ts
	 * 				TileSet a asignar.
	 */
	public void set(TileSet ts) {
		this.levelName = ts.levelName;
		this.tileSet = ts.tileSet;
	}

	/**
	 * Agregar una celda en el tablero
	 * 
	 * @param t
	 * 				Celda a agregar.
	 */
	public void add(Tile t) {
		tileSet[t.getPos().getX()][t.getPos().getY()] = t;
	}

	/**
	 * Traslada la celda origen hacia la posición destino.
	 * Crea un SimpleTile en la posición origen.
	 * 
	 * @param v1
	 * 				Celda origen.
	 * @param v2
	 * 				Posición destino. 
	 */
	public void swapTiles(Vector2D v1, Vector2D v2) {
		if (at(v2).canSwap()) {
			at(v1).translate(v2);
			add(at(v1));
			add(new SimpleTile(v1));
		}
	}

	/**
	 * Devuelve 'true' si una dada posición está contenida en un tablero.
	 * 'false' en otro caso.
	 * 
	 * @param pos
	 *  				Posición a verificar.
	 */
	public boolean contains(Vector2D pos) {
		return pos.getX() < getRows() && pos.getX() >= 0
				&& pos.getY() < getCols() && pos.getY() >= 0;
	}

	/* IMPLEMENTACIÓN DE ITERATOR */
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