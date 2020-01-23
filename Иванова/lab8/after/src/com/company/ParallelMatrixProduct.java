package com.company;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelMatrixProduct {

    public static int num;

    ParallelMatrixProduct(int num){

        this.num = num;
    }
    public static UsualMatrix parallelProduct(UsualMatrix a,UsualMatrix b) throws InterruptedException {

        UsualMatrix result = new UsualMatrix(a.n,b.m);

        int d = a.n / num;

        int count = num;
        //если потоков больше чем кол-ва строк то создаем столько потоков сколько кол-во строк
        //и каждый обрабатывает по одной строке
        if (d == 0)
        {
            count = a.n;
            d = 1;
        }



        List<Thread>list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int startRow = i*d;
            int endRow = i*d+d;
            //если строки распределились не равномерно, то последний поток обработает больше строк
            //последний поток обработает строки до конца
            if (i == count - 1)
            {
                endRow = a.n;
            }
            Thread thread = new Thread(new AdvanceThread(startRow, endRow, a,b, result));
            thread.start();
            list.add(thread);
        }

        for (int i = 0; i < list.size(); i++) {
            list.get(i).join();
        }

        return result;
    }
}