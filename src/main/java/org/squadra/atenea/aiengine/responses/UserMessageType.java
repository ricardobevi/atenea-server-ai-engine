package org.squadra.atenea.aiengine.responses;

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
		public static final String PREGUNTA_ESTADO_ANIMO = "PREGUNTA_ESTADO_ANIMO";
		public static final String SALUDO_PREGUNTA_ESTADO_ANIMO = "SALUDO_PREGUNTA_ESTADO_ANIMO";
		public static final String PREGUNTA_NOMBRE = "PREGUNTA_NOMBRE";
	}
}
