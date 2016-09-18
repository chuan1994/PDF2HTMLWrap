package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Main {

	private static HashMap<String, File> inputFiles = new HashMap<String, File>();
	public static File outputFolder;
	public static File jarFile;
	public boolean validInput;

	public static void main(String[] args) {

		//Processing input params
		if (args.length < 2) {
			printHelp();
			return;
		}

		processArgs(args);
		if (outputFolder.isFile()) {
			return;
		}

		Set<String> keys = inputFiles.keySet();

		//Creating local version of resource, this is deleted after program ends
		extractJar();
		jarFile.deleteOnExit();
		
		//Running each input pdf on its on thread
		ArrayList<HTMLConvert> hcList = new ArrayList<HTMLConvert>();
		
		for (String x : keys) {
			HTMLConvert hc = new HTMLConvert(x, inputFiles.get(x));
			hcList.add(hc);
			hc.execute();
		}
		
		//Wait for all processes to finish
		while(!hcList.isEmpty()){
			ArrayList<HTMLConvert> removeList = new ArrayList<HTMLConvert>();
			for(HTMLConvert hc : hcList){
				if(hc.isDone()){
					removeList.add(hc);
				}
			}
			
			for(HTMLConvert hc : removeList){
				hcList.remove(hc);
			}
		}
	}

	/**
	 * This method processes the input params. 
	 * @param args
	 */
	private static void processArgs(String[] args) {
		File outTemp = new File(args[args.length - 1]);
		setOutput(outTemp);

		for (int i = 0; i < args.length - 1; i++) {
			addInput(args[i]);
		}
	}

	/**
	 * this method will set the specified file as the output folder
	 * @param outFolder
	 */
	private static void setOutput(File outFolder) {
		if (outFolder.isFile()) {
			System.out.println("Invalid output directory");
			printHelp();
			return;
		}
		outputFolder = outFolder;
		if (!outputFolder.exists()) {
			outputFolder.mkdir();
		}
	}

	
	private static void addInput(String path) {
		File y = new File(path);
		if (!y.isFile()) {
			System.out.println("Invalid input file: " + path);
			return;
		}

		inputFiles.put(path, y);
	}

	/**
	 * this method will provide information of how to run the program
	 */
	private static void printHelp() {
		System.out.println("To execute this jar please follow following:");
		System.out.println("Run the jar with a list of input files separated by a space followed by an output folder");
		System.out.println(
				"Example: java -jar PDFExtractPrototype.jar example.pdf example1.pdf example2.pdf outputFolder");
		return;
	}

	/**
	 * this method creates a local version of the PDFtoHTML jar to execute
	 */
	private static void extractJar() {
		try {
			InputStream resourceStream = Main.class.getResourceAsStream("/resources/PDFToHTML.jar");
			jarFile = new File(outputFolder.getPath() + File.separator + "temp.jar");

			OutputStream os = new FileOutputStream(jarFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = resourceStream.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
			
			
			os.close();
			resourceStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
