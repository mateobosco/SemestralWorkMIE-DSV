package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import chat.ChatServer;
import chat.Message;


public class Client {
	
	private int id;
	private ChatServer chatServer;
	private String username;
	
	public Client(String username) {
		this.username = username;
	}
	
	public void connect() throws RemoteException, NotBoundException{
		String name = "ChatServer";
		Registry registry;
		registry = LocateRegistry.getRegistry("127.0.0.1", 2010);
		chatServer = (ChatServer) registry.lookup(name);
		id = chatServer.login();
	}
	
	public boolean send(String input) throws RemoteException{
		Message m = new Message(this.id, this.username, input);
		return chatServer.send(m);
	}
	
	public List<Message> receive(int from) throws RemoteException{
		return chatServer.receive(id, from);
	}
	
	public void exit(){
		try {
			chatServer.logout(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}