public class Deadlock2 extends Thread{
	private Object lock1;
	private Object lock2;
	
	public Deadlock2(Object lock1, Object lock2){
		this.lock1 = lock1;
		this.lock2 = lock2;
	}

	public void run(){
		synchronized (lock2){
			System.out.println ("thread2");
			try {
          		Thread.sleep(500);
        	} catch (InterruptedException e) {}
        	System.out.println("Waiting thread2");
        	synchronized (lock1){
        		System.out.println ("No deadlock!");
        	}
		}
	}
}