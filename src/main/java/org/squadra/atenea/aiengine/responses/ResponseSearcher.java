package org.squadra.atenea.aiengine.responses;

import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.parser.model.Sentence;

/**
 * Algoritmo encargado de ejecutar los diferentes buscadores de respuestas
 * segun el tipo de oracion de entrada.
 * @author Leandro Morrone
 *
 */
public class ResponseSearcher {

	public static void execute(Sentence.Type sentenceType, String inputMessageType, Message message) {
		
		String responseText;
		int responseType;
		
		switch (sentenceType) {
		
			case DIALOG:
				responseText = DialogResponseSearcher.getRandomResponse(inputMessageType);
				responseType = Message.DIALOG;
				break;
			
			case ORDER:
				responseText = OrderResponseSearcher.getRandomResponse(inputMessageType);
				responseType = Message.ORDER;
				break;
				
			default:
				responseText = "Disculpa, no logro entenderte.";
				responseType = Message.UNKNOWN;
				break;
		}
		
		message.setText(responseText);
		message.setType(responseType);
	}
}
