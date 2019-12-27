public class Deadlock1 extends Thread{
	private Object lock1;
	private Object lock2;

	public Deadlock1(Object lock1, Object lock2){
		this.lock1 = lock1;
		this.lock2 = lock2;
	}

	public void run(){
		synchronized (lock1){
			System.out.println ("thread1");
			try {
          		Thread.sleep(500);
        	} catch (InterruptedException e) {}
        	System.out.println("Waiting thread1");
        	synchronized (lock2){
        		System.out.println ("No deadlock!");
        	}
		}
	}
}