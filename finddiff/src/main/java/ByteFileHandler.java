import java.io.File;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;


public class ByteFileHandler {
	public static void main(String[] args) throws IOException {
		
		
		byte[] bytes = FileUtils.getBytesFromFile(new File("C:\\Users\\gaowb\\Desktop\\aaa_lib\\4.mamc"));
		for (int i = 0; i < bytes.length; i++) {

			System.out.print(i +":");
			System.out.print(bytes[i]);
			System.out.print("; ");
		}
		
		File newfile = new File("C:\\Users\\gaowb\\Desktop\\aaa_lib\\4_out.mamc");
		FileUtils.writeByteToFile(newfile, bytes);
		
//		FileUtils.
	}
}
