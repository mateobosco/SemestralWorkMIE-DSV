package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import chat.Message;

public class Program {
	
	public static void main(String args[]) {
		
		interactive();
	}
	
	static void interactive(){
		final Client c = new Client("pepe");
		try {
			c.connect();
		} catch (RemoteException | NotBoundException e1) {
			System.out.println("Impossible to connect with the server");
			return;
		}
		while (true){
			receiveMessages(c);
			getMessage(c);
		}
	}

	private static void receiveMessages(final Client c) {
		Thread one = new Thread() {
		    public void run() {
		    	int lastMessage = 0;
		        for (int i = 0 ; i < 100 ; i++){
		        	try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		        	try {
						List<Message> messages = c.receive(lastMessage);
						showMessages(messages);
						lastMessage += messages.size();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
		    }  
		};
		one.start();
	}

	protected static void showMessages(List<Message> messages) {
		for(int i = 0; i < messages.size(); i++){
			Message m = messages.get(i);
			System.out.print(m.getBody());
		}
	}

	private static void getMessage(Client c) {
		String input = getString();
		while (input != "exit"){
			try {
				c.send(input);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			input = getString();
		}
		c.exit();
	}
	
	private static String getString(){
		System.out.print("Message: ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input;
			
		try {
			while((input=br.readLine())!=null){
				return input;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
