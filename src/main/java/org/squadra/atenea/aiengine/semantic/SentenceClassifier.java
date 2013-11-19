package org.squadra.atenea.aiengine.semantic;

import java.util.List;

import lombok.extern.log4j.Log4j;

import org.squadra.atenea.aiengine.semantic.helper.DialogHelper;
import org.squadra.atenea.aiengine.semantic.helper.OrderHelper;
import org.squadra.atenea.aiengine.semantic.helper.QuestionHelper;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.base.actions.Click;
import org.squadra.atenea.base.actions.ListOfAction;
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
		String resultMessageType;
		
		if ( !(resultMessageType = DialogHelper.isDialog(sentence)).equals( UserMessageType.UNKNOWN ) ) {
			//ES UN DIALOGO
			log.debug("Clasificacion: DIALOGO");
			sentenceType = Type.DIALOG;
			
		}
		else if ( OrderHelper.isOrder(sentence) ) {
			//ES UNA ORDEN
			sentenceType = Type.ORDER;
			
			//TODO: las lineas siguientes deberían estar dentro de OrderHelper o en otra clase,
			// ya que no solo clasifican la oracion, sino que actuan sobre mensaje. Al mismo tiempo parte de este codigo se repite
			// en isOrder, se debería unificar

			//Se determina si la accion es conocida o no
			String orderName = OrderHelper.getParsedOrder(sentence); 
					
			System.out.println("OrderName: " + orderName);
			message.setOrder(orderName);
			
			List<Click> lista = ListOfAction.getInstance().getAction(orderName);
			//Accion conocida (ya enseñada)
			if (lista != null)
			{
				message.setType(Message.ORDER);
				resultMessageType = UserMessageType.Order.ORDEN_CONOCIDA;
				log.debug("Clasificacion: ORDEN CONOCIDA");
				
				for (Click click : lista) 
				{
					message.setIcon(click.serialize());
				}
			}
			//accion precargada o comando
			else if ( ListOfAction.getInstance().getPreLoadAction(orderName) != null 
					|| ListOfAction.getInstance().getCommand(orderName) != null )
			{
				System.out.println(ListOfAction.getInstance().getPreLoadAction(orderName));
				System.out.println(ListOfAction.getInstance().getCommand(orderName));
				
				message.setType(Message.PRELOAD_ACTION);
				resultMessageType = UserMessageType.Order.ORDEN_CONOCIDA;
				log.debug("Clasificacion: ORDEN PRECARGADA o COMANDO");
			}
			//Accion desconocida
			else 
			{
   				message.setType(Message.LEARN_ACTION);
   				resultMessageType = UserMessageType.Order.ORDEN_DESCONOCIDA;
   				log.debug("Clasificacion: ORDEN DESCONOCIDA");
		   	}
			
		}
		else if ( !(resultMessageType = QuestionHelper.isQuestion(sentence)).equals( UserMessageType.UNKNOWN ) ) {
			//ES UNA PREGUNTA
			log.debug("Clasificacion: PREGUNTA");
			sentenceType = Type.QUESTION;
		} 
		else {
			//POR DEFECTO ES UNA AFIRMACION
			log.debug("Clasificacion: AFIRMACION / DESCONOCIDA");
			sentenceType = Type.ASSERTION;
		}
		
		log.debug("Tipo de mensaje: " + resultMessageType);
		sentence.setType(sentenceType);
	
		return resultMessageType;
	}

}