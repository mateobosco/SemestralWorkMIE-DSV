package server;

import java.util.ArrayList;
import java.util.Collections;

public class Lamport{
    ArrayList<Integer> ticket;
	ArrayList<Boolean> entering;

    public Lamport() { 
    	ticket = new ArrayList<Integer>(Collections.nCopies(500, 0));
    	entering = new ArrayList<Boolean>(Collections.nCopies(500, false));
    }
    
    public void lock(int n, int id){
    	entering.set(id, true);
    	int max = 0;
    	for (int i = 0; i < n; i++){
    		int current = ticket.get(i);
			if (current > max){
				max = current;
			}
    	}
    	ticket.set(id, 1 + max); 
    	entering.set(id, false);
    	for (int i = 0; i < n; ++i) {
    		if (i != id) {
    			while (entering.get(i) == true) { 
    				Thread.yield(); 
    			}
    			while (ticket.get(i) != 0 && ( ticket.get(id) > ticket.get(i)  || 
    					( ticket.get(id) == ticket.get(i) && id > i))){ 
    				Thread.yield(); 
    			}
    		}
    	}
    	
    }
    
    public void unlock(int id){
    	ticket.set(id, 0);
    }
}
