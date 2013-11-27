package org.squadra.atenea.aiengine.morphologic;

import java.util.HashMap;

import lombok.extern.log4j.Log4j;

@Log4j
public class MorphologicAnalizer {
	
	private static HashMap<String, String> imperativeVerbs = new  HashMap<String, String> ();
	
	static{
	
		//TODO: esto debería ir en un archivo o a futuro consultarse de otro lado, 
		// se agrego aca porque el parser no lo reconoce y nos saca las papas del fuego
		// descarga, envia , escribi , escucha
		
		if (imperativeVerbs.isEmpty()) {
		
			imperativeVerbs.put("abrí", "abrir");
			imperativeVerbs.put("abrime", "abrir");
			imperativeVerbs.put("abrile", "abrir");
			imperativeVerbs.put("abrite", "abrir");
			imperativeVerbs.put("abrinos", "abrir");
			imperativeVerbs.put("abriles", "abrir");
			imperativeVerbs.put("abrila", "abrir");
			imperativeVerbs.put("abrilas", "abrir");
			imperativeVerbs.put("abrilo", "abrir");
			imperativeVerbs.put("abrilos", "abrir");
			               
			imperativeVerbs.put("ejecuta", "ejecutar");
			imperativeVerbs.put("ejecutame", "ejecutar");
			imperativeVerbs.put("ejecutale", "ejecutar");
			imperativeVerbs.put("ejecutate", "ejecutar");
			imperativeVerbs.put("ejecutanos", "ejecutar");
			imperativeVerbs.put("ejecutales", "ejecutar");
			imperativeVerbs.put("ejecutala", "ejecutar");
			imperativeVerbs.put("ejecutalas", "ejecutar");
			imperativeVerbs.put("ejecutalo", "ejecutar");
			imperativeVerbs.put("ejecutalos", "ejecutar");
			              
			imperativeVerbs.put("busca", "buscar");
			imperativeVerbs.put("buscame", "buscar");
			imperativeVerbs.put("buscale", "buscar");
			imperativeVerbs.put("buscate", "buscar");
			imperativeVerbs.put("buscanos", "buscar");
			imperativeVerbs.put("buscales", "buscar");
			imperativeVerbs.put("buscala", "buscar");
			imperativeVerbs.put("buscalas", "buscar");
			imperativeVerbs.put("buscalo", "buscar");
			imperativeVerbs.put("buscalos", "buscar");
		}
		
	}

	public static String execute(String inputSentence) {
		
		log.debug("Ejecutando analizador morfologico...");
		
		String outputSentence = inputSentence.trim();
		
		outputSentence = outputSentence.replace("Atenea", "atenea");
		
		//reemplazo imperativos para que los tome la gramática
		
		outputSentence = replaceImperativeVerbs( outputSentence );
		
		
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

	
	private static String replaceImperativeVerbs(String inputSentence) {

		String[] inputSentences = inputSentence.split(" ");
		String replacedSentence = "";
		
		
		for (int i = 0; i < inputSentences.length; i++) {
			
			if ( imperativeVerbs.containsKey(inputSentences[i] ) ) {
				
				replacedSentence += imperativeVerbs.get(inputSentences[i]) + " ";
				
			} else {

				replacedSentence += inputSentences[i] + " ";
			}
			
		}
			
		return replacedSentence;
	}
}
