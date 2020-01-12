package com.company;

public class DeadlockThread extends Thread {
    private Account firstAccount;
    private Account secondAccount;
    private int sum;

    public DeadlockThread(Account firstAccount, Account secondAccount, int sum) {
        this.firstAccount = firstAccount;
        this.secondAccount = secondAccount;
        this.sum = sum;
    }

    public void run() {
        synchronized(this.firstAccount) {
            System.out.println("Выполняется транзакция с счетом №" + firstAccount.getNumber());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            synchronized(this.secondAccount) {
               firstAccount.setBalance(firstAccount.getBalance() - sum);
                secondAccount.setBalance(secondAccount.getBalance() + sum);
                System.out.println("Операция успешно выполнена");
            }

        }
    }
}