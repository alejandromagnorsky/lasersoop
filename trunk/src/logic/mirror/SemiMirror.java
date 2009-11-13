package logic.mirror;

import gui.ImageUtils;

import java.awt.Color;
import logic.Vector2D;
import logic.tile.Tile;
import messages.GameMessage;

public class SemiMirror extends DoubleMirror {
	
	private Color color;
	
	public SemiMirror(Vector2D pos, int orientation) {
		super(pos, orientation);
		color = new Color(0,0,0);
	}
	
	@Override
	public GameMessage action(Tile t){
		color = ImageUtils.mix(color, getLastLaser().getColor());
		return super.action(t);
	}
	
}