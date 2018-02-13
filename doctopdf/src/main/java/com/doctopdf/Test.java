package com.doctopdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class Test {

	public static void main(String[] args) throws Exception {
		readDocFile("C:\\Users\\HP LAPTOP\\Desktop\\testing\\AOPT_Payment_Plan_Addendum_12-10-2017.doc");

	}

	public static String readDocFile(String fileName) {
		String output = "";
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			HWPFDocument doc = new HWPFDocument(fis);
			WordExtractor we = new WordExtractor(doc);
			String[] paragraphs = we.getParagraphText();
			for (String para : paragraphs) {
				output = output + "\n" + para.toString() + "\n";
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	public static void convertWordToPdf(String src, String desc) {
		try {
			// create file inputstream object to read data from file
			FileInputStream fs = new FileInputStream(src);
			// create document object to wrap the file inputstream object
			XWPFDocument doc = new XWPFDocument(fs);
			// 72 units=1 inch
			Document pdfdoc = new Document(PageSize.A4, 72, 72, 72, 72);
			// create a pdf writer object to write text to mypdf.pdf file
			PdfWriter pwriter = PdfWriter.getInstance(pdfdoc,
					new FileOutputStream(desc));
			// specify the vertical space between the lines of text
			pwriter.setInitialLeading(20);
			// get all paragraphs from word docx
			List plist = doc.getParagraphs();

			// open pdf document for writing
			pdfdoc.open();
			for (int i = 0; i < plist.size(); i++) {
				// read through the list of paragraphs
				XWPFParagraph pa = (XWPFParagraph) plist.get(i);
				// get all run objects from each paragraph
				List runs = pa.getRuns();
				// read through the run objects
				for (int j = 0; j < runs.size(); j++) {
					XWPFRun run = (XWPFRun) runs.get(j);
					// get pictures from the run and add them to the pdf
					// document
					List piclist = run.getEmbeddedPictures();
					// traverse through the list and write each image to a file
					java.util.Iterator iterator = piclist.iterator();
					while (iterator.hasNext()) {
						XWPFPicture pic = (XWPFPicture) iterator.next();
						XWPFPictureData picdata = pic.getPictureData();
						byte[] bytepic = picdata.getData();
						Image imag = Image.getInstance(bytepic);
						pdfdoc.add(imag);

					}
					// get color code
					int color = getCode(run.getColor());
					// construct font object
					Font f = null;
					if (run.isBold() && run.isItalic())
						f = FontFactory.getFont(FontFactory.TIMES_ROMAN,
								run.getFontSize(), Font.BOLDITALIC,
								new BaseColor(color));
					else if (run.isBold())
						f = FontFactory
								.getFont(FontFactory.TIMES_ROMAN,
										run.getFontSize(), Font.BOLD,
										new BaseColor(color));
					else if (run.isItalic())
						f = FontFactory.getFont(FontFactory.TIMES_ROMAN, run
								.getFontSize(), Font.ITALIC, new BaseColor(
								color));
					else if (run.isStrike())
						f = FontFactory.getFont(FontFactory.TIMES_ROMAN,
								run.getFontSize(), Font.STRIKETHRU,
								new BaseColor(color));
					else
						f = FontFactory.getFont(FontFactory.TIMES_ROMAN, run
								.getFontSize(), Font.NORMAL, new BaseColor(
								color));
					// construct unicode string
					String text = run.getText(-1);
					byte[] bs;
					if (text != null) {
						bs = text.getBytes();
						String str = new String(bs, "UTF-8");
						// add string to the pdf document
						Chunk chObj1 = new Chunk(str, f);
						pdfdoc.add(chObj1);
					}

				}
				// output new line
				pdfdoc.add(new Chunk(Chunk.NEWLINE));
			}
			// close pdf document
			pdfdoc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getCode(String code) {
		int colorCode;
		if (code != null)
			colorCode = Long.decode("0x" + code).intValue();
		else
			colorCode = Long.decode("0x000000").intValue();
		return colorCode;
	}

}
