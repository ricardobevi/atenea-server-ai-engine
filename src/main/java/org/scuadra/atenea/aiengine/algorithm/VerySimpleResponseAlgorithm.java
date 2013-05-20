package org.scuadra.atenea.aiengine.algorithm;

import java.util.ArrayList;

import org.atenea.data.query.BasicQuery;
import org.scuadra.atenea.aiengine.Message;

public class VerySimpleResponseAlgorithm  implements AbstractAlgorithm{

	public Message excecute(Message input) {
		Message response = new Message("");
		
		BasicQuery query = new BasicQuery();
		
		ArrayList<String> responses = query.getRelatedWords(input.getText());
		
		if ( responses != null && !responses.isEmpty() ){
			response.setText( responses.get(0) );
		}
		
		return response;
	}

}
