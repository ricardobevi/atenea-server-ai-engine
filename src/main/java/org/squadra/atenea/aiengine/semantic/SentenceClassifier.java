package org.squadra.atenea.aiengine.semantic;

import java.util.ArrayList;

import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.base.graph.Graph;
import org.squadra.atenea.base.graph.Node;
import org.squadra.atenea.base.word.Word;
import org.squadra.atenea.base.word.WordTypes;
import org.squadra.atenea.parser.model.Sentence;
import org.squadra.atenea.parser.model.SyntacticNode;
import org.squadra.atenea.parser.model.Sentence.Type;

public class SentenceClassifier {

/**
 * Clasifica la oracion en PREGUNTA, DIALOGO, AFIRMACION u ORDEN
 * asignandole el type al objeto Sentence y devuelve un string con
 * el significado del mensaje.
 * @param sentence Oracion analizada sintacticamente.
 * @return Tipo de significado el mensaje del usuario.
 */
public static String classify(Message message, Sentence sentence) {
		
		Type sentenceType = Type.UNKNOWN;
		String userMessageType = "";
		ArrayList<Word> words = sentence.getAllWords(false);
		
		// Si la primera palabra es un verbo en infinitivo -> es una ORDEN
		if (words.get(0).getMode().equals(WordTypes.Mode.INFINITIVE)) {
			
			sentenceType = Type.ORDER;
			message.setOrder(sentence.toString());
			
			// TODO: cuando mudemos las acciones al servidor, aca hay que validar
			//       si es una accion conocida o desconocida
			
			userMessageType = UserMessageType.Order.ORDEN_DESCONOCIDA;
			
			System.out.println("Clasificacion: ORDEN");
		}
		else {
			
			sentenceType = Type.DIALOG;
			
			// TODO: procesar tipo de mensaje para DIALOGOS
			
			userMessageType = UserMessageType.Dialog.SALUDO;
		}
		
		sentence.setType(sentenceType);
		
		return userMessageType;
	}


	@Deprecated
	public static String classify2(Sentence sentence) {
		
		Type sentenceType = Type.UNKNOWN;
		Graph<SyntacticNode> parseTree = sentence.getParseTree();
		
		for (Node<SyntacticNode> node : parseTree.getGraph().values()) {
			if (node.getId() != 0) {
				
				// Si uno de los nodos es de tipo QUESTION o la palabra es
				// un pronombre INTERROGATIVO, entonces es una pregunta.
				
				if (node.getData().getWord().getType() == WordTypes.Type.INTERROGATIVE
					|| node.getData().getType().matches(".*FS-QUE.*")) {
					sentenceType = Type.QUESTION;
				}
				
				// Si uno de los nodos es de tipo COMMAND o la palabra es
				// un verbo en modo IMPERATIVO, entonces es una orden.
				
				if (node.getData().getWord().getMode() == WordTypes.Mode.IMPERATIVE
					|| node.getData().getType().matches(".*FS-COM.*")) {
					sentenceType = Type.ORDER;
				}
				
				// Si una de las palabras es una INTERJECTION, entonces es
				// una interjeccion.
				
				else if (node.getData().getWord().getType() == WordTypes.Type.INTERJECTION) {
					sentenceType = Type.DIALOG;
				}
				
				// Si no es pregunta, orden ni interjeccion, entonces es afirmacion
				else {
					sentenceType = Type.ASSERTION;
				}
			}
		}
		
		sentence.setType(sentenceType);
		
		return null;
	}
	
}
