package org.squadra.atenea.aiengine.responses;

/**
 * Tipos de respuesta que puede dar Atenea. Deben coincidir con los del archivo
 * de texto AteneaDialogResponses.txt del DataLoader, que son los que se cargan 
 * en la base de datos neuronal.
 * 
 * @author Leandro Morrone
 *
 */
public class ResponseType {

	public static class Dialog {
		
		public static final String SALUDO = "$_SALUDO";
		public static final String SALUDO_INTERJECCION = "$_SALUDO_INTERJECCION";
		public static final String DESPEDIDA = "$_DESPEDIDA";
		
		public static final String PREGUNTA_ESTADO_ANIMO = "$_PREGUNTA_POR_ESTADO_DE_ANIMO";
		public static final String PREGUNTA_NECESIDAD = "$_PREGUNTA_POR_NECESIDAD";
		public static final String PREGUNTA_NOMBRE = "$_PREGUNTA_NOMBRE";
		
		public static final String RESPUESTA_ESTADO_ANIMO = "$_RESPUESTA_ESTADO_DE_ANIMO";
		public static final String RESPUESTA_NOMBRE_ATENEA = "$_RESPUESTA_NOMBRE";
		public static final String RESPUESTA_A_NOMBRE_USUARIO = "$_RESPUESTA_NOMBRE_USUARIO";
		public static final String RESPUESTA_A_ESTADO_ANIMO_USUARIO_BIEN = "$_RESPUESTA_ESTADO_DE_ANIMO_BIEN";
		public static final String RESPUESTA_A_ESTADO_ANIMO_USUARIO_MAL = "$_RESPUESTA_ESTADO_DE_ANIMO_MAL";
		public static final String RESPUESTA_EDAD = "$_RESPUESTA_EDAD";
		public static final String RESPUESTA_A_QUE_HACES = "$_RESPUESTA_QUE_HACES";
		
	}
	
	public static class Assertion {
		
		public static final String AFFIRMATION = "$_AFIRMACION";
		
	}
}
