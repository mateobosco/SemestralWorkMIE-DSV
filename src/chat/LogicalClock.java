package chat;

public class LogicalClock {

	int counter;
	private static LogicalClock INSTANCE = new LogicalClock();
	
	private LogicalClock(){
		this.counter = 0;
	}
	
	public static LogicalClock getInstance() {
		return INSTANCE;
	}
	
	public int getTime(){
		return this.counter;
	}
	
	public void increment(){
		this.counter ++;
	}
	
	public void increment(int otherTime){
		if (otherTime > this.counter){ this.counter = otherTime; }
		else {this.increment();}
	}
	
}
