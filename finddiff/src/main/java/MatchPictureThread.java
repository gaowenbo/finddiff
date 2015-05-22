import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class MatchPictureThread extends Thread {
	Logger logger = Logger.getLogger(MatchPictureThread.class);

	List<Rectangle> matchRectList = new ArrayList<Rectangle>();

	Random random = new Random(100);
	
	private BufferedImage lastFirstImg = null;

	@Override
	public void run() {

		while(true)
		try {
			matchRectList.clear();
			BufferedImage fullImage = new Robot()
					.createScreenCapture(new Rectangle(Toolkit
							.getDefaultToolkit().getScreenSize()));

			BufferedImage position = ImageMatchUtils
					.getImage(CommonUtils.getImgBasePath() + "position.bmp");
			Point p = ImageMatchUtils.getMatchedPosition(position, fullImage,
					3, 1);
			if (p == null) {
				sleep(100);
				continue;
			}

			Rectangle firstRect = new Rectangle(p.x + position.getWidth() + 10, p.y + position.getHeight() + 10,
					330, 410);
			
			//
			int StaticWidth = 373;
			
			//check start
			Boolean same = false;
			int sameRGB = 0;
			for (int x = 0; x < firstRect.getWidth(); x++) {
				if (same) {
					break;
				}
				for (int y = 0; y < firstRect.getHeight(); y++) {
					if (same) {
						break;
					}					
					if (fullImage.getRGB(firstRect.x + x, firstRect.y + y) == fullImage.getRGB(firstRect.x + x + StaticWidth, firstRect.y + y)) {
						sameRGB++;
						if (sameRGB * 3 > firstRect.getWidth() * firstRect.getHeight()){
							same = true;
						}
					}

				}
			}
			if (!same) {
				sleep(100);
				continue;
			} else {
			}
			
			logger.info("match start");
			fullImage = LocalInputUtils.getRobot()
			.createScreenCapture(new Rectangle(Toolkit
					.getDefaultToolkit().getScreenSize()));
			BufferedImage first = fullImage.getSubimage(firstRect.x, firstRect.y, firstRect.width,
					firstRect.height);
			BufferedImage secord = fullImage.getSubimage(firstRect.x + StaticWidth, firstRect.y, firstRect.width,
					firstRect.height);

			//check start
			Boolean stable = false;
			sameRGB = 0;
			
			if (lastFirstImg != null) {
				for (int x = 0; x < firstRect.getWidth(); x++) {
					if (stable) {
						break;
					}
					for (int y = 0; y < firstRect.getHeight(); y++) {
						if (stable) {
							break;
						}					
						if (lastFirstImg .getRGB(x, y) == first.getRGB(x, y)) {
							sameRGB++;
							if (sameRGB * 2 > firstRect.getWidth() * firstRect.getHeight()){
								stable = true;
							}
						}

					}
				}
			} 
			
			lastFirstImg = fullImage.getSubimage(firstRect.x, firstRect.y, firstRect.width,
					firstRect.height);
			if(!stable) {
				sleep(100);
				continue;
			}
//			BufferedImage merged = new BufferedImage(firstRect.width,
//					firstRect.height, NORM_PRIORITY);
//			

//			
//			FileOutputStream fos2 = null;
//			try {
//				fos2 = new FileOutputStream("d:\\test1full.bmp");
//				ImageIO.write(fullImage, "bmp", fos2);
//			} catch (Exception e) {
//				logger.error("write image error");
//			}

			BufferedImage merged = new BufferedImage(firstRect.width, firstRect.height, NORM_PRIORITY);

			for (int x = 0; x < first.getWidth(); x++) {
				for (int y = 0; y < first.getHeight(); y++) {
					merged.setRGB(x, y, first.getRGB(x, y) - secord.getRGB(x, y));
					if (first.getRGB(x, y) != secord.getRGB(x, y)
							&& !matchContainPoint(x, y)) {
						addToMatchList(first, secord, x, y);
					}
				}
			}
			
			FileOutputStream fos1 = null;
			try {
				fos1 = new FileOutputStream(CommonUtils.getImgBasePath() + "merge.bmp");
				ImageIO.write(merged, "bmp", fos1);
			} catch (Exception e) {
				logger.error("write image error");
			}

			// print
			if (matchRectList.size() > 10 && matchRectList.size() < 5) {
				continue;
			}
			for (int i = 0; i <matchRectList.size(); i++) {
				Rectangle r = matchRectList.get(i);
				
				//取随机数
				int clickX = (int) (r.getCenterX() + 0.8* (Math.abs(random.nextInt())% r.getWidth() - r.getWidth()/2));
				int clickY = (int) (r.getCenterY() + 0.8* (Math.abs(random.nextInt())% r.getHeight() - r.getHeight()/2));
				
				logger.info(clickX + "," + clickY);

//				printImg(first, r, i);
				InputUtils.click(firstRect.x +clickX, firstRect.y +clickY);
			}
			
			InputUtils.click(firstRect.x - 20, firstRect.y - 130);
			sleep(1500);
		} catch (HeadlessException e) {
			logger.error(e.getMessage());
		} catch (AWTException e) {
			logger.error(e.getMessage());
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}

	private void printImg(BufferedImage first, Rectangle r, int i) {
		// TODO Auto-generated method stub
		try {
			BufferedImage tem = first.getSubimage(r.x, r.y, r.width, r.height);
			FileOutputStream fos1 = null;
			fos1 = new FileOutputStream(CommonUtils.getImgBasePath() +  "test"+ i+".bmp");
			ImageIO.write(tem, "bmp", fos1);
		} catch (Exception e) {
			logger.error("write image error");
		}
	}

	private void addToMatchList(BufferedImage first, BufferedImage secord,
			int x, int y) {
		if (matchRectList.size() > 11) {
			return;
		}
		
		// 先判断一个较小的矩形， 然后向右，向下扩展
		final int miniRectX = x + 5;
		final int miniRectY = y + 5;
		if (miniRectX >= first.getWidth() || miniRectY >= first.getHeight()) {
			return;
		}
		
		if (!diffRect(first, secord, new Rectangle(x, y, 5, 5))) {
			return;
		}

		int endX = miniRectX;
		int endY = miniRectY;

		boolean finalX = false;
		boolean finalY = false;

		for (int i = miniRectX, j = miniRectY; i < first.getWidth()
				|| j < first.getHeight(); i++, j++) {

			
			if (!finalX) {
				if (!(i < first.getWidth())) {
					finalX = true;
					endX = i - 1;
				} else {
					int lineDiffXNumber = 0;
					for (int k = y; k < (finalY ? endY : j); k++) {
						if (first.getRGB(i, k) - secord.getRGB(i, k) > 3
								|| secord.getRGB(i, k) - first.getRGB(i, k) > 3) {
							lineDiffXNumber++;
						}
					}
					if (lineDiffXNumber == 0) {
						finalX = true;
					}
					endX = i;
				}
			}

			
			if (!finalY) {
				if (!(j < first.getHeight())) {
					finalY = true;
					endY = j - 1;
				} else {
					int lineDiffYNumber = 0;
					for (int k = x; k < (finalX ? endX : i); k++) {
						if (first.getRGB(k, j) - secord.getRGB(k, j) > 3
								|| secord.getRGB(k, j) - first.getRGB(k, j) > 3) {
							lineDiffYNumber++;
						}
					}

					if (lineDiffYNumber == 0) {
						finalY = true;
					}
					endY = j ;
				}
			}

			if (finalX && finalY) {
				break;
			}

		}

		matchRectList.add(new Rectangle(x, y, endX - x, endY - y));
	}

	private boolean diffRect(BufferedImage first, BufferedImage secord,
			Rectangle rectangle) {
		//如果已经
		
		int diffCount = 0;
		for (int x = 0; x < rectangle.width; x++) {
			for (int y = 0; y < rectangle.height; y++) {
				if (rectangle.x + x >= first.getWidth()
						|| rectangle.y + y >= first.getHeight()) {
					continue;
				}

				if (first.getRGB(rectangle.x + x, rectangle.y + y) != secord
						.getRGB(rectangle.x + x, rectangle.y + y)) {
					diffCount++;
				}
			}
		}

		return (rectangle.width * rectangle.height / diffCount < 5);
	}

	private Boolean matchContainPoint(int x, int y) {
		for (Rectangle r : matchRectList) {
			if (r.contains(x, y)) {
				return true;
			}
		}
		return false;
	}
}
