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
		public static final String DESPEDIDA = "DESPEDIDA";
		
		public static final String PREGUNTA_ESTADO_ANIMO = "PREGUNTA_ESTADO_ANIMO";
		public static final String PREGUNTA_NOMBRE = "PREGUNTA_NOMBRE";
		public static final String PREGUNTA_EDAD = "PREGUNTA_EDAD";
		public static final String PREGUNTA_QUE_HACES = "PREGUNTA_QUE_HACES";
		
		public static final String RESPUESTA_NOMBRE_USUARIO = "RESPUESTA_NOMBRE_USUARIO";
	}
	
	public static class Order {
		
		public static final String ORDEN_CONOCIDA = "ORDEN_CONOCIDA";
		public static final String ORDEN_DESCONOCIDA = "ORDEN_DESCONOCIDA";
	}
	
	public static class Question {
		
		public static final String QUIEN = "QUIEN";
		public static final String CUAL = "CUAL";
		public static final String CUANDO = "CUANDO";
		public static final String CUANTO = "CUANTO";
		public static final String DONDE = "DONDE";
		public static final String QUE = "QUE";
		public static final String OTRO = "OTRO";
	}
	
	public static final String UNKNOWN = "UNKNOWN";
}
