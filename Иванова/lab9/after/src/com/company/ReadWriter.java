package com.company;

import java.io.File;
import java.util.*;

public class ReadWriter {
    private HashMap<String, Integer> map = new HashMap<>();
    private MyThread[] threads;
    private String[] fileNames;

    public ReadWriter(String[] filenames) {
        fileNames = filenames;
        threads = new MyThread[filenames.length]; //потоков создаем столько сколько файлов
    }

    public void scan() throws InterruptedException {
        //вот тут создаем потоки и запускаем их
        for (int i = 0; i < fileNames.length; i++) {
            threads[i] = new MyThread(new File(fileNames[i]), map);
            threads[i].start();
        }
//ожидаем когда все потоки завершат свою работу
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }



    public void printMap() {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}
