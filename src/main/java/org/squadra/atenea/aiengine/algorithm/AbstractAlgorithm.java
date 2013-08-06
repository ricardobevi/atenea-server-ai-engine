package org.squadra.atenea.aiengine.algorithm;

import org.squadra.atenea.ateneacommunication.Message;

public interface AbstractAlgorithm {
	public Message execute (Message input);
}
