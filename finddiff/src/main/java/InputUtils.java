
import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

public class InputUtils {
	
	static Logger logger = Logger.getLogger(InputUtils.class);
	
	static Robot robot = null;
	public static Robot getRobot() throws AWTException {
		if (robot == null) {
			robot = new Robot();
		}
		return robot;
	}	
	
	public static void click(int x, int y) throws AWTException{
		click(x, y, true);
	}

	public static void click(int x, int y, boolean moveToSafe) throws AWTException{
		
		//use anjianjingling
		//1替换文件内容
		StringBuilder staticPart = getStaticPart();
		StringBuilder action = new StringBuilder();
		action.append("MoveTo "+x+", "+y + "\r\n");
		action.append("Delay " + 100 + "\r\n");
		action.append("LeftClick 1" + "\r\n");
		action.append("Delay " + 100 + "\r\n");
		if (moveToSafe) {
			action.append("MoveTo 0, 0" + "\r\n");
		}
		StringBuilder full = new StringBuilder(staticPart.toString());
		full.append(action);
		writetoFile(full);
		
		//2执行脚本
		getRobot().keyPress(121); //F10
		getRobot().keyRelease(121);
	}
	
	public static void batchClick(List<Point> list) throws AWTException{
		batchClick(list, true);
	}
	
	public static void batchClick(List<Point> list, boolean moveToSafe) throws AWTException{
		//use anjianjingling
				//1替换文件内容
				StringBuilder staticPart = getStaticPart();
				StringBuilder action = new StringBuilder();
				for (Point p: list) {
					action.append("MoveTo "+p.x+", "+p.y + "\r\n");
					action.append("Delay " + 100 + "\r\n");
					action.append("LeftClick 1" + "\r\n");
					action.append("Delay " + 100 + "\r\n");
				}
				if (moveToSafe) {
					action.append("MoveTo 0, 0" + "\r\n");
				}
				StringBuilder full = new StringBuilder(staticPart.toString());
				full.append(action);
				writetoFile(full);
				
				//2执行脚本
				getRobot().keyPress(121); //F10
				getRobot().keyRelease(121);
	}

	private static void writetoFile(StringBuilder full) {
		File dest = new File(CommonUtils.getScriptPath());
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(dest));
			writer.write(full.toString());
			writer.flush();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	private static StringBuilder getStaticPart() {
		StringBuilder result = new StringBuilder();
		File target = new File(CommonUtils.getScriptPath());
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(target));
			String tempStr = null;
			while ((tempStr = reader.readLine()) != null) {
				result.append(tempStr);
				result.append("\r\n");
				if (tempStr.equalsIgnoreCase("[Script]")) {
					//老的数据先不要
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return result;
	}
}
