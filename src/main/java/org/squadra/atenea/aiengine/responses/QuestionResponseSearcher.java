package org.squadra.atenea.aiengine.responses;

import java.util.ArrayList;
import java.util.Arrays;

import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.data.query.QuestionQuery;
import org.squadra.atenea.parser.model.Sentence;

public class QuestionResponseSearcher {

	public static String getResponse(Message message, String inputMessageType,
			Sentence sentence) {

		// TODO: Segun el inputMessageType (donde, quien, cuando, etc) buscar diferentes cosas
		
		ArrayList<String> words = new ArrayList<>(
				Arrays.asList(sentence.toSimpleSentence(true).toString().split(" "))
				);
		
		words.remove(0);
		
		return getAnswerByWords(words);
		
	}
	
	
	
	/**
	 * Obtiene de la base de datos una respuesta aleatoria segun el tipo buscado.
	 * @param type Tipo de respuesta que busca Atenea
	 * @return Respuesta
	 */
	public static String getAnswerByWords(ArrayList<String> words) {
		
		QuestionQuery qq = new QuestionQuery();
		String response = qq.findAnswer(words);
		
		return response.toString();
	}

}
