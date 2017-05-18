package com.ankur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class RWQRCodeToText {

	public static void main(String[] args) throws WriterException, IOException,
			NotFoundException {
		// String qrCodeData = "Hello World!";
		String filePath = "QRCode.png";
		String charset = "UTF-8"; // or "ISO-8859-1"

		String inputFileBinary = "my.bin";
		String outputFile = "my1.bin";

		// create binary
		createBinaryFile(inputFileBinary);

		Map hintMap = new HashMap();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		createQRCode(inputFileBinary, filePath, charset, hintMap, 200, 200);
		System.out.println("QR Code image created successfully!");

		System.out.println("Data read from QR Code: "
				+ readQRCode(filePath, charset, hintMap));

		createBinaryFileBack(readQRCode(filePath, charset, hintMap), outputFile);

	}

	private static void createBinaryFileBack(String readQRCode,
			String outputFile) throws IOException {
		OutputStream outputStream = new FileOutputStream(outputFile);
		outputStream.write(readQRCode.getBytes());
	}

	public static void createBinaryFile(String outputFile) throws IOException {
		OutputStream outputStream = new FileOutputStream(outputFile);
		outputStream.write("Hello App".getBytes());
	}

	public static void createQRCode(String binaryFile, String filePath,
			String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
			throws WriterException, IOException {

		StringBuilder inputText = new StringBuilder();
		InputStream inputStream = new FileInputStream(binaryFile);

		FileReader fileReader = new FileReader(binaryFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			inputText.append(line);
		}
		fileReader.close();

		BitMatrix matrix = new MultiFormatWriter().encode(new String(inputText
				.toString().getBytes(charset), charset), BarcodeFormat.QR_CODE,
				qrCodewidth, qrCodeheight, hintMap);
		MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
				.lastIndexOf('.') + 1), new File(filePath));
	}

	public static void createQRCode1(String qrCodeData, String filePath,
			String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
			throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(
				new String(qrCodeData.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
		MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
				.lastIndexOf('.') + 1), new File(filePath));
	}

	public static String readQRCode(String filePath, String charset, Map hintMap)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
				new BufferedImageLuminanceSource(
						ImageIO.read(new FileInputStream(filePath)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
				hintMap);
		return qrCodeResult.getText();
	}
}
