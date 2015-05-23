import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class LocalInputUtils {
	
	static Robot robot = null;
	
	public static Robot getRobot() throws AWTException {
		if (robot == null) {
			robot = new Robot();
		}
		return robot;
	}
	public static void click(int x, int y) throws InterruptedException {
		click(x, y, true);
	}
	public static void click(int x, int y, boolean needSafeMove) throws InterruptedException {
		MouseInterface mouse = MouseInterface.getInstance();
		mouse.move(x, y);
		Thread.sleep(80);
		mouse.mouseDown();
		Thread.sleep(80);
		mouse.mouseUp();
		Thread.sleep(80);
		if (needSafeMove) {
			mouse.move(0, 0);
		}
	}
	
}
