package deadlockPack;

public class Thread2 extends Thread{
    public  Object Lock1 = new Object();
    public  Object Lock2 = new Object();

    public Thread2(Object Lock1, Object Lock2){
        this.Lock1 = Lock1;
        this.Lock2 = Lock2;
    }
    public void run(){
        synchronized (Lock2){
            System.out.println("Thread 2: Holding lock 2...");

            try{Thread.sleep(0);}
            catch(InterruptedException e){}
            System.out.println("Thread 2: Waiting for lock 1...");

            synchronized (Lock1){
                System.out.println("Thread 2: Holding lock 1 & 2...");
            }
        }
    }
}
