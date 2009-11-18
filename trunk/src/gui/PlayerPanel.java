package gui;

import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logic.Player;

/**
 * Panel que contiene y muestra los datos (nombre y puntaje) de un jugador.
 */

public class PlayerPanel extends JPanel {

	private Player player;
	private JLabel scoreLabel;

	/**
	 * Constructor. Requiere un jugador para inicializarse.
	 * 
	 * @param player
	 */
	public PlayerPanel(Player player) {
		this.player = player;
		setLayout(null);
		setOpaque(false);
		add(getNameLabel());
		add(scoreLabel = getScoreLabel());
	}

	/**
	 * Metodo auxiliar para mostrar el nombre del jugador.
	 * 
	 * @return
	 */

	private JLabel getNameLabel() {
		System.out.println(player == null);
		JLabel label = new JLabel(player.getName());
		label.setBounds(5, 20, 100, 30);
		return label;
	}

	/**
	 * Metodo auxiliar para mostrar el puntaje del jugador.
	 * 
	 * @return
	 */

	private JLabel getScoreLabel() {
		JLabel label = new JLabel("Puntaje: "
				+ String.valueOf(player.getScore()));
		label.setBounds(5, 40, 100, 30);
		return label;
	}

	/**
	 * Metodo que imprime el panel en la pantalla.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		this.scoreLabel
				.setText("Puntaje: " + String.valueOf(player.getScore()));
	}

}
