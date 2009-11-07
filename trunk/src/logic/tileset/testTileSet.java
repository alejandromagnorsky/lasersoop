package logic.tileset;

import java.io.IOException;

public class testTileSet {
	public static void main(String[] args) throws IOException {
		TileSet tablero = new TileSet();
		tablero.loader("C:\\Users\\Alejandro\\Desktop\\Alejandro\\I.T.B.A\\Programación Orientada a Objetos\\Eclipse\\poo2\\src\\logic\\tileset\\levelTest.txt");
		System.out.println(tablero.getRows());
		System.out.println(tablero.getCols());
		System.out.println(tablero.getTileSet());
	}
}
