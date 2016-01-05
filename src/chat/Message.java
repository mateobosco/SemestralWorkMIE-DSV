package chat;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int from;
	private String body;
	private Date date;
	private int id;
	
	public Message(int from, String body){
		this.from = from;
		this.body = body;
		this.date = new Date();
	}
	
	public int getFrom(){
		return this.from;
	}
	
	public String getBody(){
		return this.body;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
}
