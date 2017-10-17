package com.doctopdf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class DocxToPDFConverter {

	public static void DocxToPDFConvert(InputStream inStream, OutputStream outStream) throws IOException {

		XWPFDocument document = new XWPFDocument(inStream);
		PdfOptions options = PdfOptions.create();
		PdfConverter.getInstance().convert(document, outStream, options);
	}
}
