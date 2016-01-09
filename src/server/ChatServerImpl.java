package server;

import java.rmi.RemoteException;
import java.util.Vector;

import chat.ChatLogger;
import chat.ChatServer;
import chat.LogicalClock;
import chat.LoginResponse;
import chat.Message;
import chat.ServerResponse;

public class ChatServerImpl implements ChatServer{

	private int nClients;
	private int idCounter;
	private volatile Lamport lamport;
	private Vector<Message> messageVector;
	private ChatLogger logger;
	
	public ChatServerImpl() {
		nClients = 0;
		idCounter = 0;
		lamport = new Lamport();
		messageVector = new Vector<Message>();
		logger = new ChatLogger("Server");
		LogicalClock.getInstance().increment();
		logger.logServer("Started");
	}

	public int send(Message m) throws RemoteException {
		lamport.lock(nClients, m.getFrom());
		
		int len = messageVector.size();
		m.setId(len);
		messageVector.add(m);
		LogicalClock.getInstance().increment(m.getLogicalTime());
		logger.logServer("Message received from client" + m.getFrom() );
		
		System.out.println(m.getBody());
		
		lamport.unlock(m.getFrom());
		return LogicalClock.getInstance().getTime();
	}

	public ServerResponse receive(int id, int idLastMessage, int logicalTime) throws RemoteException {
		lamport.lock(nClients, id+1);	
		LogicalClock.getInstance().increment(logicalTime);
		logger.logServer("Request received from client" + id );
		Vector<Message> sublist;
		if (idLastMessage == 0){
			sublist = this.messageVector;
		}
		else if (idLastMessage < this.messageVector.size() || idLastMessage >= 0){
			Vector<Message> sub = new Vector<Message>(this.messageVector.subList(idLastMessage, this.messageVector.size()));
			sublist = new Vector<Message>();
			int size = sub.size();
			for (int i = 0; i < size; i ++){
				sublist.add(sub.get(i));
			}
		}else{
			sublist = new Vector<Message>();
		}
		LogicalClock.getInstance().increment();
		ServerResponse response = new ServerResponse(sublist, LogicalClock.getInstance().getTime());
		logger.logServer("Request replied to client" + id );
		lamport.unlock(id+1);	
		
		return response;
	}

	public LoginResponse login(int logicalTime) throws RemoteException {
		LogicalClock.getInstance().increment(logicalTime);
		logger.logServer("Login requested from client");
		nClients += 2;
		idCounter += 2;
		LoginResponse response = new LoginResponse(idCounter);
		logger.logServer("Login request replied to client new number " + idCounter);
		
		return response;
	}

	public int logout(int id, int logicalTime) throws RemoteException {
		LogicalClock.getInstance().increment(logicalTime);
		logger.logServer("Logout request from client" + id);
		nClients -= 2;
		lamport.unlock(id);
		logger.logServer("Logout request replied to client" + id);
		return LogicalClock.getInstance().getTime();
	}
	
}
