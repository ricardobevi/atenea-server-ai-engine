package org.squadra.atenea.aiengine.algorithm;

import org.squadra.atenea.aiengine.morphologic.MorphologicAnalizer;
import org.squadra.atenea.aiengine.responses.ResponseSearcher;
import org.squadra.atenea.aiengine.semantic.UserMessageType;
import org.squadra.atenea.aiengine.syntactic.SyntacticAnalizer;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.parser.model.Sentence;

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
		String correctStringSentence = MorphologicAnalizer.execute(inputMessage.getText());
		
		// Ejecuto el analizador sintactico
		Sentence syntacticSentence = SyntacticAnalizer.execute(correctStringSentence);
		
		// TODO: Ejecuto el analizador semantico
		// TODO: El semantico deberia definir el tipo de oracion y el significado del mensaje
		
		// Ejecuto la busqueda de respuestas
		String responseText = ResponseSearcher.execute(Sentence.Type.DIALOG, UserMessageType.Dialog.SALUDO);
		
		// Devuelvo el mensaje de salida
		return null;
	}

}
