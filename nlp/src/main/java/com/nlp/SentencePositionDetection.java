package com.nlp;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Span;

public class SentencePositionDetection {

	public static void main(String args[]) throws Exception {

		String paragraph = " Hi. How are you? Welcome to Github. We provide source for various technologies";

		// Loading sentence detector model
		InputStream inputStream = ClassLoader.class.getResourceAsStream("/en-sent.bin");
		SentenceModel model = new SentenceModel(inputStream);

		// Instantiating the SentenceDetectorME class
		SentenceDetectorME detector = new SentenceDetectorME(model);

		// Detecting the sentence
		String sentences[] = detector.sentDetect(paragraph);

		// Detecting the position of the sentences in the raw text
		Span spans[] = detector.sentPosDetect(paragraph);

		// Printing the spans of the sentences in the paragraph
		for (int i = 0; i < spans.length; i++) {
			System.out.println("Sentence ------- ");
			System.out.println(sentences[i]);
			System.out.println(spans[i]);
		}
	}

}
