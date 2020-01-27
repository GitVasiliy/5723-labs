import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class TaskQueue {

    private LinkedList<MyTask> queue;
    private LinkedList<ArrayList<Integer>> result;
    private int threads;

    public TaskQueue(int threads) {
        queue = new LinkedList<>();
        result = new LinkedList<>();
        this.threads = threads;
    }

    public void begin() {
        this.push(new MyTask(1,100, result));
        this.push(new MyTask(10,20, result));
        this.push(new MyTask(20,30, result));
        this.push(new MyTask(30,100, result));
        this.push(new MyTask(100, 200, result));
        
        if (threads > queue.size()) {
            threads = queue.size();
        }
        Thread[] threadsArray = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            threadsArray[i] = new Thread(this.pop());
            threadsArray[i].start();
        }
        int tmp = 0;
        while (!queue.isEmpty()) {
            try {
                threadsArray[tmp].join();
                threadsArray[tmp] = new Thread(this.pop());
                threadsArray[tmp].start();
            }
            catch (InterruptedException e) { }
            tmp++;
            tmp = tmp % threads;
        }
        try {
            for (int i = 0; i < threads; i++) {
                threadsArray[i].join();
            }
        }
        catch (InterruptedException e) { }
    }

    public MyTask pop() {
        MyTask tmp = queue.peekLast();
        queue.removeLast();
        return tmp;
    }

    public void push(MyTask t) {
        queue.push(t);
    }

    public void showResult() {
        Iterator<ArrayList<Integer>> e = result.iterator();
        while(e.hasNext()) {
            System.out.println(e.next());
        }
    }
}
