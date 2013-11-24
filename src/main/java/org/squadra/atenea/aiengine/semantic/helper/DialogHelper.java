package org.squadra.atenea.aiengine.semantic.helper;

import java.util.ArrayList;
import java.util.Iterator;

import org.squadra.atenea.aiengine.responses.ResponseType;
import org.squadra.atenea.aiengine.semantic.UserMessageType;
import org.squadra.atenea.base.StringUtil;
import org.squadra.atenea.base.word.Word;
import org.squadra.atenea.data.server.Neo4jServer;
import org.squadra.atenea.parser.model.Sentence;
import org.squadra.atenea.parser.model.SimpleSentence;

public class DialogHelper {

	/**
	 * Indica si la oracion es un dialogo.
	 * 
	 * @param sentence
	 * @return Si es un dialogo, devuelve el tipo de dialogo. Sino devuelve tipo
	 *         desconocido (que no es dialogo).
	 */
	public static String isDialog(Sentence sentence) {

		String messageType = UserMessageType.UNKNOWN;

		SimpleSentence inputSentence = sentence.toSimpleSentence(false);
		ArrayList<SimpleSentence> outputSentences;

		// Verifico que la cache de dialogos este cargada
		if (Neo4jServer.dialogCache != null) {
			outputSentences = Neo4jServer.dialogCache;
		} else {
			return messageType;
		}

		// Si hay una palabra que sea Atenea en medio del saludo, la elimino
		Iterator<Word> it = inputSentence.getWords().iterator();
		while (it.hasNext()) {
			Word word = it.next();

			if (word.getName().matches("Atenea|atenea")) {
				it.remove();
			}
			/*
			 * else if (word.getType() == WordTypes.Type.PROPER_NAME) {
			 * word.setName("%nombre%"); }
			 */
		}

		String inputSentenceStr = inputSentence.toString().toLowerCase();

		String responseType = "";
		Boolean flagContain = false;

		// Verifico si la oracion de entrada es un tipo de dialogo conocido por Atenea

		int i = 0;
		while (!flagContain && i < outputSentences.size()) {

			String outputSentenceStr = outputSentences.get(i).toString().toLowerCase();

			if (StringUtil.replaceAccents(
						removeUnnecessaryChars(outputSentenceStr)).contains(
								StringUtil.replaceAccents(
										removeUnnecessaryChars(inputSentenceStr)))) {
				flagContain = true;
				responseType = outputSentences.get(i).getWords().get(0)
						.getName();
			}

			i++;
		}

		switch (responseType) {
		case ResponseType.Dialog.SALUDO:
			messageType = UserMessageType.Dialog.SALUDO;
			break;

		case ResponseType.Dialog.DESPEDIDA:
			messageType = UserMessageType.Dialog.DESPEDIDA;
			break;

		case ResponseType.Dialog.PREGUNTA_ESTADO_ANIMO:
			messageType = UserMessageType.Dialog.PREGUNTA_ESTADO_ANIMO;
			break;

		case ResponseType.Dialog.PREGUNTA_NOMBRE:
			messageType = UserMessageType.Dialog.PREGUNTA_NOMBRE;
			break;

		case ResponseType.Dialog.PREGUNTA_EDAD:
			messageType = UserMessageType.Dialog.PREGUNTA_EDAD;
			break;

		case ResponseType.Dialog.RESPUESTA_NOMBRE_ATENEA:
			messageType = UserMessageType.Dialog.RESPUESTA_NOMBRE_USUARIO;
			break;

		default:
			messageType = UserMessageType.UNKNOWN;
			break;
		}

		return messageType;
	}
	
	
	private static String removeUnnecessaryChars(String sentence) {
		return sentence.replaceAll("[\\.,;:\\-\\«\\»\\'’`¡¿?!\\\"]", "");
	}
	
}
