package org.squadra.atenea.aiengine.algorithm;

import java.util.ArrayList;

import lombok.extern.log4j.Log4j;

import org.squadra.atenea.ateneacommunication.Message;
import org.squadra.atenea.base.word.Word;
import org.squadra.atenea.data.query.BasicQuery;
import org.squadra.atenea.wordclassifier.WordClassifier;

@Log4j
public class VerySimpleResponseAlgorithm  implements AbstractAlgorithm{

	public Message execute(Message inputMessage) {
		
		String inputText = inputMessage.getText().toLowerCase();
		
		Message response = new Message();
		
		//BasicQuery query = new BasicQuery();
		
		//ArrayList<String> responses = query.getRelatedWords( inputText );
		
//		if ( responses != null && 
//			 !responses.isEmpty() && 
//			 !responses.get(0).isEmpty() )
		{

			if (inputText.contains("abrir") || inputText.contains("cerrar")) 
			{
				response.setType(Message.ORDER);
				response.setOrder(inputText);
				System.out.println(inputText);
				
				response.setText("Entendido");
			}
			else {
				//response.setType(Message.ASSERTION);
				response = new Message("Disculpa, no logre entenderte, por favor repitemelo.", Message.UNKNOWN);
			}
			
			
//		} else {
//			response = new Message("Disculpa, no logre entenderte, por favor repitemelo.", Message.UNKNOWN);
		}
		
		return response;
	}

}
