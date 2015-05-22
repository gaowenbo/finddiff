
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class ImageMatchUtils {
	
	static Logger logger = Logger.getLogger(ImageMatchUtils.class);
	
	public static Point getMatchedPosition(BufferedImage img,  BufferedImage targetImage) {
		return getMatchedPosition(img, targetImage, 3);
	}

	public static Point getMatchedPosition(BufferedImage img,  BufferedImage targetImage, int step) {
		return getMatchedPosition(img, targetImage, step, 3);
	}
	
	public static Point getMatchedPosition(BufferedImage img,  BufferedImage targetImage, int step, int colorFloat) {
		for (int x = 0; x < targetImage.getWidth() - img.getWidth(); x++ ){
			for (int y = 0; y < targetImage.getHeight() - img.getHeight(); y++ ) {
				boolean match = true;
				for (int i = 0; i < img.getWidth(); i += step) {
					for (int j = 0; j < img.getHeight(); j += step) {
						if (!similarColor(img.getRGB(i, j), targetImage.getRGB(x + i, y + j), colorFloat)) {
							match = false;
							break;
						}
					}
					if (!match) {
						break;
					}
				}
				if (!match) {
					continue;
				}
				
				return new Point(x, y);
			}
		}
		return null;
	}
	
	private static boolean similarColor(int a, int b, int colorFloat) {
		Color ca = new Color(a);
		Color cb = new Color(b);
		if (ca.getRed() - cb.getRed() > colorFloat || cb.getRed() - ca.getRed() > colorFloat) {
			return false;
		} else if (ca.getGreen() - cb.getGreen() > colorFloat ||  cb.getGreen() - ca.getGreen() > colorFloat){
			return false;
		} else if (ca.getBlue() - cb.getBlue() > colorFloat ||  cb.getBlue() - ca.getBlue() > colorFloat){
			return false;
		}
		
		return true;
	}
	
	public static BufferedImage getImage(String name){
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(name);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		 
		try {
			return ImageIO.read(fis);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
}
