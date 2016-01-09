package client;

import java.net.InetAddress;
import java.util.Iterator;
import java.util.List;

import chat.Message;

public class BatchThread implements Runnable{
	
	public void run() {
		
		Client c = new Client("jose");
		System.out.println("Connected!");
		try {
			c.connect();
			int lastMessageId = 0;
			for (int i = 0; i < 10000; i++){
				c.send(i + " hello from " + InetAddress.getLocalHost().getHostAddress());
				
				List<Message> messages = c.receive(lastMessageId);
				
				Iterator<Message> iterator = messages.iterator();
				while (iterator.hasNext()){
					Message m = iterator.next();
					System.out.println(m.getBody());
				}
				lastMessageId += messages.size();
			}
			c.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
