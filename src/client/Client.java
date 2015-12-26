package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import shmemory.SharedMemory;


public class Client<T> {
	
	private int id;
	private SharedMemory<T> shMemory;
	
	public Client() { }
	
	@SuppressWarnings("unchecked")
	public void connect() throws RemoteException, NotBoundException{
		String name = "ShMemory";
		Registry registry;
		registry = LocateRegistry.getRegistry("127.0.0.1", 2010);
		shMemory = (SharedMemory<T>) registry.lookup(name);
		id = shMemory.login();
	}
	
	public T read() throws RemoteException{
		return shMemory.read(id);
	}
	
	public boolean write(T value) throws RemoteException{
		return shMemory.write(id, value);
	}
	
	public void exit(){
		try {
			shMemory.logout(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}