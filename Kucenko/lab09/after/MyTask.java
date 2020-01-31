import java.util.ArrayList;
import java.util.LinkedList;

public class MyTask implements Runnable {

    private int start;
    private int end;
    private ArrayList<Integer> answer;
    private LinkedList<ArrayList<Integer>> result;

    public MyTask(int start, int end, LinkedList<ArrayList<Integer>> result) {
        this.start = start;
        this.end = end;
        answer = new ArrayList<>();
        this.result = result;
    }

    @Override
    public void run() {
        synchronized (result) {
            for (int i = start; i <= end; i++) {
                if (isPrime(i)) {
                    answer.add(i);
                }
            }
            result.push(answer);
        }
    }

    public boolean isPrime(int num) {
        if (num == 2) {
            return true;
        }
        for (int i = 2; i < Math.sqrt(num)+1; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}

