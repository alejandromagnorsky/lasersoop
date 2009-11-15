package logic.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import logic.Player;
import logic.tile.SimpleTile;
import logic.tile.Tile;
import logic.tileset.TileSet;

public class Highscores {
	private String levelName;
	private Player[] scores;
	
	public Highscores(String levelName){
		this.levelName = levelName;
		this.scores = new Player[10];
	}
	
	public String getLevelName(){
		return levelName;
	}
	
	public void setLevelName(String levelName){
		this.levelName = levelName;
	}
	
	public boolean loader() throws IOException{
		BufferedReader input = null;
		try {
			File file = new File(levelName + ".scores");
			input = new BufferedReader(new FileReader(file));
			String line;
			/*
			 * Si alguien le pone más de 10 highscores externamente al archivo, toma al archivo
			 * como inválido.
			 */
			for (int i = 0; (line = input.readLine()) != null; i++) {
				if (i >= 10 || line.equals("")|| line.charAt(0) == ','){
					return false;
				}
				char auxChar;
				String auxName = "", auxScore = "";
				boolean flagComa = false;
				for (int j=0; j<line.length(); j++){
					if ((auxChar = line.charAt(j)) == ',' && !flagComa){
						flagComa = true;
					} else if (!flagComa){
						auxName += auxChar;
					} else if (!Character.isDigit(auxChar)) {
						return false;
					} else {
						auxScore += auxChar;
					}
				}
				scores[i] = new Player(auxName, new Integer(auxScore));
				Arrays.sort(scores);
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
		try {
			File file = new File(levelName + ".scores");
			output = new BufferedWriter(new FileWriter(file));
			if (!file.exists()) {
				file.createNewFile();
				output.write(player.getName() + "," + player.getScore());
				return true;
			} else {
				if (player.getScore() < scores[0].getScore()){
					return false;
				}
				scores[0].setScore(player.getScore());
				output.write(scores[0].getName() + "," + scores[0].getScore());
				for (int i=0; i< 10 && scores[i] != null; i++){
					output.write("\n" + scores[i].getName() + "," + scores[i].getScore());
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
