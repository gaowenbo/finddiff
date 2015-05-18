import org.apache.log4j.PropertyConfigurator;

public class Main {

	public static void main(String[] args) {
		PropertyConfigurator.configure(Main.class.getResource("log4j.properties").getPath()); 
		new SimpleFrame();

//		InputUtils.Click(50, 60);
//		InputUtils.Click(50, 60);
//		InputUtils.Click(50, 60);
//		InputUtils.Click(50, 60);
//		InputUtils.Click(50, 60);
	}

}
