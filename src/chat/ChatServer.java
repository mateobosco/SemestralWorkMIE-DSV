package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface ChatServer extends Remote{
	
	public boolean send(Message m) throws RemoteException;
	public Vector<Message> receive(int id, int from) throws RemoteException;
	public int login() throws RemoteException;
	public boolean logout(int id) throws RemoteException;
}
