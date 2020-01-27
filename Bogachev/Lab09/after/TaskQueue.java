import dop1.*;

import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Logger;

public class TaskQueue extends LinkedList<MyTask> {

    TaskQueue() {
        super();
    }

    @Override
    public synchronized MyTask pop() {
        return (MyTask) super.pop();
    }

    public synchronized void push(MyTask t) {
        super.addLast(t);
    }

    public void calc(int threadNum) throws InterruptedException {
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++)
            threads[i] = new Thread(new Runner(this));
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    public String showResult() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size(); i++) {
            sb.append(this.get(i).show());
        }
        return sb.toString();
    }

    class Runner implements Runnable {

        TaskQueue list;

        Runner(TaskQueue tq) {
            list = tq;
        }

        private void calc(MyTask t) {
            Random random = new Random();
            for (int i = t.getStart(); i <= t.getEnd(); i++) {
                if (i > 0 && i <= 2) {
                    t.pushSimple(i);
                }
                if (i % 2 == 1 && i > 2) {
                    for (int j = 0; j < 5; j++) {
                        int a = random.nextInt(i - 2) + 2;
                        if (Math.pow(a, i - 1) % i != 1) {
                            break;
                        }
                        if (j == 4) {
                            t.pushSimple(i);
                        }
                    }
                }
            }
            t.setTaskCompleted(true);
        }

        @Override
        public void run() {
            MyTask cur;
            while (!(cur = list.pop()).getTaskCompleted()) {
                calc(cur);
                push(cur);
            }
            list.addFirst(cur);
        }
    }

    public static void main(String[] args) {
        Logger log = Logger.getLogger(TaskQueue.class.getName());
        TaskQueue test1 = new TaskQueue();
        test1.push(new MyTask(0, 4));
        test1.push(new MyTask(4, 6));
        test1.push(new MyTask(11, 14));
        test1.push(new MyTask(3, 8));
        test1.push(new MyTask(4, 5));
        test1.push(new MyTask(8, 10));
        test1.push(new MyTask(2, 6));
        TaskQueue test2 = test1;
        log.info(test1.showResult());

        try {
            long start = System.nanoTime();
            test1.calc(1);
            long end = System.nanoTime();
            log.info(String.valueOf(end - start));
            start = System.nanoTime();
            test2.calc(4);
            end = System.nanoTime();
            log.info(String.valueOf(end - start));
            log.info(test1.showResult());
        }
        catch (InterruptedException e) {
            log.info(e.getMessage());
        }

    }
}
