import com.sun.jna.Library;
import com.sun.jna.Native;


public class MouseInterface {
	
	private static MouseInterface singleton;
	
	private MouseLib lib;
	
	public interface MouseLib extends Library {
		public void DD_mov(int x, int y);
		public void DD_btn(int x);
	}
	
	private MouseInterface(){
		lib = (MouseLib) Native.loadLibrary("DD64.dll", MouseLib.class);
	}
	
	public static MouseInterface getInstance() {
		if (singleton == null) {
			singleton = new MouseInterface();
		}
		return singleton;
	}
	
	public void move(int x, int y) {
		lib.DD_mov(x, y);
	}
	
	public void mouseDown() {
		lib.DD_btn(1);
	}
	
	public void mouseUp() {
		lib.DD_btn(2);
	}
}
