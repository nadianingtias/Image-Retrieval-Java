/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageretrievalbrowser;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
public class Learning {
	
	/**
	 * Get the java.awt.Color of a pixel at a given point.
	 * @param point
	 * @param file
	 * @return
	 */
         public static void mains(String[] args) {
            String path = "E:\\Users\\Nadian\\Documents\\NetBeansProjects\\ImageRetrievalBrowser\\src\\drawable\\1469986689_vector_66_08.png";
            File input = new File(path);
            
        }
	public static Color getColorPoint(Point point, File file) {
		Color color = null;
		try {
			BufferedImage image = ImageIO.read(file);
			color = getColorAtPoint(point, image);
			image.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return color;
	}
	
	/**
	 * Get the set of all java.awt.Color found in an image.
	 * @param point
	 * @param image
	 * @return
	 */
	public static Set<Color> getAllColors(File file) {
		Set<Color> colors = new HashSet<Color>();
		try {
			BufferedImage image = ImageIO.read(file);
			for (int y = 0; y < image.getHeight(); y++) { 
				for (int x = 0; x < image.getWidth(); x++) {
					Color color = getColorAtPoint(new Point(x, y), image);
					if (color != null && !colors.contains(color))
						colors.add(color);
				}
			}
			image.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return colors;
	}
	
	/**
	 * Bytes to java.awt.Color translator.
	 * @param point
	 * @param image
	 * @return
	 */
	private static Color getColorAtPoint(Point point, BufferedImage image) {
		int colorBytes = image.getRGB(point.x, point.y);
		return new Color(
				(colorBytes & 0x00ff0000) >> 16,
				(colorBytes & 0x0000ff00) >> 8,
				(colorBytes & 0x000000ff));
	}
	
}