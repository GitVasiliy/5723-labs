public class Main {
   public static void main(String args[]) {
      Object Lock2 = new Object();
      Object Lock1 = new Object();
      ThreadDemo1 T1 = new ThreadDemo1(Lock2, Lock1);
      ThreadDemo2 T2 = new ThreadDemo2(Lock2, Lock1);
      T1.start();
      T2.start();
   }
}


