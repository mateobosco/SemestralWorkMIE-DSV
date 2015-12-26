package server;

import java.rmi.RemoteException;

import shmemory.SharedMemory;

public class ShMemoryImpl<Type> implements SharedMemory<Type> {
	
	private Type t;
		
	public ShMemoryImpl() {
	}

	public boolean write(Type t) throws RemoteException {
		this.t = t;
		return true;
	}
	
	public Type read() throws RemoteException{
		return t;
	}

}
