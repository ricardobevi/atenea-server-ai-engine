package org.squadra.atenea.aiengine.semantic.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.squadra.atenea.aiengine.semantic.UserMessageType;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.base.actions.Click;
import org.squadra.atenea.base.actions.ListOfAction;
import org.squadra.atenea.base.word.Word;
import org.squadra.atenea.base.word.WordTypes;
import org.squadra.atenea.parser.model.Sentence;
import org.squadra.atenea.wordclassifier.WordClassifier;

import com.google.gson.Gson;

@Log4j
public class OrderHelper {

	// por ahora el hashset se carga en el metodo isRelevantWord
	private static HashSet<String> irrelevantWords = new HashSet<String>();
	// por ahora el hashset se carga en el metodo isDesireExpression
	private static HashSet<String> desireExpressions = new HashSet<String>();
	
	static {
		
		if (irrelevantWords.isEmpty()) {
			irrelevantWords.add("atenea");
			irrelevantWords.add("ateneo");
			irrelevantWords.add("por");
			irrelevantWords.add("favor");
			irrelevantWords.add("por favor");
			irrelevantWords.add("gracias");
			irrelevantWords.add("disculpa");
			irrelevantWords.add("disculpame");
		}
		
		if (desireExpressions.isEmpty()) {
			desireExpressions.add("gustar");
			desireExpressions.add("querer");
			desireExpressions.add("poder");
			desireExpressions.add("necesitar");
			desireExpressions.add("desear");
		}
		
	}

	public static String getTypeOfOrder(Sentence sentence, Message message) {

		// muestro las expresiones de deseo y palabras irrelevantes cargadas
		Gson gson = new Gson();
		System.out.println( "Expresiones de deseo:" + gson.toJson(desireExpressions));
		System.out.println( "Palabras irrelevantes:" + gson.toJson( irrelevantWords ));

		//Declaro variables necesarias con sus valores por defecto
		Boolean classifierFlag = false;
		int messageType = Message.UNKNOWN;
		String userMessageType =  UserMessageType.UNKNOWN;
		String orderName = "";
		
		String processedSentence = OrderHelper.getParsedOrder(sentence);
		String filteredSentence  = OrderHelper.filterIrrelevantWords(sentence.getAllWords(false));
		
		//COMIENZO DE LOGICA DE NEGOCIO

		//si es comando
		orderName = getCommand(filteredSentence , processedSentence ); 
		if( !orderName.equals( "" ) ){
			log.debug("Clasificacion: ORDEN COMANDO");
			messageType = Message.PRELOAD_ACTION;
			userMessageType = UserMessageType.Order.ORDEN_CONOCIDA;
			classifierFlag = true;
		}
		
		//si es accion precargada
		if(  !classifierFlag  ){
		
			orderName = getPreLoadedAction( filteredSentence , processedSentence  );
			
			if(  !orderName.equals("") ){
				log.debug("Clasificacion: ORDEN PRECARGADA");
				messageType = Message.PRELOAD_ACTION;
				userMessageType = UserMessageType.Order.ORDEN_CONOCIDA;
				classifierFlag = true;
			}
		}
		
		//si es accion ya conocida
		if(  !classifierFlag  ){
		
			orderName = getLearnedAction( filteredSentence , processedSentence  ) ;
			
			if( !orderName.equals( "" ) ){
				log.debug("Clasificacion: ORDEN ENSEÑADA");
				messageType = Message.ORDER;
				userMessageType = UserMessageType.Order.ORDEN_CONOCIDA;
				classifierFlag = true;
				
				 List<Click> lista = getListOfClick(orderName);
				
				for (Click click : lista) 
				{
					message.setIcon(click.serialize());
				}
			}
		}
		
		//si es una posible accion desconocida
		if(  !classifierFlag  ){
			
			orderName = isPossibleOrder( sentence , processedSentence );

			if( !orderName.equals( "" ) ){
				log.debug("Clasificacion: ORDEN DESCONOCIDA");
				messageType = Message.LEARN_ACTION;
				userMessageType = UserMessageType.Order.ORDEN_DESCONOCIDA;
				classifierFlag = true;
			}
		}
		
		
		
		//seteo resultados
		if ( classifierFlag ) {
			message.setOrder( orderName );
			message.setType( messageType );
		}
		
		return userMessageType;
	}

	
	private static String getCommand(String filteredSentence, String processedSentence) {
		
		String orderName = "";
		
		if (ListOfAction.getInstance().getCommand(filteredSentence) != null) {
			orderName = filteredSentence;
		}
		else if(ListOfAction.getInstance().getCommand(processedSentence) != null){
			orderName = processedSentence;
		}
		
		return  orderName ;
	}

