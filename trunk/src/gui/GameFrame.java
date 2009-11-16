package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import logic.Level;
import logic.tile.SimpleTile;
import logic.tile.Tile;
import logic.tileset.TileSet;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int CELL_SIZE = 50;
	private BoardPanel bp;
	private TileSet tileset;
	private Level currentLevel;
	private TileManager tileManager;

	public GameFrame(TileSet tileset, Level currentLevel) {
		super(currentLevel.getName());
		this.tileset = tileset;
		this.currentLevel = currentLevel;

		setLayout(null);
		setSize(tileset.getCols() * CELL_SIZE + 150, tileset.getRows()
				* CELL_SIZE + 30);
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2
				- getHeight() / 2);

		bp = new BoardPanel(tileset.getCols(), tileset.getRows(), CELL_SIZE);
		bp.setBackground(Color.WHITE);
		bp.setGridColor(Color.GRAY);

		tileManager = new TileManager();
		tileManager.loadTiles();
		currentLevel.update();
		updateScreen();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		bp.setListener(new GameListener(this, bp));

		SaveButton saveButton = new SaveButton(tileset);
		saveButton.setBounds(getWidth() - 125, getHeight() / 3 + 50, 100, 50);

		add(bp);
		add(saveButton);
		this.setResizable(true);

	}

	public TileSet getTileSet() {
		return tileset;
	}

	public Level getCurrentLevel() {
		return currentLevel;
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

}
