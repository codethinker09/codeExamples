package com.nlp;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetection {

	public static void main(String args[]) {

		String paragraph = " Hi. How are you? Welcome to Github. We provide source for various technologies";

		sentenceJava(paragraph);

		System.out.println("===============================================");
		System.out.println("NLP O/P follows");
		System.out.println("===============================================");

		try {
			sentenceNlp(paragraph);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void sentenceJava(String paragraph) {
		String simple = "[.?!]";
		String[] splitString = (paragraph.split(simple));
		for (String string : splitString)
			System.out.println(string);
	}

	private static void sentenceNlp(String sentence) throws IOException {
		InputStream inputStream = ClassLoader.class.getResourceAsStream("/en-sent.bin");

		// /Loading the model
		SentenceModel model = new SentenceModel(inputStream);

		// Instantiating the SentenceDetectorME class
		SentenceDetectorME detector = new SentenceDetectorME(model);

		// Detecting the sentence
		String sentences[] = detector.sentDetect(sentence);

		// Printing the sentences
		for (String sent : sentences)
			System.out.println(sent);

	}

}
