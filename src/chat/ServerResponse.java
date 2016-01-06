package chat;

import java.io.Serializable;
import java.util.Vector;

public class ServerResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Message> messages;
	private int logicalTime;
	
	public ServerResponse(Vector<Message> messages, int logicalTime){
		this.messages = messages;
		this.logicalTime = logicalTime;
	}
	
	public Vector<Message> getMessages(){
		return this.messages;
	}
	
	public int getLogicalTime(){
		return this.logicalTime;
	}
	
}
