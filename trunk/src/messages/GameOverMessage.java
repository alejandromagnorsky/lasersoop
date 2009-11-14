package messages;

public class GameOverMessage extends GameMessage {

	public GameOverMessage() {
		this.message = "Game Over.";
	}
	
	/**
	 * Indica que hay que detener al laser.
	 */
	public boolean stopLaser(){
		return true;
	}
}
