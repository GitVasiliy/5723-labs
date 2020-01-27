public class Main {

    public static void main(String[] args) {

    	int threads = 1;

    	TaskQueue test0 = new TaskQueue(threads);
        long start = System.nanoTime();
        test0.begin();
		long finish = System.nanoTime();
		System.out.println("time (" + threads +  " threads)= " + (finish - start));

		threads = 2;
        TaskQueue test1 = new TaskQueue(2);
        start = System.nanoTime();
        test1.begin();
		finish = System.nanoTime();
		System.out.println("time (" + threads  + " threads)= " + (finish - start));

		threads = 4;
		TaskQueue test2 = new TaskQueue(4);
        start = System.nanoTime();
        test2.begin();
		finish = System.nanoTime();
		System.out.println("time (" + threads  + " threads)= " + (finish - start));

		threads = 8;
		TaskQueue test3 = new TaskQueue(8);
        start = System.nanoTime();
        test3.begin();
		finish = System.nanoTime();
		System.out.println("time (" + threads + " threads)= " + (finish - start));

		threads = 16;
		TaskQueue test4 = new TaskQueue(16);
        start = System.nanoTime();
        test4.begin();
		finish = System.nanoTime();
		System.out.println("time (" + threads  + " threads)= " + (finish - start));
        
    }
}
