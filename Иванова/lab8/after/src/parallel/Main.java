package parallel;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Integer> simpleList = new ArrayList<>();

        int size = 1000;
        Random rand = new Random();
        for (int i = 0; i < size-1; i++) {
            simpleList.add(rand.nextInt()%1000);
        }
        simpleList.add(76543);

        //для однопоточного
        long start = System.currentTimeMillis();

        int max = simpleList.get(0);
        for (int i = 0; i < size; i++) {
            if (simpleList.get(i) > max)
                max = simpleList.get(i);
        }

        long end = System.currentTimeMillis();

        System.out.println("Max = " + max);
        System.out.println("Время для однопоточного поиска = " + (end - start));

        int countThreads = 4;

        start = System.currentTimeMillis();

        ParallelMaxArrayList parallelMaxArrayList = new ParallelMaxArrayList();
        int max2 = parallelMaxArrayList.Execute(countThreads, simpleList);

        end = System.currentTimeMillis();

        System.out.println("Max = " + max2);
        System.out.println("Время для многопоточного поиска = " + (end - start));

    }
}
