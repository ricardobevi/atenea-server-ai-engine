package org.squadra.atenea.aiengine.algorithm;

import java.util.ArrayList;

import lombok.extern.log4j.Log4j;

import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.data.query.BasicQuery;

@Log4j
public class VerySimpleResponseAlgorithm  implements AbstractAlgorithm{

	public Message execute(Message inputMessage) {
		
		String inputText = inputMessage.getText().toLowerCase();
		
		Message response = new Message();
		
		if (inputText.contains("abrir") 
				|| inputText.contains("cerrar")
				|| inputText.contains("poner")
				|| inputText.contains("buscar")
				|| inputText.contains("comenzar")
				|| inputText.contains("escribir")) 
		{
			response.setType(Message.ORDER);
			response.setOrder(inputMessage.getText());
			response.setText("Entendido");
			
			System.out.println(inputText);
			
			return response;
		}
		
		BasicQuery query = new BasicQuery();
		ArrayList<String> responses = query.getRelatedWords( inputText );
		
		if ( responses != null && 
			 !responses.isEmpty() && 
			 !responses.get(0).isEmpty() )
		{
			response.setType(Message.ASSERTION);
			response.setText(responses.get(0));
		}
		else {
			response = new Message("Disculpa, no logre entenderte, por favor repitemelo.", Message.UNKNOWN);
		}
		
		
		
		return response;
	}

}
