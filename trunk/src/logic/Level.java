package logic;

import gui.GameFrame;

import java.io.IOException;
import java.util.Stack;

import logic.laser.Vector2DStack;
import logic.mirror.SemiMirror;
import logic.tile.Origin;
import logic.tile.Tile;
import logic.tile.Wall;
import logic.tileset.TileSet;
import message.EndWalkMessage;
import messages.GameMessage;
import messages.GameOverMessage;
import messages.LaserStopMessage;
import messages.NullMessage;

public class Level {
	private TileSet tileSet;

	public Level(String filename) throws IOException {

		tileSet = new TileSet();
		tileSet.loader(filename);

		GameFrame game = new GameFrame(tileSet, this);
		game.setVisible(true);
	}

	public void cleanLevel() {
		for (int i = 0; i < tileSet.getRows(); i++)
			for (int j = 0; j < tileSet.getCols(); j++)
				tileSet.at(new Vector2D(j, i)).eraseLasers();
	}

	public void update() {

		GameMessage status = new NullMessage();
		cleanLevel();

		// FOR EACH
		for (int i = 0; i < tileSet.getRows()
				&& !(status instanceof GameOverMessage); i++)
			for (int j = 0; j < tileSet.getCols()
					&& !(status instanceof GameOverMessage); j++) {

				Tile itr = tileSet.at(new Vector2D(i, j));

				if (itr instanceof Origin) {

					Vector2DStack vectorStack = new Vector2DStack();
					Stack<Tile> tileStack = new Stack<Tile>();

					vectorStack.add(itr.nextPosition());
					Tile next;
					Vector2D nextPos;
					status = new NullMessage();

					while (!vectorStack.isEmpty()
							&& !(status instanceof EndWalkMessage)) {

						nextPos = vectorStack.pop();

						// Borders are walls
						if (!tileSet.contains(nextPos)) {
							next = new Wall(nextPos);
						} else {
							next = tileSet.at(nextPos);
						}
				
						status = itr.action(next);
						itr = next;
						
						// DESPUES ARMO LA CLASE WALKER
						// PRIMERO QUIERO QUE ESTO FUNCIONE.
						// SIN SEMI MIRRORS ANDA DE 10,
						// PERO NO LO PUDE PROBAR CON SEMI
						// PORQUE NO CARGAN TODAVIA

						// If next tile is a semi mirror, put it in the tileStack
						if (next instanceof SemiMirror)
							tileStack.add(next);
						
						// If laser reached an end, check whether there is
						// any laser thread left (only caused by semi mirrors)
						// If there is no thread left, stop walking
						// If there is a thread left, walk through that thread
						// ( using the last semi mirror walked )
						if (status instanceof LaserStopMessage
								|| status instanceof GameOverMessage)
							if (tileStack.isEmpty())
								status = new EndWalkMessage();
							else
								// Override itr with a Semi Mirror
								itr = tileStack.pop();
						

						vectorStack.add(itr.nextPosition());

					}

					if (status instanceof LaserStopMessage)
						status = new NullMessage();
				}
				System.out.println(new Vector2D(i, j));
			}
	}
}
