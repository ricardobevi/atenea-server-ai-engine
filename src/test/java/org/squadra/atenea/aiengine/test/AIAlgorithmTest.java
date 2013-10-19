package org.squadra.atenea.aiengine.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.squadra.atenea.aiengine.AIEngineFacade;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.data.server.NeuralDataAccess;

public class AIAlgorithmTest {

	private static AIEngineFacade aiEngineFacade;
	
	@BeforeClass
	public static void initDataBase() {
		aiEngineFacade = new AIEngineFacade();
		}
	
	@AfterClass
	public static void stopDataBase() {
		NeuralDataAccess.stop();
	}
	
	public void searchResponse(Message message) {
		Message output = aiEngineFacade.execute(message);
		System.out.println("======= MENSAJE ENTRANTE: " + message.getText());
		System.out.println("======= RESPUESTA FINAL: " + output.getText());
		System.out.println("======= " + output.getMetadata("orden_desconocida"));
		
	}
	
	//@Test
	public void orderTest() {
		searchResponse(new Message("Abrir programa"));
		searchResponse(new Message("comenzar dictado"));
		searchResponse(new Message("Cerrar programa"));
		assertTrue(true);
	}
	
	@Test
	public void dialogTest() {
		searchResponse(new Message("Hola Atenea"));
		searchResponse(new Message("hola Atenea saludame"));
		searchResponse(new Message("Buenos días Atenea"));
		searchResponse(new Message("Buenos dias Atenea"));
		searchResponse(new Message("Atenea buenas noches"));
		searchResponse(new Message("Facundo"));
		searchResponse(new Message("Cómo estás"));
		searchResponse(new Message("Como andas Atenea"));
		searchResponse(new Message("qué onda"));
		searchResponse(new Message("hola Atenea cómo estás"));
		searchResponse(new Message("Nos vemos"));
		searchResponse(new Message("Chau Atenea"));
		searchResponse(new Message("adios"));
		searchResponse(new Message("cual es tu nombre"));
		searchResponse(new Message("Cómo te llamás"));
		searchResponse(new Message("Quién sos"));
		searchResponse(new Message("Cuántos años tenés"));		assertTrue(true);
	}

}
