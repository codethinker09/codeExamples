package com.nlp;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetectionProbability {

	public static void main(String[] args) throws IOException {
		String paragraph = " Hi. How are you? Welcome to Github. We provide source for various technologies";

		// Loading sentence detector model
		InputStream inputStream = ClassLoader.class.getResourceAsStream("/en-sent.bin");
		SentenceModel model = new SentenceModel(inputStream);

		// Instantiating the SentenceDetectorME class
		SentenceDetectorME detector = new SentenceDetectorME(model);

		// Detecting the sentence
		String sentences[] = detector.sentDetect(paragraph);

		// Getting the probabilities of the last decoded sequence
		double[] probs = detector.getSentenceProbabilities();

		System.out.println("  ");

		for (int i = 0; i < probs.length; i++) {
			System.out.println("Sentence ------- ");
			System.out.println(sentences[i]);
			System.out.println(probs[i]);
		}

	}

}
