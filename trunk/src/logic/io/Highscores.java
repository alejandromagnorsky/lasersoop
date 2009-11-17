package logic.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import logic.Player;

public class Highscores {
	private String levelName;
	private TreeSet<Player> scores;

	public Highscores(String levelName) {
		this.levelName = levelName;
		this.scores = new TreeSet<Player>();
	}

	public String getLevelName() {
		return levelName;
	}

	public TreeSet<Player> getScores() {
		return scores;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public boolean loader() throws IOException {
		BufferedReader input = null;
		try {
			/* remueve la extensión .txt para ponerle .scores. */
			File file = new File(levelName.substring(0, levelName
					.lastIndexOf("."))
					+ ".scores");
	//		System.out.println(file.getPath());
	//		System.out.println(file.exists());
			input = new BufferedReader(new FileReader(file));
			String line;
			
			/*
			 * Si alguien le pone más de 10 highscores externamente al archivo,
			 * toma al archivo como inválido.
			 */
			System.out.println(line = input.readLine());
			for (int i = 0; (line /*= input.readLine()*/) != null; i++) {
				
				if (i >= 10 || line.equals("") || line.charAt(0) == ',') {
					return false;
				}
				char auxChar;
				String auxName = "", auxScore = "";
				boolean flagComa = false;
				for (int j = 0; j < line.length(); j++) {
					if ((auxChar = line.charAt(j)) == ',' && !flagComa) {
						flagComa = true;
					} else if (!flagComa) {
						auxName += auxChar;
					} else if (!Character.isDigit(auxChar)) {
						return false;
					} else {
						auxScore += auxChar;
					}
				}
				System.out.println("PLAYER: " + auxName + "PUNTOS: " + auxScore);
				scores.add(new Player(auxName, new Integer(auxScore)));
				line = input.readLine();
			}
			return true;

		} catch (FileNotFoundException exc) {
			/* VER BIEN QUE ONDA ESTO */
			System.out.println("NO SE ENCONTRO EL ARCHIVO, RETORNO FALSE");
			return false;
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

	public boolean saver(Player player) throws IOException {
		BufferedWriter output = null;
		loader();
		try {
			File file = new File(levelName.substring(0, levelName.length() - 4)
					+ ".scores");

			boolean exists = file.exists();

			output = new BufferedWriter(new FileWriter(file));

			if (!exists) {
				file.createNewFile();
				output.write(player.getName() + "," + player.getScore());
				return true;
			} else {
				if (scores.isEmpty()) {
					output.write(player.getName() + "," + player.getScore());
					return true;
				}
				if (scores.size() == 10 && player.getScore() < scores.first().getScore()) {
					return false;
				}
				scores.add(player);
				for (Player p : scores) {
					output.write(p.getName() + "," + p.getScore());
					if (p != scores.last()) {
						output.write("\n");
					}
				}
				return true;
			}
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}
}
