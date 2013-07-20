package org.squadra.atenea.aiengine.algorithm;

import java.util.ArrayList;

import org.squadra.atenea.aiengine.Message;
import org.squadra.atenea.data.query.BasicQuery;

public class VerySimpleResponseAlgorithm  implements AbstractAlgorithm{

	public Message execute(Message inputMessage) {
		
		String inputText = inputMessage.getText();
		
		Message response = new Message();
		
		BasicQuery query = new BasicQuery();
		
		ArrayList<String> responses = query.getRelatedWords( inputText.toLowerCase() );
		
		if ( responses != null && 
			 !responses.isEmpty() && 
			 !responses.get(0).isEmpty() ){
			
			response = new Message(responses.get(0), Message.ASSERTION);
			
		} else {
			response = new Message("Disculpa, no logre entenderte, por favor repitemelo.", Message.UNKNOWN);
		}
		
		return response;
	}

}
