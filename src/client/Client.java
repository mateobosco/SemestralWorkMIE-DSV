package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import chat.ChatLogger;
import chat.ChatServer;
import chat.LogicalClock;
import chat.LoginResponse;
import chat.Message;
import chat.ServerResponse;


public class Client {
	
	private int id = -1;
	private ChatServer chatServer;
	private String username;
	private ChatLogger logger;
	
	public Client(String username) {
		this.username = username;
		LogicalClock.getInstance().increment();
		logger = new ChatLogger("Client");
	}
	
	public void connect() throws RemoteException, NotBoundException{
		LogicalClock.getInstance().increment();
		logger.logClient("Connecting", this.id);
		
		String name = "ChatServer";
		Registry registry;
		registry = LocateRegistry.getRegistry("127.0.0.1", 2010);
		this.chatServer = (ChatServer) registry.lookup(name);
		
		LoginResponse response = chatServer.login(LogicalClock.getInstance().getTime());
		this.id = response.getId();
		LogicalClock.getInstance().increment(response.getLogicalTime());
		logger.logClient("Connected", this.id);
	}
	
	public void send(String input) throws RemoteException{
		LogicalClock.getInstance().increment();
		logger.logClient("Sending message", this.id);
		Message m = new Message(this.id, this.username, input);
		int logicalTime = chatServer.send(m);
		LogicalClock.getInstance().increment(logicalTime);
		logger.logClient("Message sent", this.id);
		return ;
	}
	
	public List<Message> receive(int from) throws RemoteException{
		LogicalClock.getInstance().increment();
		//logger.logClient(Level.INFO, "Receiving messages", this.id);
		ServerResponse response = chatServer.receive(id, from, LogicalClock.getInstance().getTime());
		LogicalClock.getInstance().increment(response.getLogicalTime());
		if (response.getMessages().size() > 0) logger.logClient("Messages received", this.id);
		
		return response.getMessages();
	}
	
	public void exit(){
		LogicalClock.getInstance().increment();
		logger.logClient("Disconnecting", this.id);
		try {
			int logicalTime = chatServer.logout(id, LogicalClock.getInstance().getTime());
			LogicalClock.getInstance().increment(logicalTime);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		logger.logClient("Disconnected", this.id);
		logger.close();
	}
}