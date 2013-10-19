package org.squadra.atenea.aiengine.algorithm;

import java.util.ArrayList;

import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.base.actions.Click;
import org.squadra.atenea.base.actions.ListOfAction;

public class LearnActionAlgorithm implements AbstractAlgorithm {

	@Override
	public Message execute(Message input) {

		ArrayList <Click> clicks = new ArrayList<Click>();
		
		for (String icon : input.getIcons()) {
			clicks.add( Click.deserialize(icon));
		}
		
		ListOfAction.getInstance().addAction(input.getText() , clicks);
		ListOfAction.getInstance().writeToFile();
		
		return new Message("genial, ahora conozco esta accion");
	}

}
