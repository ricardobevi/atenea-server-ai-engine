package org.squadra.atenea.aiengine.responses;

import org.squadra.atenea.data.query.DialogQuery;

public class AssertionAndDefaultSearcher {

	/**
	 * Obtiene de la base de datos una respuesta aleatoria segun el tipo buscado.
	 * @param type Tipo de respuesta que busca Atenea
	 * @return Respuesta
	 */
	public static String getResponseByType(String type) {
		
		DialogQuery dq = new DialogQuery();
		return dq.findRandomSentenceByDialogType("defaultType", type).toString();
	}
	
}
