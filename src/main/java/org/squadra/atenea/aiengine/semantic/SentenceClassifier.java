package org.squadra.atenea.aiengine.semantic;

import java.util.ArrayList;
import java.util.Iterator;

import org.squadra.atenea.aiengine.responses.ResponseType;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.base.graph.Graph;
import org.squadra.atenea.base.graph.Node;
import org.squadra.atenea.base.word.Word;
import org.squadra.atenea.base.word.WordTypes;
import org.squadra.atenea.data.server.Neo4jServer;
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
		String userMessageType = UserMessageType.UNKNOWN;
		
		// Si la primera palabra es un verbo en infinitivo -> es una ORDEN
		if (isOrder(sentence)) {
			
			sentenceType = Type.ORDER;
			message.setOrder(sentence.toString());
			
			// TODO: cuando mudemos las acciones al servidor, aca hay que validar
			//       si es una accion conocida o desconocida
			
			userMessageType = UserMessageType.Order.ORDEN_CONOCIDA;
			
			System.out.println("Clasificacion: ORDEN");
		}
		else {
			
			userMessageType = isDialog(sentence);
			
			if (userMessageType != UserMessageType.UNKNOWN) {
				sentenceType = Type.DIALOG;
				System.out.println("Clasificacion: DIALOGO");
			}
			else {
				sentenceType = Type.ASSERTION;
				System.out.println("Clasificacion: AFIRMACION");
			}
		}
		
		sentence.setType(sentenceType);
		
		return userMessageType;
	}


	private static String isDialog(Sentence sentence) {
		
		ArrayList<Word> wordsInput = sentence.getAllWords(false);
		
		Iterator<Word> it = wordsInput.iterator();
		while (it.hasNext()) {
			Word word = it.next();
			
			if (word.getName().matches("Atenea|atenea")) {
				it.remove();
			}
		}
		
		ArrayList<ArrayList<Word>> sentencesOutput = Neo4jServer.dialogCache;
		
		String sentenceInput = "";
		for (Word word : wordsInput) {
			sentenceInput += word.getName() + " ";
		}
		
		String responseType = "";
		Boolean flagContain = false;
		
		int i = 0;
		while ( !flagContain && i < sentencesOutput.size() ){
			
			String sentenceOutput = "";
			
			for (Word word : sentencesOutput.get(i)) {
				sentenceOutput += word.getName() + " ";
			}
			
			// TODO: metodo toGoogleEntry en SimpleSentence
			if( replaceAccents(sentenceOutput.toLowerCase()).contains(
					replaceAccents(sentenceInput.toLowerCase())) ){
				flagContain = true;
				responseType = sentencesOutput.get(i).get(0).getName();
			}
			
			i++;
		}
		
		String messageType;
		
		switch (responseType) {
			case ResponseType.Dialog.SALUDO:
				messageType = UserMessageType.Dialog.SALUDO;
				break;
				
			case ResponseType.Dialog.DESPEDIDA:
				messageType = UserMessageType.Dialog.DESPEDIDA;
				break;
			
			case ResponseType.Dialog.PREGUNTA_ESTADO_ANIMO:
				messageType = UserMessageType.Dialog.PREGUNTA_ESTADO_ANIMO;
				break;
				
			case ResponseType.Dialog.PREGUNTA_NOMBRE:
				messageType = UserMessageType.Dialog.PREGUNTA_NOMBRE;
				break;
				
			case ResponseType.Dialog.PREGUNTA_EDAD:
				messageType = UserMessageType.Dialog.PREGUNTA_EDAD;
				break;
				
			default:
				messageType = UserMessageType.UNKNOWN;
				break;
		}
		
		return messageType;
	}


	private static boolean isOrder(Sentence sentence) {
		
		ArrayList<Word> words = sentence.getAllWords(false);
		
		if (words.get(0).getMode().equals(WordTypes.Mode.INFINITIVE)) {
			return true;
		}
		return false;
	}


	
	public static String replaceAccents(String input) {
		
		String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
	    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
	    String output = new String(input);
	    
	    for (int j = 0; j < original.length(); j++) {
	        output = output.replace(original.charAt(j), ascii.charAt(j));
	    }
	    
	    return output;
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
