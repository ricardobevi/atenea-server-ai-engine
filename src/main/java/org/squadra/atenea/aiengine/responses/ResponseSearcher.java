package org.squadra.atenea.aiengine.responses;

import lombok.extern.log4j.Log4j;

import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.parser.model.Sentence;

/**
 * Algoritmo encargado de ejecutar los diferentes buscadores de respuestas
 * segun el tipo de oracion de entrada.
 * @author Leandro Morrone
 *
 */
@Log4j
public class ResponseSearcher {

	public static void execute(Message message, Sentence sentence, String inputMessageType) {
		
		log.debug("Buscando respuesta...");
		
		String responseText;
		int responseType;
		
		switch (sentence.getType()) {
		
			case DIALOG:
				responseText = DialogResponseSearcher.getRandomResponse(message ,inputMessageType);
				responseType = Message.DIALOG;
				break;
			
			case QUESTION:
				responseText = QuestionResponseSearcher.getResponse(message, inputMessageType, sentence);
				responseType = Message.QUESTION;
				break;
			
			case ORDER:
				responseText = OrderResponseSearcher.getRandomResponse(message, inputMessageType);
				responseType = message.getType();
				break;
				
			default:
				responseText = AssertionAndDefaultSearcher.getResponseByType(ResponseType.Default.AFFIRMATION);
				responseType = Message.UNKNOWN;
				break;
		}
		
		message.setText(responseText);
		message.setType(responseType);
		
		log.debug("Devolviendo respuesta...");
	}
}
