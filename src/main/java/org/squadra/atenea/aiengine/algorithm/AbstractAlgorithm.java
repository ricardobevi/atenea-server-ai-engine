package org.squadra.atenea.aiengine.algorithm;

import org.squadra.atenea.ateneacommunication.Message;

public interface AbstractAlgorithm {
	
	/**
	 * Ejecuta el algoritmo de inteligencia artificial.
	 * @param input mensaje de entrada recibido del cliente
	 * @return mensaje de salida devuelto por el servidor
	 */
	public Message execute (Message input);
}
