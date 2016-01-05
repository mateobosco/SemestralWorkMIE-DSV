package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import chat.ChatServer;

public class Server {
	
	public static void main(String[] args) {
		String name = "ChatServer";
		String name2 = "ChatServer2";
		
		try {
			ChatServerImpl chatServer = new ChatServerImpl();
			
			ChatServer stub = (ChatServer) UnicastRemoteObject.exportObject(chatServer, 50000);
			
			Registry registry = LocateRegistry.createRegistry(2010);
			registry.rebind(name, chatServer);
			registry.rebind(name2, stub);
		}
		catch (Exception e) {
			System.err.println("Data exception: " + e.getMessage());
		}
	}
}