package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * TEST CLASS CURRENTLY UNUSED
 * @author cwu323
 *
 */
public class background {

	String input = "C:\\Users\\chuan\\Desktop\\700\\21phd.pdf";
	String output = "C:\\Users\\chuan\\Desktop\\700\\21phd.html";
	
	public background(){
		String resourcePath = getClass().getResource("/resources/PDFToHTML.jar").getPath();
		if(System.getProperty("os.name").contains("indows") && resourcePath.startsWith("/")){
			resourcePath = resourcePath.substring(1, resourcePath.length());
		}
		
		
		String command = "java -jar " + resourcePath + " " + input + " " + output;
		
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line = "";
			
			while((line = reader.readLine())!= null){
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
