package org.squadra.atenea.aiengine.algorithm;

import java.util.ArrayList;

import org.squadra.atenea.aiengine.Message;
import org.squadra.atenea.data.query.BasicQuery;

public class VerySimpleResponseAlgorithm  implements AbstractAlgorithm{

	public Message excecute(Message input) {
		Message response = new Message("");
		
		BasicQuery query = new BasicQuery();
		
		ArrayList<String> responses = query.getRelatedWords( input.getText().toLowerCase() );
		
		if ( responses != null && 
			 !responses.isEmpty() && 
			 !responses.get(0).isEmpty()  ){
			
			response.setText( responses.get(0) );
			
		} else {
			
			response.setText( "Disculpa, no logre entenderte, por favor repitemelo." );
			
		}
		
		return response;
	}

}
