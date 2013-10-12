package org.squadra.atenea.aiengine.semantic;

import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.parser.model.Sentence;

public class SemanticAnalizer {

	public static String execute(Message message, Sentence sentence) {
		
		// TODO: validacion de significado (busqueda probablilistica en la BD)
		
		return SentenceClassifier.classify(message, sentence);
	
	}
}
