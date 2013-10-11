package org.squadra.atenea.aiengine.responses;

import java.util.ArrayList;

import org.squadra.atenea.aiengine.semantic.UserMessageType;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.base.word.Word;
import org.squadra.atenea.base.word.WordTypes;
import org.squadra.atenea.data.query.DialogQuery;

public class DialogResponseSearcher {

	/**
	 * Selecciona una respuesta aleatoria segun el tipo de mensaje.
	 * @param inputMessageType Tipo de mensaje de entrada del usuario.
	 * @return Respuesta final que se devolverá al usuario.
	 */
	public static String getRandomResponse(Message message, String inputMessageType) {
		
		// TODO: tomar estos datos del cliente
		String userName = "Leandro";
		String systemName = "Atenea";
		String systemAge = "4 meses";
		
		String finalResponse = "";
		Integer randomInt1 = (int) Math.round(Math.random() * 100);
		Integer randomInt2 = (int) Math.round(Math.random() * 100);
		Integer randomInt3 = (int) Math.round(Math.random() * 100);
		
		switch (inputMessageType) {
			
			// Si el usuario saluda a Atenea
			case UserMessageType.Dialog.SALUDO:
				
				finalResponse += getResponseByType(ResponseType.Dialog.SALUDO);
				
				if (randomInt1 <= 60) {            // Saludo + nombre del usuario
					
					if (userName.equals("?")) {    // Si no sabe el nombre lo pregunta
						finalResponse += " " +
								getResponseByType(ResponseType.Dialog.PREGUNTA_NOMBRE);
						break;
					} else {
						finalResponse = 
								finalResponse.substring(0, finalResponse.length() - 1) +
								" " + userName + ".";
					}
				}
				
				if (randomInt2 <= 80) {
					
					if (randomInt2 <= 20) {        // Saludo + interjeccion
						finalResponse += " " +
								getResponseByType(ResponseType.Dialog.SALUDO_INTERJECCION);
					}
					else if (randomInt2 <= 50) {    // Saludo + como estas
						finalResponse += " " +
								getResponseByType(ResponseType.Dialog.PREGUNTA_ESTADO_ANIMO);
					}
					else if (randomInt2 <= 80) {    // Saludo + que necesitas
						finalResponse += " " +
								getResponseByType(ResponseType.Dialog.PREGUNTA_NECESIDAD);
					}
				}
				break;
			
			// Si el usuario le pregunta a Atenea como esta
			case UserMessageType.Dialog.PREGUNTA_ESTADO_ANIMO:
			case UserMessageType.Dialog.SALUDO_PREGUNTA_ESTADO_ANIMO:
				
				// Si saluda, agrego un saludo al comienzo
				if (inputMessageType.equals(UserMessageType.Dialog.SALUDO_PREGUNTA_ESTADO_ANIMO)
						&& randomInt3 <= 50) {
					finalResponse += getResponseByType(ResponseType.Dialog.SALUDO) + " ";
				}
				
				if (randomInt1 <= 25) {
					if (randomInt1 <= 10) {
						finalResponse += "Estoy ";
					} else if (randomInt1 <= 20) {
						finalResponse += "Me siento ";
					} else if (randomInt1 <= 25) {
						finalResponse += "Me encuentro ";
					}
				}
				
				finalResponse += getResponseByType(ResponseType.Dialog.RESPUESTA_ESTADO_ANIMO);
				
				if (randomInt2 <= 80) {
					if (randomInt2 <= 20) {         // Estado de animo + y vos?
						finalResponse += " Y vos?";
					}
					else if (randomInt2 <= 30) {    // Estado de animo + como estas?
						finalResponse += " " +
								getResponseByType(ResponseType.Dialog.PREGUNTA_ESTADO_ANIMO);
					}
					else if (randomInt2 <= 80) {    // Estado de animo + que necesitas?
						finalResponse += " " +
								getResponseByType(ResponseType.Dialog.PREGUNTA_NECESIDAD);
					}
				}
				break;
			
			// Si el usuario le pregunta a Atenea como se llama
			case UserMessageType.Dialog.PREGUNTA_NOMBRE:
				
				finalResponse += 
						getResponseByType(ResponseType.Dialog.RESPUESTA_NOMBRE_ATENEA);
				
				if (userName.equals("?")) {    // Si no sabe el nombre le re-pregunta
					finalResponse += " Y vos?";
				}
				break;
				
			// Si el usuario le pregunta a Atenea la edad
			case UserMessageType.Dialog.PREGUNTA_EDAD:
				
				finalResponse += 
						getResponseByType(ResponseType.Dialog.RESPUESTA_EDAD_ATENEA);
				break;
				
			// Si el usuario le pregunta a Atenea que esta haciendo
			case UserMessageType.Dialog.PREGUNTA_QUE_HACES:
				
				finalResponse += 
						getResponseByType(ResponseType.Dialog.RESPUESTA_A_QUE_HACES);
				break;
				
			// Si el usuario le pregunta a Atenea que esta haciendo
			case UserMessageType.Dialog.DESPEDIDA:
				
				finalResponse += 
						getResponseByType(ResponseType.Dialog.DESPEDIDA);
				
				if (!userName.equals("?") && randomInt1 <= 30) { // Despedida + nombre del usuario
					finalResponse = 
							finalResponse.substring(0, finalResponse.length() - 1) +
							" " + userName + ".";
				}
				
				break;
			
			// No deberia pasar nunca por aca
			default:
				finalResponse = "Disculpa, no entendí lo que me dijiste.";
				break;
		}
		
		finalResponse = finalResponse
				.replace("%nombre%", systemName)
				.replace("%edad%", systemAge);
		
		return finalResponse;
	}
	
	
	/**
	 * Obtiene de la base de datos una respuesta aleatoria segun el tipo buscado.
	 * @param type Tipo de respuesta que busca Atenea
	 * @return Respuesta
	 */
	public static String getResponseByType(String type) {
		
		DialogQuery dq = new DialogQuery();
		ArrayList<Word> response = dq.findRandomSentenceByDialogType("dialogType", type);
		
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
