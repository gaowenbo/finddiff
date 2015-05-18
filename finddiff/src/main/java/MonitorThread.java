import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


public class MonitorThread extends Thread {
	
	private String lastStr = "";
	
	@Override
	public void run() {
		while (true) {
			if (exceptedStop()) {
				String send = "";
				if (lastStr.length() > 40) {
					send = lastStr.substring(lastStr.length() -40, lastStr.length());
				}
				try {
					EmailHelper.send_email(send);
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			} else {
				try {
					sleep(600000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private boolean exceptedStop() {
		String logStr = "";
		FileReader fr = null;
		try {
			//这里填日志文件路径
			fr = new FileReader("d:\\finddiff\\log.log");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //可以换成工程目录下的其他文本文件
        BufferedReader br=new BufferedReader(fr);
        try {
			while(br.readLine()!=null){
			    logStr += br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if (lastStr.isEmpty()) {
        	lastStr = logStr;
        	return false;
        } else if (logStr.length() < lastStr.length()) {
        	lastStr = logStr;
        	return false;
        } else {
        	String newStr = logStr.substring(lastStr.length());
        	//此处判断 每次开始游戏的日志
        	if (newStr.contains("match start")) {
        		lastStr = logStr;
        		return false;
        	} else {
        		return true;
        	}
        }
	}
}
