package org.squadra.atenea.aiengine.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.squadra.atenea.aiengine.responses.DialogResponseSearcher;
import org.squadra.atenea.aiengine.responses.UserMessageType;
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
		for (int i = 0; i < cant; i++) {
			System.out.println("RESPUESTA FINAL === " + DialogResponseSearcher.getRandomResponse(type));
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
	public void saludoYPreguntaEstadoAnimo() {
		System.out.println("\n========= SALUDA Y PREGUNTA ESTADO ANIMO ===========\n");
		searchResponses(UserMessageType.Dialog.SALUDO_PREGUNTA_ESTADO_ANIMO, 5);
		assertTrue(true);
	}
	
}
