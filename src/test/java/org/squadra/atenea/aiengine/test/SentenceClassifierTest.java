package org.squadra.atenea.aiengine.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.squadra.atenea.data.server.Neo4jServer;
import org.squadra.atenea.data.server.NeuralDataAccess;
import org.squadra.atenea.parser.model.SimpleSentence;

public class SentenceClassifierTest {

	@BeforeClass
	public static void initDataBase() {
		NeuralDataAccess.init();
	}
	
	@AfterClass
	public static void stopDataBase() {
		NeuralDataAccess.stop();
	}
	
	@Test
	public void saludo() {
		
		ArrayList<SimpleSentence> sentences = Neo4jServer.dialogCache;
		
		for (SimpleSentence sentence : sentences) {
		 			System.out.println(sentence.toString());
		}
		assertTrue(true);
	}

}
