import java.lang.management.*;

public class Test {

	static class Cat{
		private String name;
		
		public Cat(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public synchronized void noise(Cat cat) {
			System.out.println("Cat with name " + this.getName() + " says first: MEOW!");
			cat.noiseBack(this);
		}
		
		public synchronized void noiseBack(Cat cat) {
			System.out.println("Cat with name " + this.getName() + " says back: MEOW!");
		}
		
	}
	
	
	public static void main(String[] args) {
		Cat cat1 = new Cat("Danya");
		Cat cat2 = new Cat("Ira");
		
		System.out.println("privet ya gotov k rabote");
		
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				cat1.noise(cat2);
			}
		});
		
		Thread thread2 =new Thread(new Runnable() {
			@Override
			public void run() {
				cat2.noise(cat1);
			}
		});

		thread1.start();
		thread2.start();

		ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
		long[] ids = tmx.findDeadlockedThreads();
		if (ids != null) {
	 		ThreadInfo[] infos = tmx.getThreadInfo(ids, true, true);
	 		System.out.println("The following threads are deadlocked:");
	 		for (ThreadInfo ti : infos) {
	    		System.out.println(ti);
			}
		}
	}

}
