package com.doctopdf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.converter.ConverterRegistry;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.XDocConverterException;

public class DocxToPDFConverter {

	public static void DocxToPDFConvert(InputStream inStream, OutputStream outStream)
			throws IOException, XDocConverterException {

		Options options = Options.getTo(ConverterTypeTo.PDF);
		IConverter converter = ConverterRegistry.getRegistry().getConverter(options);

		converter.convert(inStream, outStream, options);
	}
}
