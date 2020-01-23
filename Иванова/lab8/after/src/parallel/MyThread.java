package parallel;

import java.util.ArrayList;

public class MyThread implements Runnable {

    int start, end;
    ArrayList<Integer> list;
    ArrayList<Integer> res;

    public MyThread(int start, int end, ArrayList<Integer> list, ArrayList<Integer> res)
    {
        this.start = start;
        this.end = end;
        this.list = list;
        this.res = res;
    }

    @Override
    public void run() {
        int max = list.get(start);

        for (int i = start; i < end; i++) {
            if (list.get(i) > max)
                max = list.get(i);
        }

        res.add(max);
    }
}
