package chat;

import java.io.Serializable;

public class LoginResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int logicalTime;
	
	public LoginResponse(int id){
		this.id = id;
		this.logicalTime = LogicalClock.getInstance().getTime();
	}
	
	public int getId() {
		return id;
	}
	
	public int getLogicalTime() {
		return logicalTime;
	}

}
