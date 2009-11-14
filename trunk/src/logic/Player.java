package logic;

/**
 * Representa un jugador con un nombre y puntaje para cada nivel.
 * 
 */
public class Player {
	private String name;
	private int score; //DEBERIA SER UN ARRAY PUES TIENE UN PUNTAJE POR CADA NIVEL 
	
	public Player(String name){
		this.name = name;
		score = 0;
	}
	
	public String getName(){
		return name;
	}
	
	public void incrementScore(int number){
		score += number;
	}
	
	public int getScore(){
		return score;
	}
	
}