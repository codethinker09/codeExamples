package com.nlp;

import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class LocationFinder {
	public static void main(String args[]) throws Exception {

		InputStream inputStreamTokenizer = ClassLoader.class.getResourceAsStream("/en-token.bin");
		TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);

		String paragraph = "Github is located in India";

		// Instantiating the TokenizerME class
		TokenizerME tokenizer = new TokenizerME(tokenModel);
		String tokens[] = tokenizer.tokenize(paragraph);

		// Loading the NER-location moodel
		InputStream inputStreamNameFinder = ClassLoader.class.getResourceAsStream("/en-ner-location.bin");
		TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);

		// Instantiating the NameFinderME class
		NameFinderME nameFinder = new NameFinderME(model);

		// Finding the names of a location
		Span nameSpans[] = nameFinder.find(tokens);
		// Printing the spans of the locations in the sentence
		for (Span s : nameSpans)
			System.out.println(s.toString() + "  " + tokens[s.getStart()]);
	}
}
