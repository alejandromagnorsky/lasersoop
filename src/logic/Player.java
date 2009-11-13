package logic;

public class Player {
	private String name;
	private int score;
	
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