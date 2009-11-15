package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import logic.Level;
import logic.io.LevelSaver;
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
	private JFileChooser saveDialog;
	private JButton saveButton;

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

		saveDialog = new JFileChooser();
		saveButton = new JButton("Guardar");
		saveButton.setBounds(getWidth() - 125, getHeight() / 3 + 50, 100, 50);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = showSaveDialog();
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String filename = saveDialog.getSelectedFile().getPath();
					LevelSaver save = new LevelSaver(filename);
					try {
						save.saver(getTileSet());
					} catch (IOException e1) {
						System.out.println(e1);
					}
				}
			}
		});

		add(bp);
		add(saveButton);
		this.setResizable(false);

	}

	public int showSaveDialog() {
		return saveDialog.showOpenDialog(saveButton);
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
