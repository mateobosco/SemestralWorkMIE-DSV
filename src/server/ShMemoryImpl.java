package server;

import java.rmi.RemoteException;

import shmemory.SharedMemory;

public class ShMemoryImpl<Type> implements SharedMemory<Type> {
	
	private Type t;
	private int nClients;
	private int idCounter;
	private Lamport lamport;
		
	public ShMemoryImpl() {
		t = null;
		nClients = 0;
		idCounter = 0;
		lamport = new Lamport();
	}

	public boolean write(int id, Type t) throws RemoteException {
		lamport.lock(nClients, id);
		System.out.println("Cliente" + id +" toma el lock");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.t = t;
		lamport.unlock(id);
		System.out.println("Cliente" + id +" libera el lock");
		return true;
	}
	
	public Type read(int id) throws RemoteException{
		/*if (t == null){
			throw new NullPointerException("return value is null, try writing first");
		}*/
		return t;
	}

	@Override
	public int login() throws RemoteException {
		nClients ++;
		idCounter ++;
		return idCounter;
	}

	@Override
	public boolean logout(int id) throws RemoteException {
		nClients --;
		lamport.unlock(id);
		return true;
	}

}
