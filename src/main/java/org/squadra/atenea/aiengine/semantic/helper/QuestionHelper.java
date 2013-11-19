package org.squadra.atenea.aiengine.semantic.helper;

import org.squadra.atenea.aiengine.semantic.UserMessageType;
import org.squadra.atenea.aiengine.semantic.UserMessageType.Question;
import org.squadra.atenea.parser.model.Sentence;

public class QuestionHelper {

	/**
	 * Indica si la oracion es una pregunta.
	 * 
	 * @param sentence
	 * @return Si es un dialogo, devuelve el tipo de dialogo. Sino devuelve tipo
	 *         desconocido (que no es dialogo).
	 */
	public static String isQuestion(Sentence sentence) {

		String messageType = UserMessageType.UNKNOWN;
		String questionWord;

		// Obtengo el adverbio interrigativo de la oracion
		try {
			questionWord = sentence.getQuestionWords().get(0).getName();
		} catch (Exception e) {
			// Si la oracion no contiene un interrogativo, no es una pregunta
			return messageType;
		}

		// Calculo el tipo de pregunta de realizada

		if (questionWord.toLowerCase().matches("dónde")) {
			messageType = UserMessageType.Question.DONDE;
		} else if (questionWord.toLowerCase().matches("cuándo")) {
			messageType = UserMessageType.Question.CUANDO;
		} else if (questionWord.toLowerCase().matches("quién")) {
			messageType = UserMessageType.Question.QUIEN;
		} else if (questionWord.toLowerCase().matches("qué")) {
			messageType = UserMessageType.Question.QUE;
		}

		return messageType;
	}
	
}
