import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class StartGameThread extends Thread{
	

	Logger logger = Logger.getLogger(StartGameThread.class);
	@Override
	public void run() {
		while (true) {
			FileInputStream fis = null;
			try {
				BufferedImage fullScreenImage = LocalInputUtils.getRobot()
						.createScreenCapture(new Rectangle(Toolkit
								.getDefaultToolkit().getScreenSize()));
				fis = new FileInputStream(CommonUtils.getImgBasePath() + "start.bmp");
				BufferedImage img  = ImageIO.read(fis);
				Point matchPosition = ImageMatchUtils.getMatchedPosition(img, fullScreenImage, 3, 50);
				
				if (matchPosition == null) {
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					}
				} else {
					InputUtils.click(matchPosition.x + 20,matchPosition.y+10);
					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					InputUtils.click(matchPosition.x + 20,matchPosition.y+50);
				}
		
				
			} catch (HeadlessException e) {
				logger.error(e.getMessage());
			} catch (AWTException e) {
				logger.error(e.getMessage());
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		
	}
}
