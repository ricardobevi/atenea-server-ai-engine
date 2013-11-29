package org.squadra.atenea.aiengine.responses.concept;

import java.util.ArrayList;

import org.squadra.atenea.base.word.Word;

import lombok.extern.log4j.Log4j;

/**
 * Esta clase se encarga de la búsqueda en la base conceptual y geneneración de objetos 
 * tipo ConceptSearchResult
 * @author ric
 */
@Log4j
public class ConceptSearch {

	ArrayList<String> concepts;
		

	public ConceptSearch() {
		concepts = new ArrayList<String>();
	}


	/**
	 * Agrega un nuevo concepto a la búsqueda.
	 * @param concept
	 * @author ric
	 */
	public void addSearchConcept(String concept){
		concepts.add(concept);
	}
	
	
	/**
	 * Agrega un nuevo concepto a la búsqueda.
	 * @param concept
	 * @author ric
	 */
	public void addSearchConcept(Word concept){
		concepts.add( concept.getBaseWord() );
	}
	

	/**
	 * Ejecuta la búsqueda en la base conceptual.
	 * @return
	 * @author ric
	 */
	public ArrayList<ConceptSearchResult> executeSearch(){
		ArrayList<ConceptSearchResult> conceptSearchResult = new ArrayList<ConceptSearchResult>();
		
		
		
		return conceptSearchResult;
	}
	

	/**
	 * Borra los conceptos por los que se buscó la vez anterior.
	 * @author ric
	 */
	public void cleanSearchConcepts(){
		
	}
	
}
