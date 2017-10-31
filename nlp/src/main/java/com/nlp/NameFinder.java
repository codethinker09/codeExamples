package com.nlp;

import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

public class NameFinder {

	public static void main(String args[]) throws Exception {
		InputStream inputStream = ClassLoader.class.getResourceAsStream("/en-ner-person.bin");
		TokenNameFinderModel model = new TokenNameFinderModel(inputStream);

		// Instantiating the NameFinder class
		NameFinderME nameFinder = new NameFinderME(model);

		// Getting the sentence in the form of String array
		String paragraph = " Hi. How are you? Welcome to ankur. We provide source for various technologies";
		String[] sentence = new String[] { "Mike", "and", "Singhal", "are", "good", "friends" };

		// Finding the names in the sentence
		//Span nameSpans[] = nameFinder.find(paragraph.replace(".", "").split(" "));
		Span nameSpans[] = nameFinder.find(sentence);

		// Printing the spans of the names in the sentence
		for (Span s : nameSpans)
			System.out.println(s.toString());
	}

}