	private static String getPreLoadedAction(String filteredSentence, String processedSentence) {
		
		String orderName = "";
		
		if (ListOfAction.getInstance().getPreLoadAction(filteredSentence) != null) {
			orderName = filteredSentence;
		}
		else if(ListOfAction.getInstance().getPreLoadAction(processedSentence) != null){
			orderName = processedSentence;
		}
		
		return  orderName ;
	}

	private static String getLearnedAction(String filteredSentence, String processedSentence) {
	
		String orderName = "";
		
		if (ListOfAction.getInstance().getAction(filteredSentence) != null) {
			orderName = filteredSentence;
		}
		else if(ListOfAction.getInstance().getAction(processedSentence) != null){
			orderName = processedSentence;
		}
		
		return  orderName;		
	}
	
	private static List<Click> getListOfClick(String orderName) {
		return ListOfAction.getInstance().getAction(orderName) ;
	}


	/**
	 * Este metodo indica si la oracion entregada expresa una order. Toma como
	 * orden que comience con un verbo infinitivo, que se construya con una
	 * expresion de deseo o un verbo imperativo. Previo al analisis elimina
	 * palabras irrelevantes
	 * @param processedSentence 
	 * @param sentence 
	 * 
	 * @param sentence
	 * @return true/false
	 */
	private static String isPossibleOrder(Sentence sentence, String processedSentence) {
		
		ArrayList<Word> allWords = sentence.getAllWords(false);
		ArrayList<Sentence> allVerbs = sentence.getVerbs();
		ArrayList<Word> mainVerbs = sentence.getMainVerbs();
		Boolean isOrder = false;

		// chequeo si comienza con infinitivo excluyendo palabras irrelevantes
		isOrder = isInfinitiveAction(allWords);

		// chequeo si contiene expresion de deseo en sus verbos
		if (!isOrder && !allVerbs.isEmpty()) {
			isOrder = isDesireExpressionAction(allVerbs, mainVerbs);
		}

		// chequeo si comienza con imperativo excluyendo palabras irrelevantes
		/*
		if (!isOrder) {
			isOrder = isImperativeVerbAction(allWords);
		}
		*/

		return isOrder? processedSentence : "";
	}
	

	/**
	 * Este metodo indica si la oracion es una accion expresada mediante verbos
	 * imperativos
	 * @param allVerbs 
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Boolean isImperativeVerbAction(ArrayList<Word> allWords) {

		Boolean isOrder = false;
		Boolean isImperativeChecked = false;
		WordClassifier classifier = new WordClassifier();
		Integer i = 0;

		while (!isImperativeChecked && i < allWords.size()) {

			if (isRelevantWord(allWords.get(i).getName())) {

				log.debug("Orden: verificando si es verbo y es imperativo " + allWords.get(i).getName()
						+ " es imperativo ( base: " + allWords.get(i).getBaseWord() + ")");

				if (  !classifier.isImperative( allWords.get(i).getName(), allWords.get(i).getBaseWord() )) {
					log.debug("Orden: COMIENZA CON VERBO IMPERATIVO");
					isImperativeChecked = true;
					isOrder = true;
				} else {
					isImperativeChecked = true;
				}
			}

			i++;
		}
		
		return isOrder;

	}

	/**
	 * Este metodo indica si la oracion es una accion expresada mediante
	 * expression de deseo
	 * @param mainVerbs 
	 * @param allVerbs 
	 * 
	 * @return
	 */
	private static Boolean isDesireExpressionAction(ArrayList<Sentence> allVerbs, ArrayList<Word> mainVerbs) {

		Sentence verbs = allVerbs.get(0);
		Boolean isOrder = false;
		
		for (Word word : verbs.getAllWords(false)) {
			for (Word verb : mainVerbs) {

				log.debug("Es Deseo? " + word.getName() + " - MainVerb "
						+ verb.getName());

				if (containsDesireExpression(word.getBaseWord())
						&& !word.getName().equals(verb.getName())) {
					log.debug("Orden: Contiene expresion de deseo");
					isOrder = true;
				}
			}
		}
		
		return isOrder;

	}

