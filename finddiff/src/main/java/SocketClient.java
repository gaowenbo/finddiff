
import java.awt.AWTException;
import java.awt.Robot;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	
	private static Socket socket;
	
	public static Socket getSocket() throws AWTException, UnknownHostException, IOException {
		if (socket == null) {
			socket = new Socket("localhost", 9991);  
		}
		return socket;
	}
	
	public static void Close() {
		try {
			getSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public static synchronized void sendMessage(String msg) {
        try {  
        	Socket client = null;
			client = new Socket("localhost", 9991);
            //向服务器端第一次发送字符串     
            OutputStream netOut = client.getOutputStream();  
            DataOutputStream doc = new DataOutputStream(netOut);  
            DataInputStream in = new DataInputStream(client.getInputStream());  
            //向服务器端第二次发送字符串     
            doc.writeUTF("list");  
            String res = in.readUTF();  
            System.out.println(res);  
            doc.writeUTF(msg);  
            res = in.readUTF();  
            System.out.println(res);  
            doc.close();  
            in.close();  
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
          
        }  
	}
}
