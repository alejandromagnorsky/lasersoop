package logic;

public class Vector2D {
	private int x;
	private int y;

	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D(int angle) {
		this(0, 0);
		this.setDirection(angle);
	}
	
	public Vector2D(Vector2D v) {
		this(v.x, v.y);
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// La suma esta definida de este modo puesto que estamos sumando
	// un vector direccion con una posicion de la matriz
	public Vector2D add(Vector2D v) {
		int ansX = 0;
		int ansY = 0;
		if (Math.abs(v.getX()) == 1) {
			ansX = this.getX() + v.getY();
			ansY = this.getY() + v.getX();
		}
		if (Math.abs(v.getY()) == 1) {
			ansX = this.getX() - v.getY();
			ansY = this.getY() - v.getX();
		}
		return new Vector2D(ansX, ansY);
	}

	/**
	 * Cambia la direccion de un vector usando la matriz de transformacion
	 */
	public void changeDirection(int angle) {
		double radians = angle * Math.PI / 180;
		int auxX = (int) (Math.cos(radians) * x - Math.sin(radians) * y);
		int auxY = (int) (Math.sin(radians) * x + Math.cos(radians) * y);
		x = auxX;
		y = auxY;
	}

	public void setDirection(int angle) {
		double radians = angle * Math.PI / 180;
		x = (int) Math.cos(radians);
		y = (int) Math.sin(radians);
	}

	public String toString() {
		return "( " + x + " , " + y + " )";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
