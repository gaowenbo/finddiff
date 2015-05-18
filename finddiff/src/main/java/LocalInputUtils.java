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
	public static void click(int x, int y) throws AWTException {
		Robot robot = getRobot();
		robot.mouseMove(x, y);
		robot.delay(100);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(100);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(100);
	}
}
