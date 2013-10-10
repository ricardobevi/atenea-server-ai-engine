package org.squadra.atenea.aiengine.responses;

import org.squadra.atenea.parser.model.Sentence;

/**
 * Algoritmo encargado de ejecutar los diferentes buscadores de respuestas
 * segun el tipo de oracion de entrada.
 * @author Leandro Morrone
 *
 */
public class ResponseSearcher {

	public static String execute(Sentence.Type sentenceType, String inputMessageType) {
		
		String response = "";
		
		switch (sentenceType) {
		
			case DIALOG:
				response = DialogResponseSearcher.getRandomResponse(inputMessageType);
				break;
				
			default:
				response = "Disculpa, no logro entenderte.";
				break;
		}
		
		return response;
	}
}
