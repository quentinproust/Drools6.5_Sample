package drools.sample.model;

import java.text.MessageFormat;

public class Message {

	private String messageErreur;
	
	public Message(String messageErreur) {
		this.messageErreur = messageErreur;
	}
	
	public Message(String messageErreur, Object... params) {
		this.messageErreur = MessageFormat.format(messageErreur, (Object[])params);
	}

	@Override
	public String toString() {
		return "Message : " + messageErreur;
	}
}
