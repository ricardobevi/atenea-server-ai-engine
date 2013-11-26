package org.squadra.atenea.aiengine.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.squadra.atenea.data.query.ConceptQuery;
import org.squadra.atenea.data.query.result.ConceptQueryResultCollection;
import org.squadra.atenea.data.server.NeuralDataAccess;

public class ConceptSearchTest {

	@BeforeClass
	public static void init(){
		NeuralDataAccess.init("/home/ric/Documentos/Universidad/Proyecto/workspace/AteneaDataLoader/graphDB");
		
	}
	
	@AfterClass
	public static void end(){
		NeuralDataAccess.stop();
	}
	
	@Test
	public void basicTest() {
		
		ConceptQuery query = new ConceptQuery();
		
		query.addConcept("");
		
		query.conceptSearch();
		
		assertTrue(true);
	}
	
	@Test
	public void motherTest() {
		
		ConceptQuery query = new ConceptQuery();
		
		query.addConcept("madre");
		query.addConcept("san martín");
		
		ConceptQueryResultCollection  conceptQueryResultCollection = query.conceptSearch(4);
		
		System.out.println(conceptQueryResultCollection.getTopResult());
		
		assertTrue(true);
	}

	@Test
	public void simonTest() {
		
		ConceptQuery query = new ConceptQuery();
		
		query.addConcept("guayaquil");
		query.addConcept("encontrar");
		query.addConcept("josé de san martín");
		
		ConceptQueryResultCollection  conceptQueryResultCollection = query.conceptSearch(4);
		
		System.out.println(conceptQueryResultCollection.getTopResult());
		
		assertTrue(true);
	}

	@Test
	public void sanTest() {
		
		ConceptQuery query = new ConceptQuery();
		
		query.addConcept("ser");
		query.addConcept("josé de san martín");
				
		ConceptQueryResultCollection  conceptQueryResultCollection = query.conceptSearch(4);
		
		System.out.println(conceptQueryResultCollection.getTopResult());
		
		assertTrue(true);
	}
	
	@Test
	public void guayaquilTest() {
		
		ConceptQuery query = new ConceptQuery();
		
		query.addConcept("estar");
		query.addConcept("guayaquil");
						
		ConceptQueryResultCollection  conceptQueryResultCollection = query.conceptSearch(4);
		
		System.out.println(conceptQueryResultCollection.getTopResult());
		
		assertTrue(true);
	}
	
	
	@Test
	public void libertoTest() {
		
		ConceptQuery query = new ConceptQuery();
		
		query.addConcept("argentina");
		query.addConcept("chile");
		query.addConcept("perú");
		query.addConcept("independencia");
				
		ConceptQueryResultCollection  conceptQueryResultCollection = query.conceptSearch(3);
		
		System.out.println(conceptQueryResultCollection.getTopResult());
		
		assertTrue(true);
	}
	
}
