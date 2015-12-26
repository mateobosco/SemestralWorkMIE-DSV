package shmemory;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedMemory<Type> extends Remote{
	
	public Type read() throws RemoteException;
	public boolean write(Type t) throws RemoteException;
	//public boolean login() throws RemoteException;
	//public boolean logout() throws RemoteExpcetion;
}
