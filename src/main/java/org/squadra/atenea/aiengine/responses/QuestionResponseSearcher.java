package org.squadra.atenea.aiengine.responses;

import java.util.ArrayList;

import org.squadra.atenea.aiengine.semantic.UserMessageType;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.base.word.Word;
import org.squadra.atenea.data.query.QuestionQuery;
import org.squadra.atenea.parser.Parser;
import org.squadra.atenea.parser.model.Sentence;
import org.squadra.atenea.parser.model.SimpleSentence;

public class QuestionResponseSearcher {

	public static String getResponse(Message message, String inputMessageType,
			Sentence sentence) {

		ArrayList<Word> nounsWords = sentence.getNouns();
		ArrayList<String> nounsStrings = new ArrayList<>();
		
		ArrayList<Word> verbsWords = sentence.getMainVerbs();
		ArrayList<String> verbsStrings = new ArrayList<>();
		
		for (Word noun : nounsWords) {
			System.out.println("Sustantivo: " + noun.getName());
			nounsStrings.add(noun.getName());
		}
		
		for (Word verb : verbsWords) {
			System.out.println("Verbo: " + verb.getName());
			verbsStrings.add(verb.getName());
		}
		
		String answer = "No tengo esa informacion.";
		
		// Armo la respuesta segun el tipo de pregunta ingresada
		
		message.setType(Message.QUESTION);
		
		switch (inputMessageType) {
		
			case UserMessageType.Question.QUIEN:
				
				try {
					answer = getAnswerByWords(nounsStrings);
					Sentence parsedAnswer = new Parser().parse(answer);
					
					answer = parsedAnswer.getSubjects().get(0).getProperNouns().get(0).getName();
				}
				catch (Exception e) {
					answer = getGoogleAnswer(message, sentence);
				}
				break;
			
			case UserMessageType.Question.CUANDO:
			case UserMessageType.Question.DONDE:
				
				try {
					nounsStrings.addAll(verbsStrings);
					answer = getAnswerByWords(nounsStrings);
					Sentence parsedAnswer = new Parser().parse(answer);
					
					answer = parsedAnswer.toSimpleSentence(true).toString();
				}
				catch (Exception e) {
					answer = getGoogleAnswer(message, sentence);
				}
				break;
				
			default:
				answer = getGoogleAnswer(message, sentence);
				break;
		}

		return answer;
		
	}
	
	
	/**
	 * Obtiene de la base de datos una respuesta aleatoria segun el tipo buscado.
	 * @param type Tipo de respuesta que busca Atenea
	 * @return Respuesta
	 */
	public static String getAnswerByWords(ArrayList<String> words) {
		
		QuestionQuery qq = new QuestionQuery();
		
		ArrayList<SimpleSentence> answers = qq.findAnswers(words);
		
		for (SimpleSentence answer : answers) {
			System.out.println(answer);
		}
		
		SimpleSentence response = answers.get(0);
		
		return response.toString();
	}
	
	
	/**
	 * Obtiene una respuesta aleatoria de que no conoce la respuesta y setea los
	 * parametros necesarios para ejecutar una busqueda en Google.
	 * @param message
	 * @param sentence
	 * @return
	 */
	public static String getGoogleAnswer(Message message, Sentence sentence) {
		
		//TODO: cambiar segun facu
		message.setOrder("buscar en google " + sentence.toSimpleSentence(true));
		message.setType(Message.PRELOAD_ACTION);
		
		Integer randomInt1 = (int) Math.round(Math.random() * 100);
		
		String googleAnswer = "";
		
		if (randomInt1 < 70) {
			googleAnswer += 
					AssertionAndDefaultSearcher.getResponseByType(ResponseType.Default.FALTA_INFORMACION)
					.replace(".", ",") + " ";
		}
		
		googleAnswer += 
				AssertionAndDefaultSearcher.getResponseByType(ResponseType.Default.BUSQUEDA_EN_GOOGLE);
		
		return googleAnswer;
	}

}
