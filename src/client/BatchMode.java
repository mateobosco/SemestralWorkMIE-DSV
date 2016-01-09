package client;

public class BatchMode {
	
	public static void main(String[] args){

		for (int i = 0; i < 10 ; i++){
			Thread clientThread = new Thread(new BatchThread());
			clientThread.start();
		}
		
	}

}
