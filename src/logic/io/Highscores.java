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
			/* remueve la extensi�n .txt para ponerle .scores. */
			File file = new File(levelName.substring(0, levelName
					.lastIndexOf("."))
					+ ".scores");
			input = new BufferedReader(new FileReader(file));
			String line;
			
			/*
			 * Si alguien le pone m�s de 10 highscores externamente al archivo,
			 * toma al archivo como inv�lido.
			 */
			for (int i = 0; (line = input.readLine()) != null; i++) {
				
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
				scores.add(new Player(auxName, new Integer(auxScore)));
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

	public void saver(Player player) throws IOException {
		BufferedWriter output = null;
	
		try {
			loader();
			File file = new File(levelName.substring(0, levelName.length() - 4) + ".scores");

			boolean exists = file.exists();
			output = new BufferedWriter(new FileWriter(file));

			if (!exists) {
				file.createNewFile();
				output.write(player.getName() + "," + player.getScore());
			} else {
				if (scores.isEmpty()) {
					output.write(player.getName() + "," + player.getScore());
				} else if (scores.size() != 10 || player.getScore() >= scores.first().getScore()){
					scores.add(player);
				}
				for (Player p : scores) {
					output.write(p.getName() + "," + p.getScore());
					if (p != scores.last()) {
						output.write("\n");
					}
				}
			}
		} catch(IOException exc){
			System.out.println("ALGUN ERROR LEVANTANDO O GUARDANDO");
		}finally {
			if (output != null) {
				output.close();
			}
		}
	}
}
