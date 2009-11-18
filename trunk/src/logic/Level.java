package logic;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import logic.io.LevelLoader;
import logic.laser.Laser;
import logic.mirror.SemiMirror;
import logic.tile.Goal;
import logic.tile.Tile;
import logic.tile.Wall;
import logic.tileset.TileSet;
import messages.GameMessage;
import messages.NullMessage;

public class Level {

	private TileSet tileSet;
	private String path;
	private String name;
	private int goalsReached;
	private Player player = new Player("");

	public String getNextLevelPath() {

		return getMinimumFile((new File(path)).getName());
	}

	public static String getLastLevelPath() {
		String filename = null;

		File levelDir = new File("levels");

		for (String str : levelDir.list())
			if (!(new File(str)).isDirectory() && str.contains(".txt"))
				if (filename == null || str.compareTo(filename) > 0)
					filename = str;

		return "levels/" + filename;

	}

	private static String getMinimumFile(String start) {
		String filename = null;

		File levelsDir = new File("levels");

		Vector<String> strings = new Vector<String>();

		// Armar un vector con todos los strings mayores
		for (File file : levelsDir.listFiles())
			if (!file.isDirectory())
				if (start == null || file.getName().compareTo(start) > 0)
					strings.add(file.getName());

		// Seleccionar el menor
		for (String str : strings)
			if (str.contains(".txt"))
				if (filename == null || str.compareTo(filename) < 0)
					filename = str;

		return "levels/" + filename;
	}

	/**
	 * Metodo de clase que devuelve siempre el primer nivel.
	 */
	public static String getFirstLevel() {

		return getMinimumFile(null);
	}

	/**
	 * Crea un nuevo nivel con un determinado nombre.
	 * 
	 * @param filename
	 *            Nombre del archivo que contiene los datos del nivel.
	 * @throws IOException
	 *             Si no se pudo cargar el nivel correctamente.
	 */
	public Level(String filename) {
		Goal.initGoals();
		path = filename;
		LevelLoader load = new LevelLoader(path);
		try {
			tileSet = load.loader().getTileset();
		} catch (Exception e) {
			System.out.println(e);
		}
		name = tileSet.getLevelName();
	}

	/**
	 * Constructor default
	 */
	public Level() {

	}

	public Level(String filename, Player player) throws IOException {
		this(filename);
		this.player = player;

	}

	/**
	 * Borra los lasers del tablero.
	 */
	public void cleanLevel() {
		for (Tile itr : tileSet)
			itr.eraseLasers();
	}

	/**
	 * "Dispara" los lasers desde los origines y suma el puntaje que
	 * corresponda.
	 */
	public void update() {
		GameMessage status = new NullMessage();
		cleanLevel();
		goalsReached = 0;
		player.setScore(0);
		for (Tile itr : tileSet)
			if (itr.shootLaser())
				walk(itr, status);

		// Si el puntaje es -1, es porque hay un laser en una trampa.
		for (Tile itr : tileSet)
			if (player.getScore() != -1) {
				itr.changeScore(player);
				if (itr.laserHasReached())
					goalsReached++;
			}
	}

	/**
	 * Realiza el recorrido del laser.
	 * 
	 * @param t
	 *            Celda la cual agrega un laser en la siguiente posicion.
	 * @param status
	 *            Se utiliza para verificar si el laser se detuvo, y ademas para
	 *            determinar si el jugador gano, perdio o el juego continua.
	 */
	private void walk(Tile t, GameMessage status) {
		if (status.stopLaser())
			return;

		Vector2D nextPos;
		Tile next;
		if (t instanceof SemiMirror) {
			nextPos = t.getPos().add(t.getLastLaser().getDir());
			if (tileSet.contains(nextPos)) {
				// Si son dos lasers, mezcla sus colores
				Color color = ((SemiMirror) t).mixLasersColors();
				Laser l = new Laser(t.getLastLaser().getDir(), color);

				tileSet.at(nextPos).addLaser(l);
				walk(tileSet.at(nextPos), status);
			}
		}

		nextPos = t.nextPosition();
		// Los bordes son paredes.
		if (!tileSet.contains(nextPos))
			next = new Wall(nextPos);
		else
			next = tileSet.at(nextPos);
		status = t.action(next);
		t = next;
		walk(t, status);
	}

	public boolean hasWon() {
		return goalsReached == Goal.countGoals();
	}

	public boolean hasLost() {
		return player.getScore() == -1;
	}

	/**
	 * Setea el nombre del nivel tomando la parte sin extension del String
	 * recibido.
	 * 
	 * @param name
	 *            Nombre de archivo con extension .txt.
	 */

	public String getPath() {
		return path;
	}

	public Player getPlayer() {
		return player;
	}

	public TileSet getTileset() {
		return tileSet;
	}

	public String getName() {
		return name;
	}

	public void setTileSet(TileSet ts) {
		tileSet = ts;
	}
}