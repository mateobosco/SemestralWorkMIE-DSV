package shmemory;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SharedMemory<Type> extends Remote{
	
	public Type read(int id) throws RemoteException;
	public boolean write(int id, Type t) throws RemoteException;
	public int login() throws RemoteException;
	public boolean logout(int id) throws RemoteException;
}
