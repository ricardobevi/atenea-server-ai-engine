package org.scuadra.atenea.aiengine;

import org.atenea.data.server.NeuralDataAccess;
import org.scuadra.atenea.aiengine.algorithm.AbstractAlgorithm;
import org.scuadra.atenea.aiengine.algorithm.VerySimpleResponseAlgorithm;

public class AIEngineFacade {
	
	private Boolean isDBStarted;
	
	public AIEngineFacade(){
		this.initDatabase();
	}
	
	public Message execute(Message input) {
		
		Message response;
		
		this.initDatabase();
		
		AbstractAlgorithm algorithm = new VerySimpleResponseAlgorithm();
		
		response = algorithm.excecute(input);
		
		return response;
	}
	
	public void initDatabase(){
		
		if ( this.isDBStarted == false ){
			//inicia la bd si no esta iniciada.
			NeuralDataAccess.init();
			this.isDBStarted = true;
		}
		
	}
	
}
