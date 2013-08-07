package org.squadra.atenea.aiengine.algorithm;

import org.squadra.atenea.aiengine.model.SyntaxTree;
import org.squadra.atenea.aiengine.morphologic.MorphologicAnalizer;
import org.squadra.atenea.aiengine.syntactic.SyntacticAnalizer;
import org.squadra.atenea.ateneacommunication.Message;

/**
 * Algoritmo principal de Inteligencia Artificial de Atenea. 
 * Sirve como controlador ya que se encarga de ejecutar los analizadores morfologico, 
 * sintactico, semantico, buscador de respuestas, etc. utilizando la salida de uno
 * como entrada del otro.
 * 
 * @author Leandro Morrone
 *
 */
public class AIAlgorithm implements AbstractAlgorithm {

	@Override
	public Message execute(Message inputMessage) {
		
		// Ejecuto el analizador morfologico
		String correctSentence = MorphologicAnalizer.execute(inputMessage.getText());
		
		// Ejecuto el analizador sintactico
		SyntaxTree syntaxTree = SyntacticAnalizer.execute(correctSentence);
		
		// Ejecuto el analizador semantico
		
		//...
		
		// Devuelvo el mensaje de salida
		return null;
	}

}
