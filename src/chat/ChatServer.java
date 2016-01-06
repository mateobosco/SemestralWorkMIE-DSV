package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote{
	
	public int send(Message m) throws RemoteException;
	public ServerResponse receive(int id, int from, int logicalTime) throws RemoteException;
	public LoginResponse login(int logicalTime) throws RemoteException;
	public int logout(int id, int logicalTime) throws RemoteException;
}
