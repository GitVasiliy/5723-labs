package mainPack;
import deadlockPack.Thread1;
import deadlockPack.Thread2;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        Object o2 = new Object();
        Thread1 t1 = new Thread1(o1, o2);
        Thread2 t2 = new Thread2(o1, o2);
        t1.start();
        t2.start();
    }
}
