import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;


public class RestartOnError extends Thread {
	
	private int duringTime = 0;
	
	public RestartOnError(int duringTime) {
		this.duringTime = duringTime;
	}
	
	@Override
	public void run() {
		while (true) {
			//遍历图片，点击关闭
			File dir = new File(CommonUtils.getImgBasePath() + "restart");
			if (!dir.isDirectory()) {
				break;
			}
			
			BufferedImage fullScreenImage = null;
			try {
				fullScreenImage = new Robot()
						.createScreenCapture(new Rectangle(Toolkit
								.getDefaultToolkit().getScreenSize()));
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
			File[] imgs = dir.listFiles();
			for (File img: imgs) {
				String s = img.getName().substring(0, img.getName().indexOf(".") - 1);
				
				String[] strs = s.split("_");
				if (strs.length != 3) {
					break;
				}
				
				Point position = ImageMatchUtils.getMatchedPosition(ImageMatchUtils.getImage(img.getPath()), fullScreenImage, 1);
				if (position != null) {
					int offsetX = Integer.parseInt(strs[1]);
					int offsetY = Integer.parseInt(strs[2]);
					InputUtils.click(offsetX, offsetY);
					
				}
			}
			try {
				sleep(1000);
				duringTime = duringTime - 1000;
				if (duringTime < 0) {
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
