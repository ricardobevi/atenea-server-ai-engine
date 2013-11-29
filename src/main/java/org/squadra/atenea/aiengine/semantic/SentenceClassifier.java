package org.squadra.atenea.aiengine.semantic;

import lombok.extern.log4j.Log4j;

import org.squadra.atenea.aiengine.responses.ResponseType;
import org.squadra.atenea.aiengine.semantic.helper.DialogHelper;
import org.squadra.atenea.aiengine.semantic.helper.OrderHelper;
import org.squadra.atenea.aiengine.semantic.helper.QuestionHelper;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.parser.model.Sentence;
import org.squadra.atenea.parser.model.Sentence.Type;

@Log4j
public class SentenceClassifier {

	/**
	 * Clasifica la oracion en PREGUNTA, DIALOGO, AFIRMACION u ORDEN asignandole
	 * el type al objeto Sentence y devuelve un string con el significado del
	 * mensaje.
	 * 
	 * @param sentence
	 *            Oracion analizada sintacticamente.
	 * @return Tipo de significado el mensaje del usuario.
	 */
	public static String classify(Message message, Sentence sentence) {

		Type sentenceType = Type.UNKNOWN;
		String userMessageType;
		Boolean classificationFlag = false; 
		
		userMessageType = DialogHelper.isDialog(sentence);
		if ( !userMessageType.equals( UserMessageType.UNKNOWN ) ) {
			//ES UN DIALOGO
			log.debug("Clasificacion: DIALOGO");
			sentenceType = Type.DIALOG;
			classificationFlag = true;
		}
		
		
		if ( !classificationFlag ) {

			userMessageType = OrderHelper.getTypeOfOrder(sentence , message);
			if(!userMessageType.equals( UserMessageType.UNKNOWN )){
				//ES UNA ORDEN
				log.debug("Clasificacion: ORDEN");
				sentenceType = Type.ORDER;
				classificationFlag = true;
			}
				
		}
		
		if( !classificationFlag )
		{ 
			userMessageType = QuestionHelper.isQuestion(sentence);
			if ( !userMessageType.equals( UserMessageType.UNKNOWN ) ) {
				//ES UNA PREGUNTA
				log.debug("Clasificacion: PREGUNTA");
				sentenceType = Type.QUESTION;
				classificationFlag = true;
			}
		}
		
		if( !classificationFlag )
		{
			//POR DEFECTO ES UNA AFIRMACION
			userMessageType = ResponseType.Default.AFFIRMATION;
			log.debug("Clasificacion: AFIRMACION / DESCONOCIDA");
			sentenceType = Type.ASSERTION;
			classificationFlag = true;
		}
		
		
		log.debug("Tipo de mensaje: " + userMessageType);
		sentence.setType(sentenceType);
	
		return userMessageType;
	}

}