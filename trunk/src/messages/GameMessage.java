package messages;

public class GameMessage {

	protected String message;

	public GameMessage() {

	}

	/**
	 * Por default no indica que hay que detener al laser.
	 * @return
	 */
	public boolean stopLaser(){
		return false;
	}
	
	public GameMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return message;
	}

	@Override
	public String toString() {
		return this.message;
	}
}
