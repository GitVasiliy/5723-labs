package com.company;

import java.io.*;
import java.util.*;

public class MyThread extends Thread {
    private File file;
    private HashMap<String, Integer> map;

    public MyThread(File file, HashMap<String, Integer> map) {
        this.file = file;
        this.map = map;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(file); //создаем сканер чтобы считывать данные из файла
            //пока данные есть в файле
            while (scanner.hasNext()) {
                String word = scanner.next(); //считываем по одному слову
                //чтобы не получилось так что два потока одновременно не обратяться в map ок
                synchronized (map) { //бы должны обезопасить доступ разных поток
                    if (map.containsKey(word)) {
                        map.put(word, map.get(word) + 1);
                    } else {
                        map.put(word, 1); //еслои такого слова нету то добавляем его со значением 1 ок
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File: " + file.getName() + " not found!");
        }

    }

}
