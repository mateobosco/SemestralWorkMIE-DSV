package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Program {
	
	
	
	public static void main(String args[]) {
		
		interactive();
		
		/*Client<Integer> c = new Client<Integer>();
		c.connect();
		
		try {
			System.out.println("Client: " + c.write(2));
			System.out.println("Client: " + c.read());
		}
		catch (Exception e) {
			System.err.println("Exception: " + e.toString());
		}*/
	}
	
	static void interactive(){
		Client<Integer> c = new Client<Integer>();
		try {
			c.connect();
		} catch (RemoteException | NotBoundException e1) {
			System.out.println("Impossible to connect with the server");
			return;
		}
		while (true){
			System.out.println("1 - Read value");
			System.out.println("2 - Write value");
			System.out.println("3 - Exit");
			System.out.print("Select the desired option: ");
			Scanner in = new Scanner(System.in);
			int num = in.nextInt();
			try{
				if (num == 1){
					System.out.println("The value is " + c.read());
				}
				else if (num == 2){
					System.out.print("Enter the desired value: ");
					num = in.nextInt();
					if (c.write(num)) System.out.println("The value was successfully written");
					else System.out.println("There was an error writing the value");
				}
				else if (num == 3){
					c.exit();
					System.out.println("Goodbye");
					return;
				}
			}catch(RemoteException e){
				e.printStackTrace();
			}
			System.out.println("---------------------------------------------");
		}
	}

}
