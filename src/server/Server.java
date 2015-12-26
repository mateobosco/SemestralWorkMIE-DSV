package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import shmemory.SharedMemory;


public class Server {
	
	public static void main(String[] args) {
		String name = "ShMemory";
		String name2 = "ShMemory2";
		
		try {
			SharedMemory<?> sh = new ShMemoryImpl<Object>();
			
			SharedMemory<?> stub = (SharedMemory<Object>) UnicastRemoteObject.exportObject(sh, 50000);
			
			Registry registry = LocateRegistry.createRegistry(2010);
			registry.rebind(name, sh);
			registry.rebind(name2, stub);
		}
		catch (Exception e) {
			System.err.println("Data exception: " + e.getMessage());
		}
	}
}