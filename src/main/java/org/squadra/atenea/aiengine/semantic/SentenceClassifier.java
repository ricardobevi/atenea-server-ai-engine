package org.squadra.atenea.aiengine.semantic;

import org.squadra.atenea.base.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.squadra.atenea.aiengine.responses.ResponseType;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.base.actions.Click;
import org.squadra.atenea.base.actions.ListOfAction;
import org.squadra.atenea.base.graph.Graph;
import org.squadra.atenea.base.graph.Node;
import org.squadra.atenea.base.word.Word;
import org.squadra.atenea.base.word.WordTypes;
import org.squadra.atenea.data.server.Neo4jServer;
import org.squadra.atenea.parser.model.Sentence;
import org.squadra.atenea.parser.model.SimpleSentence;
import org.squadra.atenea.parser.model.SyntacticNode;
import org.squadra.atenea.parser.model.Sentence.Type;
import org.squadra.atenea.wordclassifier.WordClassifier;

@Log4j
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
		
		// VERIFICO SI ES UNA ORDEN 
		log.debug("Verificando si es una orden...");
		
		if (isOrder(sentence)) {
			sentenceType = Type.ORDER;
			
			//Se determina si la accion es conocida o no
			
			String orderName = !sentence.getMainVerbs().isEmpty() ? sentence.getMainVerbs().get(0).getBaseWord() + " ": "";
			
			for( Word element : sentence.getNouns() ){
				
				//TODO: Este conjunto de palabras en el if deberia ser un metodo isValidNoun4Order
				if( !element.getName().toLowerCase().equals("atenea") &&
				    !element.getName().toLowerCase().equals("favor") ){
					orderName += element.getName();
				}
				
			}
			
			System.out.println(orderName);
			message.setOrder(orderName);
			
			List<Click> lista = ListOfAction.getInstance().getAction(orderName);
			
			//Accion conocida
			if (lista != null)
			{
				message.setType(Message.ORDER);
				userMessageType = UserMessageType.Order.ORDEN_CONOCIDA;
				log.debug("Clasificacion: ORDEN CONOCIDA");
				
				for (Click click : lista) 
				{
					message.setIcon(click.serialize());
				}
			}
			
			//Si es una accion precargada o comando
			else if ( ListOfAction.getInstance().getPreLoadAction(orderName) != null 
					|| ListOfAction.getInstance().getCommand(orderName) != null )
			{
				System.out.println(ListOfAction.getInstance().getPreLoadAction(orderName));
				System.out.println(ListOfAction.getInstance().getCommand(orderName));
				
				message.setType(Message.PRELOAD_ACTION);
				userMessageType = UserMessageType.Order.ORDEN_CONOCIDA;
				
				log.debug("Clasificacion: ORDEN PRECARGADA o COMANDO");
			}
			
			else //Accion desconocida
			{
				message.setType(Message.LEARN_ACTION);
				log.debug("Clasificacion: ORDEN DESCONOCIDA");
				userMessageType = UserMessageType.Order.ORDEN_DESCONOCIDA;
			}
			
		}
		
		else {
			
			// VERIFICO SI ES UNA PREGUNTA
			log.debug("Verificando si es una pregunta...");
			
			userMessageType = isQuestion(sentence);
			
			if (userMessageType != UserMessageType.UNKNOWN) {
				sentenceType = Type.QUESTION;
				log.debug("Clasificacion: PREGUNTA");
			}
		
			else {
				
				// VERIFICO SI ES UN DIALOGO
				log.debug("Verificando si es un dialogo...");
				
				userMessageType = isDialog(sentence);
				
				if (userMessageType != UserMessageType.UNKNOWN) {
					sentenceType = Type.DIALOG;
					log.debug("Clasificacion: DIALOGO");
				}
				
				// SINO POR DEFECTO ES UNA AFIRMACION
				
				else {
					sentenceType = Type.ASSERTION;
					log.debug("Clasificacion: AFIRMACION / DESCONOCIDA");
				}
			}
			
		}
		
		log.debug("Tipo de mensaje: " + userMessageType);
		sentence.setType(sentenceType);
		return userMessageType;
	}

	
	/**
	 * Indica si la oracion es una pregunta.
	 * @param sentence
	 * @return Si es un dialogo, devuelve el tipo de dialogo.
	 *         Sino devuelve tipo desconocido (que no es dialogo).
	 */
	private static String isQuestion(Sentence sentence) {

		String messageType = UserMessageType.UNKNOWN;
		String questionWord;
		
		// Obtengo el adverbio interrigativo de la oracion
		try {
			questionWord = sentence.getQuestionWords().get(0).getName();
		}
		catch (Exception e) {
			// Si la oracion no contiene un interrogativo, no es una pregunta
			return messageType;
		}
		
		// Calculo el tipo de pregunta de realizada
		
		if (questionWord.toLowerCase().matches("dónde")) {
			messageType = UserMessageType.Question.DONDE;
		}
		else if (questionWord.toLowerCase().matches("cuándo")) {
			messageType = UserMessageType.Question.CUANDO;
		}
		else if (questionWord.toLowerCase().matches("quién")) {
			messageType = UserMessageType.Question.QUIEN;
		}
		else if (questionWord.toLowerCase().matches("qué")) {
			messageType = UserMessageType.Question.QUE;
		}
		
		return messageType;
	}


	/**
	 * Indica si la oracion es un dialogo.
	 * @param sentence
	 * @return Si es un dialogo, devuelve el tipo de dialogo.
	 *         Sino devuelve tipo desconocido (que no es dialogo).
	 */
	private static String isDialog(Sentence sentence) {
		
		String messageType = UserMessageType.UNKNOWN;
		SimpleSentence inputSentence = sentence.toSimpleSentence(false);
		ArrayList<SimpleSentence> outputSentences;
		
		// Verifico que la cache de dialogos este cargada
		if (Neo4jServer.dialogCache != null) {
			outputSentences = Neo4jServer.dialogCache;
		} else {
			return messageType;
		}
		
		// Si hay una palabra que sea Atenea en medio del saludo, la elimino
		Iterator<Word> it = inputSentence.getWords().iterator();
		while (it.hasNext()) {
			Word word = it.next();
			
			if (word.getName().matches("Atenea|atenea")) {
				it.remove();
			}
		}
		
		String inputSentenceStr = inputSentence.toString();
		
		String responseType = "";
		Boolean flagContain = false;
		
		// Verifico si la oracion de entrada es un tipo de dialogo conocido por Atenea
		
		int i = 0;
		while ( !flagContain && i < outputSentences.size() ){
			
			String outputSentenceStr = outputSentences.toString();
			
			// TODO: metodo toGoogleEntry en SimpleSentence
			if( StringUtil.replaceAccents(outputSentenceStr.toLowerCase()).contains(
					StringUtil.replaceAccents(inputSentenceStr.toLowerCase())) ){
				flagContain = true;
				responseType = outputSentences.get(i).getWords().get(0).getName();
			}
			
			i++;
		}
		
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

	

	/**
	 * Indica si la oracion es una orden.
	 * @param sentence
	 * @return true si es una orden, false si no lo es
	 */
	private static boolean isOrder(Sentence sentence) {
		
		ArrayList<Word> allWords = sentence.getAllWords(false);
		ArrayList<Sentence> allVerbs = sentence.getVerbs();
		ArrayList<Word> mainVerbs =  sentence.getMainVerbs();
		
		Boolean isOrder = false; 
		
		System.out.println( "---------- ES ORDEN ---------" );
		
		//comienza con un infinitivo
		if( allWords.get(0).getMode().equals(WordTypes.Mode.INFINITIVE) ){
			System.out.println( "---------- ES INFINITIVO ---------" );
			isOrder = true;
		}
			
		
		//contiene expresion de deseo
		if( !isOrder &&  !allVerbs.isEmpty() ){
			
			Sentence Verbs = allVerbs.get(0);
			
			for (Word word : Verbs.getAllWords(false) ) {
				
				for (Word verb : mainVerbs ) {
					
					System.out.println( "Es Deseo: " + word.getName() + " - MainVerb " + verb.getName() ); 
					if ( containsDesireExpression( word.getBaseWord() ) &&  !word.getName().equals( verb.getName())  ) {
						
						System.out.println( "---------- ES EXPRESION DE DESEO ---------: "  );
						isOrder = true;
					}
				}
					
			}
		}
		
		//empieza con imperativo
		if( !isOrder &&  !allVerbs.isEmpty() ){
			WordClassifier classifier = new WordClassifier();
			
			//TODO: se podria hacer que isImperative tome el verbo base y el conjugado, 
			//      asi sabe donde ir a busar en la RAE. Tmb hay que ver que saltee el Atenea y el por favor
			
			Sentence Verbs = allVerbs.get(0);
			
			for (Word word : Verbs.getAllWords(false) ) {
				if ( !classifier.isImperative( word.getName() ).equals("") ) {
					System.out.println( "---------- ES IMPERATIVO ---------" );
					isOrder = true;
				}
			}

		}
		
		//TODO: El verbo es imperativo con me
		
		
		return isOrder;
		
	}



	private static boolean containsDesireExpression(String verb) {
		
		boolean isDesireExpression = false;
		
		System.out.println("containsDesireExpression: contiene expresion de deseo?: " +  verb);
		
		ArrayList<String> desireExpressions = new ArrayList<String>();
		desireExpressions.add("gustar");
		desireExpressions.add("querer");
		desireExpressions.add("poder");
		desireExpressions.add("necesitar");

		for (String desireExpression : desireExpressions) {
			
			if( verb.equals( desireExpression ) ){
				isDesireExpression = true;
			}
			
		}
		
		
		return isDesireExpression;
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
