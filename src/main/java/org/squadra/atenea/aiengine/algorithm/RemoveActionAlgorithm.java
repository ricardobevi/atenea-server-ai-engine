package org.squadra.atenea.aiengine.algorithm;

import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.base.actions.ListOfAction;

public class RemoveActionAlgorithm implements AbstractAlgorithm {

	@Override
	public Message execute(Message input) {
		ListOfAction.getInstance().removeAction(input.getText());
		ListOfAction.getInstance().writeToFile();
		return new Message();
	}

}
