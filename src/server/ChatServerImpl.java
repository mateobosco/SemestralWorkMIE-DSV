package server;

import java.rmi.RemoteException;
import java.util.Vector;

import chat.ChatServer;
import chat.LogicalClock;
import chat.LoginResponse;
import chat.Message;
import chat.ServerResponse;

public class ChatServerImpl implements ChatServer{

	private int nClients;
	private int idCounter;
	private Lamport lamport;
	private Vector<Message> messageVector;
	
	public ChatServerImpl() {
		nClients = 0;
		idCounter = 0;
		lamport = new Lamport();
		messageVector = new Vector<Message>();
		LogicalClock.getInstance().increment();
	}

	public int send(Message m) throws RemoteException {
		lamport.lock(nClients, m.getFrom());
		
		int len = messageVector.size();
		m.setId(len);
		messageVector.add(m);
		LogicalClock.getInstance().increment(m.getLogicalTime());
		
		System.out.println(m.getBody());
		
		lamport.unlock(m.getFrom());
		return LogicalClock.getInstance().getTime();
	}

	public ServerResponse receive(int id, int idLastMessage, int logicalTime) throws RemoteException {
		lamport.lock(nClients, id);	
		LogicalClock.getInstance().increment(logicalTime);
		Vector<Message> sublist;
		if (idLastMessage == 0){
			sublist = this.messageVector;
		}
		else if (idLastMessage < this.messageVector.size() || idLastMessage >= 0){
			sublist = new Vector<Message>(this.messageVector.subList(idLastMessage, this.messageVector.size()));
		}else{
			sublist = new Vector<Message>();
		}
		LogicalClock.getInstance().increment();
		ServerResponse response = new ServerResponse(sublist, LogicalClock.getInstance().getTime());
		lamport.unlock(id);	
		
		return response;
	}

	public LoginResponse login(int logicalTime) throws RemoteException {
		LogicalClock.getInstance().increment(logicalTime);
		nClients ++;
		idCounter ++;
		LoginResponse response = new LoginResponse(idCounter);
		
		return response;
	}

	public int logout(int id, int logicalTime) throws RemoteException {
		LogicalClock.getInstance().increment(logicalTime);
		nClients --;
		lamport.unlock(id);
		return LogicalClock.getInstance().getTime();
	}
	
}
