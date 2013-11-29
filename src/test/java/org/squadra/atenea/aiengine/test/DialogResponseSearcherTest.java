package org.squadra.atenea.aiengine.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.squadra.atenea.aiengine.responses.DialogResponseSearcher;
import org.squadra.atenea.aiengine.semantic.UserMessageType;
import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.data.server.NeuralDataAccess;

public class DialogResponseSearcherTest {

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
			System.out.println("RESPUESTA FINAL === " + DialogResponseSearcher.getRandomResponse(message, type));
		}
	}
	
	@Test
	public void saludo() {
		System.out.println("\n===================== SALUDO =======================\n");
		searchResponses(UserMessageType.Dialog.SALUDO, 5);
		assertTrue(true);
	}

	@Test
	public void preguntaEstadoAnimo() {
		System.out.println("\n============== PREGUNTA ESTADO ANIMO ===============\n");
		searchResponses(UserMessageType.Dialog.PREGUNTA_ESTADO_ANIMO, 5);
		assertTrue(true);
	}
	
	@Test
	public void preguntaNombre() {
		System.out.println("\n================ PREGUNTA NOMBRE ===================\n");
		searchResponses(UserMessageType.Dialog.PREGUNTA_NOMBRE, 5);
		assertTrue(true);
	}
	
	@Test
	public void preguntaEdad() {
		System.out.println("\n================= PREGUNTA EDAD ====================\n");
		searchResponses(UserMessageType.Dialog.PREGUNTA_EDAD, 5);
		assertTrue(true);
	}
	
	@Test
	public void preguntaQueHaces() {
		System.out.println("\n============== PREGUNTA QUE HACES ==================\n");
		searchResponses(UserMessageType.Dialog.PREGUNTA_QUE_HACES, 5);
		assertTrue(true);
	}
	
	@Test
	public void despedida() {
		System.out.println("\n================== DESPEDIDA =======================\n");
		searchResponses(UserMessageType.Dialog.DESPEDIDA, 10);
		assertTrue(true);
	}
	
}
