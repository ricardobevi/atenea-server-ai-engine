package org.scuadra.atenea.aiengine;

import java.util.HashMap;

public class Message {
	
	private String text;
	private HashMap<String, String> metadata;
	
	public Message(String text){
		
		this.text = text;
		this.metadata = new HashMap<String, String>();
		
	}
	
	public void addMetadata (String key, String value){
		this.metadata.put(key, value);
	}
	
	public String getMetadata (String key){
		return this.metadata.get(key);
	}
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public HashMap<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(HashMap<String, String> metadata) {
		this.metadata = metadata;
	}
	
	

	
}