	/**
	 * Este metodo indica si la oracion comienza con un palabra relevante que
	 * sea verbo infinitivo
	 * 
	 * @param allWords
	 * @return
	 */
	private static Boolean isInfinitiveAction(ArrayList<Word> allWords) {

		Boolean isInfinitiveChecked = false;
		Boolean isOrder = false;
		Integer i = 0;

		while (!isInfinitiveChecked && i < allWords.size()) {

			if (isRelevantWord(allWords.get(i).getName())) {

				log.debug("Comienza con infinitivo? "
						+ allWords.get(i).getName());

				if (allWords.get(i).getMode().equals(WordTypes.Mode.INFINITIVE)) {
					log.debug("Orden: COMIENZA CON INFINITIVO");
					isInfinitiveChecked = true;
					isOrder = true;
				} else {
					isInfinitiveChecked = true;
				}
			}

			i++;
		}

		return isOrder;
	}

	/**
	 * Este metodo se utiliza para interpretar la orden solicitada y detectar
	 * que es lo que realmente busca realizar el usuario
	 * 
	 * @param sentence
	 * @return orderNameParsed
	 */
	public static String getParsedOrder(Sentence sentence) {

		String orderName = new String();

		/*
		 * Se procede a tomar los verbos ,sustantivos principales y adjetivos 
		 */
		for (Word element : sentence.getAllWords(false)) { 
			if ( element.getType().equals( WordTypes.Type.VERB  ) || element.getType().equals(WordTypes.Type.NOUN) || element.getType().equals(WordTypes.Type.ADJECTIVE)  ) {
				orderName += element.getBaseWord() + " ";
			}
		}

		log.debug("OrderName: " + orderName + " - valor devuelto: "	+ filterIrrelevantWords(orderName).trim() + " Oración inicial: "	+ sentence.toSimpleSentence(false).toString());
		
		return filterIrrelevantWords(orderName).trim();
	}

	/**
	 * Este metodo elimina las palabras irrelevantes de la oracion
	 * 
	 * @param orderName
	 * @return FilteredOrderName
	 */
	private static String filterIrrelevantWords(String orderName) {

		String filteredWords = new String();

		for (String word : orderName.split(" ")) {
			if (isRelevantWord(word)) {
				filteredWords += word + " ";
			}
		}

		return filteredWords;
	}
	
	/**
	 * Este metodo elimina las palabras irrelevantes de la oracion
	 * 
	 * @param orderName
	 * @return FilteredOrderName
	 */
	private static String filterIrrelevantWords(ArrayList<Word> sentence) {

		String filteredWords = new String();

		for (Word word : sentence) {
			if (isRelevantWord(word.getBaseWord())) {
				filteredWords += word + " ";
			}
		}

		return filteredWords;
	}

	/**
	 * Este metodo indica si una palabra es irrelevante para ejecutar acciones
	 * 
	 * @param word
	 * @return true/false
	 */
	private static boolean isRelevantWord(String word) {
		return !irrelevantWords.contains(word.toLowerCase());
	}

	/**
	 * Este metodo indica si un verbo en infinitivo es un verbo que indica
	 * expresion de deseo
	 * 
	 * @param verb
	 * @return true/false
	 */
	public static boolean containsDesireExpression(String verb) {

		System.out.println("viendo si es expresion de deseo");
		return desireExpressions.contains(verb.toLowerCase());
	}

}
