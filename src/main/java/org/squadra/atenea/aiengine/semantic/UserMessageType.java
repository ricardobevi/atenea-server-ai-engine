package org.squadra.atenea.aiengine.semantic;

/**
 * Tipos de mensajes del usuario que puede reconoce Atenea. En base a ellos
 * buscara una respuesta acorde en la clase DialogResponseSearcher.
 * 
 * @author Leandro Morrone
 *
 */
public class UserMessageType {

	public static class Dialog {
		
		public static final String SALUDO = "SALUDO";
		public static final String SALUDO_PREGUNTA_ESTADO_ANIMO = "SALUDO_PREGUNTA_ESTADO_ANIMO";
		public static final String DESPEDIDA = "DESPEDIDA";
		
		public static final String PREGUNTA_ESTADO_ANIMO = "PREGUNTA_ESTADO_ANIMO";
		public static final String PREGUNTA_NOMBRE = "PREGUNTA_NOMBRE";
		public static final String PREGUNTA_EDAD = "PREGUNTA_EDAD";
		public static final String PREGUNTA_QUE_HACES = "PREGUNTA_QUE_HACES";
	}
	
	public static class Order {
		
		public static final String ORDEN_CONOCIDA = "ORDEN_CONOCIDA";
		public static final String ORDEN_DESCONOCIDA = "ORDEN_DESCONOCIDA";
	}
	
	public static class Question {
		
		public static final String QUESTION = "QUESTION";
	}
	
	public static final String UNKNOWN = "UNKNOWN";
}
