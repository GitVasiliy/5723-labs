package com.company;

public class Main {
    public static void main(String[] args) {
       Account account1 = new Account(20000, 345);
       Account account2 = new Account(50000, 290);
       int sum = 1000;

        DeadlockThread thread1 = new DeadlockThread(account1, account2, sum);
        DeadlockThread thread2 = new DeadlockThread(account2, account1, sum);
        thread1.start();
        thread2.start();
    }
}

