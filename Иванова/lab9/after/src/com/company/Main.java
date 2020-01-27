package com.company;

public class Main {

    //через аргументы мы передаем названия файлов который нужно обработать
    public static void main(String[] args) throws InterruptedException {
        if (args.length > 0) {
            ReadWriter readerWriter = new ReadWriter(args); //передаем эти файлы в класс который будет считать частоты слов
            readerWriter.scan(); //вызываем функцию подсчета кол-ва слов

            readerWriter.printMap(); //выводим результат на экран в виде слово - частота
        } else {
            System.out.println("Error. Input filenames!");
        }
    }
}
