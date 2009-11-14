package logic;

import gui.GameFrame;

import java.io.IOException;

import logic.laser.Laser;
import logic.mirror.SemiMirror;
import logic.tile.Tile;
import logic.tile.Wall;
import logic.tileset.TileSet;
import messages.GameMessage;
import messages.NullMessage;

public class Level {
	private TileSet tileSet;

	/**
	 * Crea un nuevo nivel con un determinado nombre.
	 * @param filename Nombre del nivel.
	 * @throws IOException Si no se pudo cargar el nivel correctamente.
	 */
	public Level(String filename) throws IOException {
		tileSet = new TileSet();
		tileSet.loader(filename);

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
		for (Tile itr : tileSet)
			if ( itr.shootLaser() )
				walk(itr, status);
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
		if ( status.stopLaser() || t.stopLaser() ) 
			return;
		Vector2D nextPos = t.nextPosition();
		Tile next;

		// Borders are walls
		if (!tileSet.contains(nextPos))
			next = new Wall(nextPos);
		else
			next = tileSet.at(nextPos);

		if (t instanceof SemiMirror) {
			nextPos = t.getPos().add(t.getLastLaser().getDir());
			if (tileSet.contains(nextPos)) {
				Laser l = new Laser(t.getLastLaser().getDir(), t.getLastLaser()
						.getColor());
				tileSet.at(nextPos).addLaser(l);
				walk(tileSet.at(nextPos), status);
			}
		}
		status = t.action(next);
		t = next;
		walk(t, status);
	}
}