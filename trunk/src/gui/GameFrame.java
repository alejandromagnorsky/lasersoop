package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import logic.Level;
import logic.Player;
import logic.io.Highscores;
import logic.io.LevelLoader;
import logic.tile.SimpleTile;
import logic.tile.Tile;
import logic.tileset.TileSet;

/**
 * Frame principal del juego. Contiene el panel de juego y el panel lateral con
 * opciones.
 * 
 */
public class GameFrame extends JFrame implements LevelStarter {

	private static final long serialVersionUID = 1L;
	private static final int CELL_SIZE = 50;

	private BoardPanel bp;
	private SidebarPanel pp;

	private TileSet tileset;
	private Level currentLevel;
	private TileManager tileManager;

	private Player player;

	private GameMenu gameMenu;

	/**
	 * Para construir requiere un menu y la direccion de un nivel para comenzar
	 * 
	 * @param gameMenu
	 * @param filename
	 */
	public GameFrame(GameMenu gameMenu, String filename) {
		this.gameMenu = gameMenu;

		tileManager = new TileManager();
		tileManager.loadTiles();

		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		startLevel(filename);
	}

	public TileSet getTileSet() {
		return tileset;
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public GameMenu getMenu() {
		return gameMenu;
	}

	/**
	 * Metodo que actualiza la pantalla. Limpia la pantalla y se fija si el
	 * usuario gana o pierde
	 */
	public void updateScreen() {

		clearScreen();
		for (Tile itr : tileset)
			itr.drawTile(tileManager, bp);

		repaint();

		if (currentLevel.hasLost()) {
			setEnabled(false);
			JOptionPane.showMessageDialog(null, "¡Has perdido!");
			openMenu();
		}
		if (currentLevel.hasWon())
			nextLevel();
	}

	/**
	 * Metodo que limpia la pantalla.
	 */
	public void clearScreen() {
		for (Tile itr : tileset)
			new SimpleTile(itr.getPos()).drawTile(tileManager, bp);
	}

	/**
	 * Metodo para volver al menu principal
	 */
	public void openMenu() {
		setVisible(false);
		this.
		gameMenu.setVisible(true);
	}

	/**
	 * Metodo que inicializa el panel de juego.
	 */
	public void initFrame() {

		this.removeAll();
		this.frameInit();

		setSize(tileset.getCols() * CELL_SIZE + 150, tileset.getRows()
				* CELL_SIZE + 28);

		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2
				- getHeight() / 2);

		bp = new BoardPanel(tileset.getRows(), tileset.getCols(), CELL_SIZE);

		currentLevel.update();
		updateScreen();

		bp.setListener(new GameListener(this));

		pp = new SidebarPanel(this, player);

		add(pp);
		add(bp);

		paintAll(getGraphics());

	}

	/**
	 * Metodo que pide al usuario el nombre del jugador
	 */
	public void getPlayer() {
		if (player == null) {
			String playerName = JOptionPane.showInputDialog(null,
					"Ingrese el nombre del jugador", "", 1);
			if (playerName == null || playerName.equals(""))
				playerName = "Jugador 1";

			player = new Player(playerName);
			
		}
	}

	/**
	 * Metodo necesario para poder comenzar nuevos niveles. Hace llamadas a
	 * getPlayer e initFrame. En caso de excepcion, avisa al usuario que hubo un
	 * error.
	 */
	public void startLevel(String filename) {
		try {

			if (LevelLoader.isInLevels(new File(filename))) {
				getPlayer();
				currentLevel = new Level(filename, player);
			} else {
				currentLevel = new Level(filename);
				player = currentLevel.getPlayer();
			}

			tileset = currentLevel.getTileset();
			if (tileset == null) {
				JOptionPane.showMessageDialog(null,
						"Archivo incorrecto o corrupto.");
				openMenu();
			}

			setTitle(currentLevel.getName());
			initFrame();

		} catch (IOException exc) {
			System.out.println(exc);
			JOptionPane.showMessageDialog(null,
					"Archivo incorrecto o corrupto.");
		}
	}

	/**
	 * Metodo que selecciona el proximo nivel luego de ganar el actual.
	 */
	public void nextLevel() {
		JOptionPane.showMessageDialog(null, "¡Has ganado!");
		JOptionPane.showMessageDialog(null, "¡Has pasado al próximo nivel!");
		
		Highscores hs = new Highscores(currentLevel.getPath());
		
		try {
			hs.saver(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String path = (new File(currentLevel.getPath())).getAbsolutePath();
		
		
		if( path.equals(Level.getLastLevelPath())){
			JOptionPane.showMessageDialog(null, "¡Has ganado el juego!");
			openMenu();
		}
		
		System.out.println(path);
			
		
		startLevel(currentLevel.getNextLevelPath());
	}

}
