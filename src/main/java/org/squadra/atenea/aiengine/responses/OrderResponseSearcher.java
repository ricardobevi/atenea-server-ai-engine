package org.squadra.atenea.aiengine.responses;

import java.util.ArrayList;

import org.squadra.atenea.aiengine.semantic.UserMessageType;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.base.word.Word;
import org.squadra.atenea.base.word.WordTypes;
import org.squadra.atenea.data.query.DialogQuery;

public class OrderResponseSearcher {

	/**
	 * Selecciona de la base de datos una respuesta aleatoria a una orden.
	 * @param inputMessageType Tipo de mensaje de entrada del usuario.
	 * @return Respuesta final que se devolverá al usuario.
	 */
	public static String getRandomResponse(Message message, String inputMessageType) {
		
		String finalResponse = "";
		//Integer randomInt1 = (int) Math.round(Math.random() * 100);
		
		switch (inputMessageType) {
		
			// Si el usuario solicita una accion conocida
			case UserMessageType.Order.ORDEN_CONOCIDA:
				
				finalResponse += 
						getResponseByType(ResponseType.Order.ORDEN_CONOCIDA);
				
				// TODO: sacar esto cuando llevemos las acciones al servidor
				message.addMetadata("orden_desconocida",
						getResponseByType(ResponseType.Order.ORDEN_DESCONOCIDA)
						.replace("%order%", message.getOrder()));
				break;
				
			// Si el usuario solicita una accion desconocida
			case UserMessageType.Order.ORDEN_DESCONOCIDA:
				
				finalResponse += 
						getResponseByType(ResponseType.Order.ORDEN_DESCONOCIDA)
						.replace(",", ".");
				break;
				
			// No deberia pasar nunca por aca
			default:
				finalResponse = "Disculpa, no entendí lo que me dijiste.";
				break;
		}
		
		finalResponse = finalResponse
				.replace("%order%", message.getOrder());
		
		return finalResponse;
	}
	
	
	/**
	 * Obtiene de la base de datos una respuesta aleatoria segun el tipo buscado.
	 * @param type Tipo de respuesta que busca Atenea
	 * @return Respuesta
	 */
	public static String getResponseByType(String type) {
		
		DialogQuery dq = new DialogQuery();
		ArrayList<Word> response = dq.findRandomSentenceByDialogType("orderType", type);
		
		String responseText = "";
		
		for (Word word : response) {
			if (word.getType().equals(WordTypes.Type.PUNCTUATION)) {
				responseText += word.getName();
			} else { 
				responseText += " " + word.getName();
			}
		}
		
		return responseText.trim();
	}
}
