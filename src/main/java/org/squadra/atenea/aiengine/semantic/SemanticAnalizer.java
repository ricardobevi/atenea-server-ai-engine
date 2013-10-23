package org.squadra.atenea.aiengine.semantic;

import lombok.extern.log4j.Log4j;

import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.parser.model.Sentence;

@Log4j
public class SemanticAnalizer {

	public static String execute(Message message, Sentence sentence) {
		
		log.debug("Ejecutando analizador semantico...");
		
		// TODO: validacion de significado (busqueda probablilistica en la BD)
		
		return SentenceClassifier.classify(message, sentence);
	
	}
}
