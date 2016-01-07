package chat;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChatLogger {
	
	private String className;
	private PrintWriter writer;
	
	public ChatLogger(String name){
		className = name;		
		try {
			writer = new PrintWriter(className + ".log", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void logClient(String body, int clientId){
		this.log("Client" + clientId, body);
	}
	
	public void logServer(String body){
		this.log("Server", body);
	}
	
	private void log(String invoker, String body){
		int logicalTime = LogicalClock.getInstance().getTime();
		String datetime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String logicalTimeString = "Logical Time: " + logicalTime;
		String datetimeString = " | " + datetime;
		String finalBody = invoker + " : " + body + "(" + logicalTimeString + datetimeString + ")";
		
		writer.println(finalBody);
		writer.flush();
	}
	
	public void close(){
		writer.close();
	}

}
