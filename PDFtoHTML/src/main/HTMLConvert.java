package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.SwingWorker;

/**
 * Class is responsible for converting the pdf document in the bakcground
 * @author cwu323
 *
 */
public class HTMLConvert extends SwingWorker<Void, Void> {

	private String path;
	private File pdf;
	private File output;
	private String command;

	public HTMLConvert(String path, File pdf) {
		this.path = path;
		this.pdf = pdf;
		getOutput();
		
		//Creating the default command to run
		command = "java -jar " + Main.jarFile.getAbsolutePath() + " " + path + " " + output.getPath();
	}

	@Override
	protected Void doInBackground() throws Exception {
		Process p;
		
		try {
			//Running process
			p = Runtime.getRuntime().exec(command);
			
			BufferedReader reader1 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while((line = reader1.readLine())!= null){
			}
			
			reader1.close();
			reader.close();
			
			//Wait for process then destroy after completing
			p.waitFor();
			p.destroy();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Creating the output path. Considers cases where file already exists
	 */
	private void getOutput() {
		String outputPath = Main.outputFolder.getPath() + File.separator + pdf.getName().split("\\.")[0] + ".html";

		try {
			output = new File(outputPath);
			if (!output.exists()) {
				output.createNewFile();
			} else{
				int i = 0;
				while(output.exists()){
					i++;
					outputPath = Main.outputFolder.getCanonicalPath() + File.separator + pdf.getName().split("\\.")[0] + "("
							+ i + ")" + ".html";

					output = new File(outputPath);
				}
				
				output.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
