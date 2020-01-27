public class ThreadDemo1 extends Thread {
   private Object lock1 = new Object();
   private Object lock2 = new Object();
   public ThreadDemo1 (Object lock1, Object lock2) {
      this.lock1 = lock1;
      this.lock2 = lock2;
   }
   public void run() {
      synchronized (lock1) {
         System.out.println("Thread 1: Holding lock 1...");

         try { Thread.sleep(10); }
         catch (InterruptedException e) {}
         System.out.println("Thread 1: Waiting for lock 2...");

         synchronized (lock2) {
            System.out.println("Thread 1: Holding lock 1 & 2...");
         }
      }
   }
}