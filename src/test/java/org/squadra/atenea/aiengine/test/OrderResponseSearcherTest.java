package org.squadra.atenea.aiengine.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.squadra.atenea.aiengine.responses.OrderResponseSearcher;
import org.squadra.atenea.aiengine.semantic.UserMessageType;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.data.server.NeuralDataAccess;

public class OrderResponseSearcherTest {

	@BeforeClass
	public static void initDataBase() {
		NeuralDataAccess.init();
	}
	
	@AfterClass
	public static void stopDataBase() {
		NeuralDataAccess.stop();
	}
	
	public void searchResponses(String type, int cant) {
		Message message = new Message("Ejemplo");
		for (int i = 0; i < cant; i++) {
			System.out.println("RESPUESTA FINAL === " + OrderResponseSearcher.getRandomResponse(message, type));
		}
	}
	
	@Test
	public void ordenConocida() {
		System.out.println("\n================= ORDEN CONOCIDA ===============\n");
		searchResponses(UserMessageType.Order.ORDEN_CONOCIDA, 8);
		assertTrue(true);
	}
	
	@Test
	public void ordenDesconocida() {
		System.out.println("\n=============== ORDEN DESCONOCIDA ==============\n");
		searchResponses(UserMessageType.Order.ORDEN_DESCONOCIDA, 8);
		assertTrue(true);
	}

}
