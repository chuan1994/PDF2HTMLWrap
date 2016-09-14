package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;

public class Main {

	private static HashMap<String, File> inputFiles = new HashMap<String, File>();
	public static File outputFolder;
	public static File jarFile;
	public boolean validInput;

	public static void main(String[] args) {

		if (args.length < 2) {
			printHelp();
		}

		processArgs(args);
		if (outputFolder.isFile()) {
			return;
		}

		Set<String> keys = inputFiles.keySet();

		extractJar();
		jarFile.deleteOnExit();
		for (String x : keys) {
			HTMLConvert hc = new HTMLConvert(x, inputFiles.get(x));
			hc.run();
		}
	}

	private static void processArgs(String[] args) {
		File outTemp = new File(args[args.length - 1]);
		setOutput(outTemp);

		for (int i = 0; i < args.length - 1; i++) {
			addInput(args[i]);
		}
	}

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

	private static void printHelp() {
		System.out.println("To execute this jar please follow following:");
		System.out.println("Run the jar with a list of input files separated by a space followed by an output folder");
		System.out.println(
				"Example: java -jar PDFExtractPrototype.jar example.pdf example1.pdf example2.pdf outputFolder");
		return;
	}

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
