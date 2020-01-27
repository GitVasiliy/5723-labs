// thread started

public class TestThread {
   public static Object Lock1 = new Object();
   public static Object Lock2 = new Object();

   public static void main(String args[]) {
      ThreadDemo1 T1 = new ThreadDemo1();
      ThreadDemo2 T2 = new ThreadDemo2();
      T1.start();
      T2.start();
   }

   private static class ThreadDemo1 extends Thread {
      public void run() {
         synchronized (Lock1) {
            System.out.println("Thread 1: Holding lock 1...");

            try { Thread.sleep(10); }
            catch (InterruptedException e) {}
            System.out.println("Thread 1: Waiting for lock 2...");

            synchronized (Lock2) {
               System.out.println("Thread 1: Holding lock 1 & 2...");
            }
         }
      }
   }
   private static class ThreadDemo2 extends Thread {
      public void run() {
         synchronized (Lock2) {
            System.out.println("Thread 2: Holding lock 2...");

            try { Thread.sleep(10); }
            catch (InterruptedException e) {}
            System.out.println("Thread 2: Waiting for lock 1...");

            synchronized (Lock1) {
               System.out.println("Thread 2: Holding lock 1 & 2...");
            }
         }
      }
   } 
}

/*public class Deadlock1 
{
    static class Thread 
	{
        private final String name;
        public Thread(String name) 
		{
            this.name = name;
        }
        public String getName() 
		{
            return this.name;
        }
        public synchronized void thread1(Thread status) 
		{
            System.out.printf("%s:" + "  thread waiting for", this.name, status.getName());
        }
        public synchronized void thread2(Thread status) 
		{
            System.out.printf("%s:" + "  thread waiting for", this.name, status.getName());
        }
    }    
    public static void main(String[] args) 
	{
        final Thread thread1 = new Thread("Person1");
        final Thread thread2 = new Thread("Person2");
        
        new Thread(new Runnable() {
            public void run()
                {
                    thread1.talk(thread2);
                }
        }).start();
        new Thread(new Runnable() {
            public void run()
                {
                    thread2.talk(thread1);
                }
        }).start();
    }
}*/
