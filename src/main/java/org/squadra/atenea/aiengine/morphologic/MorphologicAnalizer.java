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
			else if (firstWord.toLowerCase().matches("que")) {
				outputSentence = outputSentence.replace(firstWord, "Qué");
			}
		}
		
		return outputSentence;
	}
}
