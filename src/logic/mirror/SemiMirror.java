package logic.mirror;

import java.util.Vector;
import logic.Vector2D;
import logic.laser.Laser;

public class SemiMirror extends DoubleMirror {

	public SemiMirror(Vector2D pos, int orientation) {
		super(pos, orientation);
	}
	
	public boolean stopLoop(){
		Vector<Laser> lasers = getLasers();
		System.out.println("Lasers: "+lasers);
		System.out.println("CountLasers: "+countLasers());
		for(int i=0; i < countLasers();i++)
			for(int j=i+1; j < countLasers();j++)
				if( lasers.elementAt(i).equals(lasers.elementAt(j)) )
					return true;
		return false;
		
	}

}