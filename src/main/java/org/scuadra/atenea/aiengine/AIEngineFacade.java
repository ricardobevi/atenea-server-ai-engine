package org.scuadra.atenea.aiengine;

import org.atenea.data.server.NeuralDataAccess;
import org.scuadra.atenea.aiengine.algorithm.AbstractAlgorithm;
import org.scuadra.atenea.aiengine.algorithm.VerySimpleResponseAlgorithm;

public class AIEngineFacade {
	
	public Message execute(Message input) {
		
		Message response;
		
		//inicia la bd si no esta iniciada.
		NeuralDataAccess.init();
		
		AbstractAlgorithm algorithm = new VerySimpleResponseAlgorithm();
		
		response = algorithm.excecute(input);
		
		return response;
	}
	
}
