package logic;

import gui.GameFrame;
import gui.GameMenu;

public class Game {

	public static void main(String args[]) {

		try{
			
		Level level = new Level("levelTest.txt");
		} catch( Exception e){
			System.out.println(e);
		}

	}
}
