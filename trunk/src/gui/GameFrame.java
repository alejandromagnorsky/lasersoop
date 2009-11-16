package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import logic.Level;
import logic.Player;
import logic.tile.SimpleTile;
import logic.tile.Tile;
import logic.tileset.TileSet;

public class GameFrame extends JFrame implements LevelStarter {

	private static final long serialVersionUID = 1L;
	private static final int CELL_SIZE = 50;
	
	private BoardPanel bp;
	private SidebarPanel pp;
	
	private TileSet tileset;
	private Level currentLevel;
	private TileManager tileManager;
	
	private GameMenu gameMenu;

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

	public void updateScreen() {
		clearScreen();
		for (Tile itr : tileset)
			itr.drawTile(tileManager, bp);
	}

	public void clearScreen() {
		for (Tile itr : tileset)
			new SimpleTile(itr.getPos()).drawTile(tileManager, bp);
	}

	public void openMenu() {
		setVisible(false);
		gameMenu.setVisible(true);
	}

	public void initFrame() {

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

		Player player = new Player("Mariano");
		pp = new SidebarPanel(this, player);

		add(pp);
		add(bp);

		paintAll(getGraphics());
		
	}

	public void startLevel(String filename) {
		try {
			currentLevel = new Level(filename);
			tileset = currentLevel.getTileset();
			initFrame();
		} catch (IOException exc) {
			System.out.println(exc);
			JOptionPane.showMessageDialog(null,
					"Archivo incorrecto o corrupto.");
		}
	}

}
