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

		// Obtengo sustantivos, verbos y nombres propios
		
		ArrayList<Word> nounsWords = sentence.getNonProperNouns();
		ArrayList<String> nounsStrings = new ArrayList<>();
		
		ArrayList<Word> verbsWords = sentence.getMainVerbs();
		ArrayList<String> verbsStrings = new ArrayList<>();
		
		ArrayList<Word> properNounsWords = sentence.getProperNouns();
		ArrayList<String> properNounsString = new ArrayList<>();
		
		// Convierto los sustantivos, verbos y nombres en strings con su palabra base
		
		for (Word noun : nounsWords) {
			System.out.println("Sustantivo: " + noun.getBaseWord());
			nounsStrings.add(noun.getBaseWord());
		}
		for (Word verb : verbsWords) {
			System.out.println("Verbo: " + verb.getBaseWord());
			verbsStrings.add(verb.getBaseWord());
		}
		for (Word name : properNounsWords) {
			System.out.println("Nombres: " + name.getBaseWord());
			properNounsString.add(name.getBaseWord());
		}
		
		// Seteo los valores iniciales
		
		String answer = "";
		message.setType(Message.QUESTION);
		
		// Armo la respuesta segun el tipo de pregunta ingresada
		
		switch (inputMessageType) {
			
			case UserMessageType.Question.QUIEN:
				
				if (!properNounsString.isEmpty() && !nounsStrings.isEmpty()) {
					answer = getAnswerFromAdditionalInfo(
							properNounsString.get(0), nounsStrings.get(0), "nombre");
				}
				if (answer.equals("")) {
					// Algoritmo de Ricky
				}
				break;
			
			case UserMessageType.Question.CUANDO:
				
				if (!properNounsString.isEmpty() && !nounsStrings.isEmpty()) {
					answer = getAnswerFromAdditionalInfo(
							properNounsString.get(0), nounsStrings.get(0), "fecha");
				}
				else if (!properNounsString.isEmpty() && !verbsStrings.isEmpty()) {
					answer = getAnswerFromAdditionalInfo(
							properNounsString.get(0), verbsStrings.get(0), "fecha");
				}
				if (answer.equals("")) {
					// Algoritmo de Ricky
				}
				break;
				
			case UserMessageType.Question.DONDE:
				
				if (!properNounsString.isEmpty() && !nounsStrings.isEmpty()) {
					answer = getAnswerFromAdditionalInfo(
							properNounsString.get(0), nounsStrings.get(0), "lugar");
				}
				else if (!properNounsString.isEmpty() && !verbsStrings.isEmpty()) {
					answer = getAnswerFromAdditionalInfo(
							properNounsString.get(0), verbsStrings.get(0), "lugar");
				}
				if (answer.equals("")) {
					// Algoritmo de Ricky
				}
				break;
				
			case UserMessageType.Question.QUE:
			case UserMessageType.Question.CUAL:
				
				if (!properNounsString.isEmpty() && !nounsStrings.isEmpty()) {
					answer = getAnswerFromAdditionalInfo(
							properNounsString.get(0), nounsStrings.get(0), "sustantivo");
				}
				else if (!properNounsString.isEmpty() && !verbsStrings.isEmpty()) {
					answer = getAnswerFromAdditionalInfo(
							properNounsString.get(0), verbsStrings.get(0), "sustantivo");
				}
				if (answer.equals("")) {
					// Algoritmo de Ricky
				}
				break;
				
			case UserMessageType.Question.CUANTO:
				
				if (!properNounsString.isEmpty() && !nounsStrings.isEmpty()) {
					answer = getAnswerFromAdditionalInfo(
							properNounsString.get(0), nounsStrings.get(0), "cantidad");
				}
				else if (!properNounsString.isEmpty() && !verbsStrings.isEmpty()) {
					answer = getAnswerFromAdditionalInfo(
							properNounsString.get(0), verbsStrings.get(0), "cantidad");
				}
				if (answer.equals("")) {
					// Algoritmo de Ricky
				}
				break;
				
			default:
				answer = getGoogleAnswer(message, sentence);
				break;
		}

		if (answer.equals("")) {
			answer = getGoogleAnswer(message, sentence);
		}
		
		return answer;
		
	}
	
	
	
	/**
	 * Obtiene una respuesta de la base de datos que contenga las palabras ingresadas.
	 * @param words Lista de palabras a buscar en la respuesta.
	 * @return Respuesta
	 */
	private static String getAnswerByKeyWords(ArrayList<String> words) {
		
		QuestionQuery qq = new QuestionQuery();
		
		ArrayList<SimpleSentence> answers = qq.findSentencesByKeyWords(words);
		
		for (SimpleSentence answer : answers) {
			System.out.println(answer);
		}
		
		SimpleSentence response = answers.get(0);
		
		return response.toString();
	}
	
	
	/**
	 * Obtiene una respuesta de la base de datos por titulo y subtitulo.
	 * @param title Titulo del articulo de Wikipedia.
	 * @param subtitle Subtitulo del cuadrito de la Wikipedia
	 * @return Respuesta
	 */
	private static String getAnswerFromAdditionalInfo(String title, String subtitle, String contentType) {
		
		QuestionQuery qq = new QuestionQuery();
		
		return qq.findSentencesFromAdditionalInfo(title, subtitle, contentType);
	}
	
	
	/**
	 * Obtiene una respuesta aleatoria de que no conoce la respuesta y setea los
	 * parametros necesarios para ejecutar una busqueda en Google.
	 * @param message
	 * @param sentence
	 * @return Respuesta
	 */
	private static String getGoogleAnswer(Message message, Sentence sentence) {
		
		//TODO: cambiar segun facu
		message.setOrder("buscar google " + sentence.toSimpleSentence(true));
		message.setType(Message.PRELOAD_ACTION_WITH_PARAM);
		
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
