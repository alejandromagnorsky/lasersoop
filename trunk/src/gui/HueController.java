package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Utilidad que permite cambiar la tonalidad de una imagen.
 *
 */
public class HueController {

	/**
	 * Metodo que recibe una imagen y un color, y devuelve una copia de la imagen
	 * con la tonalidad de la imagen adaptada al color recibido. 
	 */
	public static Image changeHue(Image image, Color target) {

		// Create image with same size
		BufferedImage result = new BufferedImage(image.getWidth(null), image
				.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		result.getGraphics().drawImage(image, 0, 0, null);

		float[] targetHSB = Color.RGBtoHSB(target.getRed(), target.getGreen(),
				target.getBlue(), null);

		float hue = targetHSB[0];

		for (int x = 0; x < image.getWidth(null); x++) {
			for (int y = 0; y < image.getHeight(null); y++) {

				Color imgColor = new Color(result.getRGB(x, y), true);

				float[] colorHSB = Color.RGBtoHSB(imgColor.getRed(), imgColor
						.getGreen(), imgColor.getBlue(), null);

				Color auxColor = Color.getHSBColor(hue, colorHSB[1],
						colorHSB[2]);

				Color newColor = new Color(auxColor.getRed(), auxColor
						.getGreen(), auxColor.getBlue(), imgColor.getAlpha());

				result.setRGB(x, y, newColor.getRGB());

			}
		}
		return result;

	}
}
