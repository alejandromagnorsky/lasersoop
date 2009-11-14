package messages;

public class LaserStopMessage extends GameMessage {

	public LaserStopMessage() {
		this.message = "Laser reached a wall.";
	}
	
	/**
	 * Indica que hay que detener al laser.
	 */
	public boolean stopLaser(){
		return true;
	}
}
