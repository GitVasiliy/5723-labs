package parallel;

import java.util.ArrayList;
import java.util.List;

public class ParallelMaxArrayList {
    public int Execute(int countThread, ArrayList<Integer> list) throws InterruptedException {
        ArrayList<Integer> listMax = new ArrayList<>();

        int d = list.size() / countThread;

        int count = countThread;
        //если потоков больше чем кол-ва строк то создаем столько потоков сколько кол-во строк
        //и каждый обрабатывает по одной строке
        if (d == 0)
        {
            count = list.size();
            d = 1;
        }


        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int startRow = i*d;
            int endRow = i*d+d;

            if (i == count - 1)
                endRow = list.size();

            Thread thread = new Thread(new MyThread(startRow, endRow, list, listMax));
            thread.start();
            threads.add(thread);
        }

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).join();
        }

        int max = listMax.get(0);

        for (int i = 0; i < listMax.size(); i++) {
            if (listMax.get(i) > max)
                max = listMax.get(i);
        }

        return max;
    }
}
