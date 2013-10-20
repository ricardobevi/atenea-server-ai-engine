package org.squadra.atenea.aiengine.morphologic;

public class MorphologicAnalizer {

	public static String execute(String inputSentence) {
		
		String outputSentence = inputSentence;
		
		//TODO: Aca hay que adaptar la oracion recibida de Google al formato requerido por el CG3
		
		outputSentence = outputSentence.replace("Atenea", "atenea");
		
		return outputSentence;
	}
}
