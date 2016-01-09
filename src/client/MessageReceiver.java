package client;

import java.awt.TextArea;
import java.util.Iterator;
import java.util.List;

import chat.Message;

public class MessageReceiver implements Runnable{

	private Client client;
	private TextArea messageArea;
	private int lastMessage;
	
	public MessageReceiver(Client c, TextArea messageArea){
		this.client = c;
		this.messageArea = messageArea;
		this.lastMessage = 0;
	}

	@Override
	public void run() {
		while (true){
			try {
				Thread.sleep(100);
				List<Message> messages = this.client.receive(lastMessage);
				if (messages == null) return;
				
				Iterator<Message> i = messages.iterator();
				while (i.hasNext()){
					Message m = i.next();
					@SuppressWarnings("deprecation")
					String time = m.getDate().getHours() + ":" + m.getDate().getMinutes();
					this.messageArea.append(m.getUsername() + " (" + time + ")> " + m.getBody() + "\n" );
				}
				this.lastMessage += messages.size();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
}
