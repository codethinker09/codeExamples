package com.nlp;

import java.io.InputStream;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.parser.chunking.Parser;

public class ParserExample {

	public static void main(String args[]) throws Exception {
		// Loading parser model
		InputStream inputStream = ClassLoader.class.getResourceAsStream("/en-parser-chunking.bin");
		ParserModel model = new ParserModel(inputStream);

		// Creating a parser
		Parser parser = (Parser) ParserFactory.create(model);

		// Parsing the sentence
		String sentence = "Github is the largest Source library.";
		Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);

		for (Parse p : topParses)
			p.show();
	}

}
