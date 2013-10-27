package org.squadra.atenea.aiengine.syntactic;

import lombok.extern.log4j.Log4j;

import org.squadra.atenea.parser.Parser;
import org.squadra.atenea.parser.model.Sentence;

@Log4j
public class SyntacticAnalizer {

	/**
	 * Metodo que se encarga de realizar el analisis sintactico de la oracion recibida.
	 * Devuelve un arbol sintactico.
	 * @param inputSentence Oracion proveniente del analizador morfologico
	 * @return Arbol sintactico con la estructura de la oracion y las palabras clasificadas.
	 */
	public static Sentence execute(String inputSentence) {
		
		log.debug("Ejecutando analizador sintactico...");
		
		//TODO: Pre-parsing (identificar los verbos imperativos desde la BD neuronal)
		
		Sentence sentence = new Parser().parse(inputSentence);
		
		//TODO: Aplicar reglas de concordancia (genero, numero, etc)
		
		//TODO: Actualizar la base neuronal con las palabras desconocidas (o lo hacemos en el semantico)
		
		return sentence;
		
	}
	
	
	
}
