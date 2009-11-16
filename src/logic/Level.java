package logic;

import gui.GameFrame;

import java.awt.Color;
import java.io.IOException;

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
	private String name;
	private int goalsReached;
	/**
	 * Crea un nuevo nivel con un determinado nombre.
	 * 
	 * @param filename
	 *            Nombre del archivo que contiene los datos del nivel.
	 * @throws IOException
	 *             Si no se pudo cargar el nivel correctamente.
	 */
	public Level(String filename) throws IOException {

		LevelLoader load = new LevelLoader(filename);
		tileSet = load.loader();
		setName(filename);

		GameFrame game = new GameFrame(tileSet, this);
		game.setVisible(true);
	}

	/**
	 * Borra los lasers del tablero
	 */
	public void cleanLevel() {
		for (Tile itr : tileSet)
			itr.eraseLasers();
	}

	/**
	 * "Dispara" los lasers desde los origines.
	 */
	public void update() {
		GameMessage status = new NullMessage();
		cleanLevel();
		goalsReached = 0;
		for (Tile itr : tileSet){
			if (itr.shootLaser())
				walk(itr, status);
			if(itr.laserHasReached())
				goalsReached++;
		}
		//PONERLO EN LA PARTE GRAFICA
		if(isOver())
			System.out.println("Ha ganado el juego");
				
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
		/* MEJORAR ESTO */
		if (t instanceof SemiMirror) {
			nextPos = t.getPos().add(t.getLastLaser().getDir());
			if (tileSet.contains(nextPos)) {
				// Si son mas de dos lasers, mezcla sus colores
				Color color = ((SemiMirror) t).mixLasersColors();
				Laser l = new Laser(t.getLastLaser().getDir(), color);

				tileSet.at(nextPos).addLaser(l);
				walk(tileSet.at(nextPos), status);
			}
		}

		nextPos = t.nextPosition();
		// Borders are walls
		if (!tileSet.contains(nextPos))
			next = new Wall(nextPos);
		else
			next = tileSet.at(nextPos);
		status = t.action(next);
		t = next;
		walk(t, status);
	}

	/**
	 * Setea el nombre del nivel tomando la parte sin extension del String
	 * recibido.
	 * 
	 * @param name
	 *            Nombre de archivo con extension .txt.
	 */
	public void setName(String name) {
		int index = name.lastIndexOf(".");
		this.name = name.substring(0, index);
	}
	
	public boolean isOver(){
		return goalsReached == Goal.countGoals();
	}

	public String getName() {
		return name;
	}
}