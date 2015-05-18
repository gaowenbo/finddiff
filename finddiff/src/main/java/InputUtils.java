

public class InputUtils {
	
	public static void click(int x, int y){
		//local
//		try {
//			LocalInputUtils.click(x, y);
//		} catch (AWTException e) {
//		}
		//remote
//		ClickRequest r = new ClickRequest();
//		r.addPoint(x, y);
//		r.send();
		
		//socket
		SocketClient.sendMessage(x + ","+ y);
	}
}
