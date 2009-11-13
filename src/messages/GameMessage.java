package messages;

public class GameMessage {

	protected String message;

	public GameMessage() {

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
