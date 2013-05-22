package org.scuadra.atenea.aiengine.algorithm;

import java.util.ArrayList;

import org.atenea.data.query.BasicQuery;
import org.scuadra.atenea.aiengine.Message;

public class VerySimpleResponseAlgorithm  implements AbstractAlgorithm{

	public Message excecute(Message input) {
		Message response = new Message("");
		
		BasicQuery query = new BasicQuery();
		
		ArrayList<String> responses = query.getRelatedWords( input.getText().toLowerCase() );
		
		if ( responses != null && !responses.isEmpty() ){
			response.setText( responses.get(0) );
		} else {
			response.setText( "Disculpa, no logre entenderte, por favor repitemelo." );
		}
		
		return response;
	}

}
