
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
	
	public static void click(int x, int y, boolean moveToSafe){
	
		try {
			LocalInputUtils.click(x, y, moveToSafe);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}

	}
	
	public static void click(int x, int y){
		try {
			LocalInputUtils.click(x, y);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}
	
	
//	static Robot robot = null;
//	public static Robot getRobot() throws AWTException {
//		if (robot == null) {
//			robot = new Robot();
//		}
//		return robot;
//	}	
//	
//	public static void click(int x, int y) throws AWTException{
//		click(x, y, true);
//	}
//
//	public static void click(int x, int y, boolean moveToSafe) throws AWTException{
//		
//		while(!Main.a.isFocused()) {
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		//use anjianjingling
//		//1替换文件内容
//		StringBuilder staticPart = getStaticPart();
//		StringBuilder action = new StringBuilder();
//		action.append("MoveTo "+x+", "+y + "\r\n");
//		action.append("Delay " + 300 + "\r\n");
//		//第一次使游戏获得焦点
//		action.append("LeftClick 1" + "\r\n");
//		action.append("LeftClick 1" + "\r\n");
//		action.append("Delay " + 300 + "\r\n");
//		if (moveToSafe) {
//			action.append("MoveTo 15, 15" + "\r\n");
//			//为了重回焦点
//			action.append("Delay " + 300 + "\r\n");
//			action.append("LeftClick 1" + "\r\n");
//		}
//		StringBuilder full = new StringBuilder(staticPart.toString());
//		full.append(action);
//		writetoFile(full);
//		
//		//2执行脚本
//		getRobot().keyPress(121);
//		getRobot().keyRelease(121); //F10
//	}
//	
//	public static void batchClick(List<Point> list) throws AWTException{
//		batchClick(list, true);
//	}
//	
//	public static void batchClick(List<Point> list, boolean moveToSafe) throws AWTException{
//		//use anjianjingling
//				//1替换文件内容
//				StringBuilder staticPart = getStaticPart();
//				StringBuilder action = new StringBuilder();
//				for (Point p: list) {
//					action.append("MoveTo "+p.x+", "+p.y + "\r\n");
//					action.append("Delay " + 100 + "\r\n");
//					action.append("LeftClick 1" + "\r\n");
//					action.append("Delay " + 100 + "\r\n");
//				}
//				if (moveToSafe) {
//					action.append("MoveTo 0, 0" + "\r\n");
//				}
//				StringBuilder full = new StringBuilder(staticPart.toString());
//				full.append(action);
//				writetoFile(full);
//				
//				//2执行脚本
//				getRobot().keyPress(121);
//				getRobot().keyRelease(121); //F10
//	}
//
//	private static void writetoFile(StringBuilder full) {
//		File dest = new File(CommonUtils.getScriptPath());
////		dest.delete();
////		try {
////			dest.createNewFile();
////		} catch (IOException e1) {
////			logger.error(e1.getMessage());
////		}
//		BufferedWriter writer = null;
//		try {
//			writer = new BufferedWriter(new FileWriter(dest));
//			writer.write(full.toString());
//			writer.flush();
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		} finally {
//			try {
//				writer.close();
//			} catch (IOException e) {
//				logger.error(e.getMessage());
//			}
//		}
//	}
//
//	private static StringBuilder getStaticPart() {
//		StringBuilder result = new StringBuilder();
//		File target = new File(CommonUtils.getScriptPath());
//		
//		BufferedReader reader = null;
//		try {
//			reader = new BufferedReader(new FileReader(target));
//			String tempStr = null;
//			while ((tempStr = reader.readLine()) != null) {
//				result.append(tempStr);
//				result.append("\r\n");
//				if (tempStr.equalsIgnoreCase("[Script]")) {
//					//老的数据先不要
//					break;
//				}
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		} finally {
//			try {
//				reader.close();
//			} catch (IOException e) {
//				logger.error(e.getMessage());
//			}
//		}
//		return result;
//	}
}
