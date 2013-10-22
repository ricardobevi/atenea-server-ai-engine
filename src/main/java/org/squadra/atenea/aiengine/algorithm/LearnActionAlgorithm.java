package org.squadra.atenea.aiengine.algorithm;

import java.util.ArrayList;

import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.base.actions.Click;
import org.squadra.atenea.base.actions.ListOfAction;

public class LearnActionAlgorithm implements AbstractAlgorithm {

	@Override
	public Message execute(Message input) {

		ArrayList <Click> clicks = new ArrayList<Click>();
		System.out.println("Empiezo a deserializar");
		System.out.println(input.getIcons().size());
		for (String icon : input.getIcons()) {
			System.out.println("recibi: " + icon);
			clicks.add( Click.deserialize(icon));
		}
		System.out.println("Termino de deserializar");
		ListOfAction.getInstance().addAction(input.getText() , clicks);
		ListOfAction.getInstance().writeToFile();
		System.out.println("Termino de hacer todo :'D");
		return new Message("genial, ahora conozco esta accion");
	}

}
