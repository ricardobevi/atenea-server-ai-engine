package org.squadra.atenea.aiengine.morphologic;

import lombok.extern.log4j.Log4j;

@Log4j
public class MorphologicAnalizer {

	public static String execute(String inputSentence) {
		
		log.debug("Ejecutando analizador morfologico...");
		
		String outputSentence = inputSentence.trim();
		
		
		// NO SE PARA QUE MIERDA ES ESTO, CREO QUE NO SIRVE
		outputSentence = outputSentence.replace("Atenea", "atenea");
		
		
		// Pongo acentos a la primera palabra si es una pregunta
		Integer index = outputSentence.indexOf(' ');
		
		if (index > 0) {
			
			String firstWord = outputSentence.substring(0, index);
			
			if (firstWord.toLowerCase().matches("cuando")) {
				outputSentence = outputSentence.replace(firstWord, "Cuándo");
			}
			else if (firstWord.toLowerCase().matches("donde")) {
				outputSentence = outputSentence.replace(firstWord, "Dónde");
			}
			else if (firstWord.toLowerCase().matches("quien")) {
				outputSentence = outputSentence.replace(firstWord, "Quién");
			}
			else if (firstWord.toLowerCase().matches("quienes")) {
				outputSentence = outputSentence.replace(firstWord, "Quiénes");
			}
			else if (firstWord.toLowerCase().matches("cual")) {
				outputSentence = outputSentence.replace(firstWord, "Cuál");
			}
			else if (firstWord.toLowerCase().matches("cuales")) {
				outputSentence = outputSentence.replace(firstWord, "Cuáles");
			}
			else if (firstWord.toLowerCase().matches("que")) {
				outputSentence = outputSentence.replace(firstWord, "Qué");
			}
			else if (firstWord.toLowerCase().matches("cuanto")) {
				outputSentence = outputSentence.replace(firstWord, "Cuánto");
			}
			else if (firstWord.toLowerCase().matches("cuantos")) {
				outputSentence = outputSentence.replace(firstWord, "Cuántos");
			}
		}
		
		return outputSentence;
	}
}
