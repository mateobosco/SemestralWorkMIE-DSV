package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import shmemory.SharedMemory;


public class Client {
	public static void main(String args[]) {
		try {
			String name = "ShMemory";
			Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2010);
			@SuppressWarnings("unchecked")
			SharedMemory<Integer> shMemory = (SharedMemory<Integer>) registry.lookup(name);
			int id = shMemory.login();
			System.out.println("Client: " + shMemory.write(id, 2));
			System.out.println("Client: " + shMemory.read(id));
		}
		catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}
	}
}