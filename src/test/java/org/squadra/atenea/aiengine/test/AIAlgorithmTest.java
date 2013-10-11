package org.squadra.atenea.aiengine.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.squadra.atenea.aiengine.AIEngineFacade;
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
	
	public void searchResponse(Message message) {
		AIEngineFacade aiEngineFacade = new AIEngineFacade();
		Message output = aiEngineFacade.execute(message);
		System.out.println("RESPUESTA FINAL: " + output.getText());
	}
	
	@Test
	public void orderTest() {
		searchResponse(new Message("Abrir programa"));
		searchResponse(new Message("comenzar dictado"));
		searchResponse(new Message("Cerrar programa."));
		assertTrue(true);
	}
	
	@Test
	public void dialogTest() {
		searchResponse(new Message("Hola Atenea"));
		assertTrue(true);
	}

}
