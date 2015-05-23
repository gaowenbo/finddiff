import java.awt.AWTException;

import javax.swing.JFrame;

import org.apache.log4j.PropertyConfigurator;

public class Main {

	//这里没办法，只好设成全局的
	public static JFrame a;
	
	public static void main(String[] args) throws AWTException, InterruptedException {
		PropertyConfigurator.configure(Main.class.getResource("log4j.properties").getPath()); 
		a = new SimpleFrame();
//		InputUtils.Click(50, 60);
//		InputUtils.Click(50, 60);
//		InputUtils.Click(50, 60);
//		InputUtils.Click(50, 60);
//		InputUtils.Click(50, 60);
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		LocalInputUtils.click(1000, 800);
	}

}
