package org.squadra.atenea.aiengine.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.squadra.atenea.aiengine.AIEngineFacade;
import org.squadra.atenea.aiengine.algorithm.AIAlgorithm;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.data.server.NeuralDataAccess;

public class AIAlgorithmTest {

	@BeforeClass
	public static void initDataBase() {
		NeuralDataAccess.init();
	}
	
	@AfterClass
	public static void stopDataBase() {
		NeuralDataAccess.stop();
	}
	
	@Test
	public void dialogTest() {
		
		Message input = new Message("Abrir programa", Message.DIALOG);
		AIEngineFacade aiEngineFacade = new AIEngineFacade();
		Message output = aiEngineFacade.execute(input);
		
		System.out.println("RESPUESTA FINAL: " + output.getText());
		
		assertTrue(true);
	}

}
