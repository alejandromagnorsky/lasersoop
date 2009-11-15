package logic;

/**
 * Representa un jugador con un nombre y puntaje para cada nivel.
 * 
 */
public class Player implements Comparable<Player>{
	private String name;
	private int score; //DEBERIA SER UN ARRAY PUES TIENE UN PUNTAJE POR CADA NIVEL 
	
	public Player(String name){
		this.name = name;
		score = 0;
	}
	
	public Player(String name, int score){
		this.name = name;
		this.score = score;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setScore(int score){
		this.score = score;
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

	@Override
	public int compareTo(Player p) {
		return this.score - p.score;
	}
}