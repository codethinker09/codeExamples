package com.doctopdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class App {
	public static void main(String[] args) throws Exception {

		String inPath = "C:\\Users\\ankur.singhal\\Desktop\\testing\\AOPT_Payment_Plan_Addendum_12-10-2017.doc";
		String lowerCaseInPath = inPath.toLowerCase();

		InputStream inStream = getInFileStream(inPath);
		OutputStream outStream = getOutFileStream(
				"C:\\Users\\ankur.singhal\\Desktop\\testing\\AOPT_Payment_Plan_Addendum_12-10-2017.pdf");

		if (lowerCaseInPath.endsWith("doc")) {
			DocToPDFConverter.DocToPDFConvert(inStream, outStream);
		} else if (lowerCaseInPath.endsWith("docx")) {
			DocxToPDFConverter.DocxToPDFConvert(inStream, outStream);
		}

		inStream.close();
		outStream.close();

	}

	private static InputStream getInFileStream(String inputFilePath) throws FileNotFoundException {
		File inFile = new File(inputFilePath);
		FileInputStream iStream = new FileInputStream(inFile);
		return iStream;
	}

	private static OutputStream getOutFileStream(String outputFilePath) throws IOException {
		File outFile = new File(outputFilePath);

		try {
			// Make all directories up to specified
			outFile.getParentFile().mkdirs();
		} catch (NullPointerException e) {
			// Ignore error since it means not parent directories
		}

		outFile.createNewFile();
		FileOutputStream oStream = new FileOutputStream(outFile);
		return oStream;
	}

}
