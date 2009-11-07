package logic;

public class GameMessage {
	
	private String name;
	
	public GameMessage(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
