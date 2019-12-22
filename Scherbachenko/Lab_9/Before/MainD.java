public class MainD
{
	public static void main(String[] args){
		Object lock1 = new Object();
		Object lock2 = new Object();
		Deadlock1 thr1 = new Deadlock1(lock1, lock2);
		Deadlock2 thr2 = new Deadlock2(lock1, lock2);
		thr1.start();
		thr2.start();
	}
}