package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import chat.ChatServer;
import chat.LogicalClock;
import chat.LoginResponse;
import chat.Message;
import chat.ServerResponse;


public class Client {
	
	private int id;
	private ChatServer chatServer;
	private String username;
	
	public Client(String username) {
		this.username = username;
		LogicalClock.getInstance().increment();
	}
	
	public void connect() throws RemoteException, NotBoundException{
		LogicalClock.getInstance().increment();
		String name = "ChatServer";
		Registry registry;
		registry = LocateRegistry.getRegistry("127.0.0.1", 2010);
		this.chatServer = (ChatServer) registry.lookup(name);
		LoginResponse response = chatServer.login(LogicalClock.getInstance().getTime());
		this.id = response.getId();
		LogicalClock.getInstance().increment(response.getLogicalTime());
	}
	
	public void send(String input) throws RemoteException{
		LogicalClock.getInstance().increment();
		Message m = new Message(this.id, this.username, input);
		int logicalTime = chatServer.send(m);
		LogicalClock.getInstance().increment(logicalTime);
		return ;
	}
	
	public List<Message> receive(int from) throws RemoteException{
		LogicalClock.getInstance().increment();
		ServerResponse response = chatServer.receive(id, from, LogicalClock.getInstance().getTime());
		LogicalClock.getInstance().increment(response.getLogicalTime());
		return response.getMessages();
	}
	
	public void exit(){
		LogicalClock.getInstance().increment();
		try {
			int logicalTime = chatServer.logout(id, LogicalClock.getInstance().getTime());
			LogicalClock.getInstance().increment(logicalTime);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}