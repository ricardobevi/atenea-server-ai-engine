package org.squadra.atenea.aiengine;


import lombok.extern.log4j.Log4j;

import org.squadra.atenea.aiengine.algorithm.AbstractAlgorithm;
import org.squadra.atenea.aiengine.algorithm.VerySimpleResponseAlgorithm;
import org.squadra.atenea.data.server.NeuralDataAccess;

@Log4j
public class AIEngineFacade {
	
	private Boolean isDBStarted;
	
	public AIEngineFacade(){
		isDBStarted = false;
		this.initDatabase();
	}
	
	public Message execute(Message input) {
		
		log.debug("------------log AiEngine");
		Message response;
		
		this.initDatabase();
		
		AbstractAlgorithm algorithm = new VerySimpleResponseAlgorithm();
		
		response = algorithm.execute(input);
		
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
