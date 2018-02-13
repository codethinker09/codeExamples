package com.doctopdf;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.docx4j.convert.in.Doc;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class DocToPDFConverter {

	public static void DocToPDFConvert(InputStream inStream, OutputStream outStream) throws Exception {

		InputStream iStream = inStream;
		WordprocessingMLPackage wordMLPackage = getMLPackage(iStream);
		Docx4J.toPDF(wordMLPackage, outStream);

	}

	private static WordprocessingMLPackage getMLPackage(InputStream iStream) throws Exception {
		PrintStream originalStdout = System.out;

		// Disable stdout temporarily as Doc convert produces alot of output
		System.setOut(new PrintStream(new OutputStream() {
			public void write(int b) {
				// DO NOTHING
			}
		}));

		WordprocessingMLPackage mlPackage = Doc.convert(iStream);

		System.setOut(originalStdout);
		return mlPackage;
	}

}
