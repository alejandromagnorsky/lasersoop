package logic.laser;

import java.awt.Color;

public class LaserColor extends Color {

	private static final long serialVersionUID = 1L;

	public LaserColor(int r, int g, int b) {
		super(r, g, b);
	}

	public static void main(String args[]) {
		LaserColor c = new LaserColor(255, 111, 1);
		LaserColor c2 = new LaserColor(255, 111, 1);
		System.out.println(c.add(c2));

	}

	/**
	 * Validate values. Return colors in the range 0-255.
	 */
	private int validateValue(int v) {
		if (v > 0 && v < 255)
			return v;
		if (v < 0)
			return 0;
		return 255;
	}

	/**
	 * Add colors numerically. Validates values before, so that the expected
	 * range is always 0-255. ( White + White = White )
	 */
	public LaserColor add(LaserColor c) {
		int r = validateValue(this.getRed() + c.getRed());
		int g = validateValue(this.getGreen() + c.getGreen());
		int b = validateValue(this.getBlue() + c.getBlue());

		return new LaserColor(r, g, b);
	}

	/**
	 * Subtract colors numerically. Validates values before, so that the
	 * expected range is always 0-255. ( Black - White = Black )
	 */
	public LaserColor subtract(LaserColor c) {
		int r = validateValue(this.getRed() - c.getRed());
		int g = validateValue(this.getGreen() - c.getGreen());
		int b = validateValue(this.getBlue() - c.getBlue());

		return new LaserColor(r, g, b);
	}

}
