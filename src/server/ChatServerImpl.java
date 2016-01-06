package server;

import java.rmi.RemoteException;
import java.util.Vector;

import chat.ChatServer;
import chat.Message;

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
	}

	public boolean send(Message m) throws RemoteException {
		lamport.lock(nClients, m.getFrom());
		
		int len = messageVector.size();
		m.setId(len);
		messageVector.add(m);
		
		System.out.println(m.getBody());
		
		lamport.unlock(m.getFrom());
		return true;
	}

	public Vector<Message> receive(int id, int idLastMessage) throws RemoteException {
		lamport.lock(nClients, id);	
		Vector<Message> sublist;
		if (idLastMessage == 0){
			sublist = this.messageVector;
		}
		else if (idLastMessage < this.messageVector.size() || idLastMessage >= 0){
			sublist = new Vector<Message>(this.messageVector.subList(idLastMessage, this.messageVector.size()));
		}else{
			sublist = new Vector<Message>();
		}
		
		
		lamport.unlock(id);		
		
		return sublist;
	}

	public int login() throws RemoteException {
		nClients ++;
		idCounter ++;
		return idCounter;
	}

	public boolean logout(int id) throws RemoteException {
		nClients --;
		lamport.unlock(id);
		return true;
	}
	
}
