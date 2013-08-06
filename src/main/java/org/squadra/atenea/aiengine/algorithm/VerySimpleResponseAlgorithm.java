package org.squadra.atenea.aiengine.algorithm;

import java.util.ArrayList;

import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.data.query.BasicQuery;

public class VerySimpleResponseAlgorithm  implements AbstractAlgorithm{

	public Message execute(Message inputMessage) {
		
		String inputText = inputMessage.getText().toLowerCase();
		
		Message response = new Message();
		
		BasicQuery query = new BasicQuery();
		
		ArrayList<String> responses = query.getRelatedWords( inputText );
		
		if ( responses != null && 
			 !responses.isEmpty() && 
			 !responses.get(0).isEmpty() ){
			
			if (inputText.contains("abrir") || inputText.contains("cerrar")) {
				response.setType(Message.ORDER);
				switch (inputText) {
					case "abrir bloc de notas":
						response.setOrder("notepad.exe");
						break;
					case "cerrar bloc de notas":
						response.setOrder("taskkill /IM notepad.exe");
						break;
					case "abrir panel de control":
						response.setOrder("control");
						break;
					case "abrir administrador de tareas":
						response.setOrder("taskmgr");
						break;
				}
			}
			else {
				response.setType(Message.ASSERTION);
			}
			response.setText(responses.get(0));
			
		} else {
			response = new Message("Disculpa, no logre entenderte, por favor repitemelo.", Message.UNKNOWN);
		}
		
		return response;
	}

}
