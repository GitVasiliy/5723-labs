import java.util.Scanner;

public class MainQ {

     public static void main(String[] args) throws InterruptedException {
        int n = Integer.parseInt(args[0]);
        int maxThreads = Integer.parseInt(args[1]);
        Queen2.setMaxGSK(maxThreads);
        Queen2 queens = new Queen2(n, 1, new int[n+1]);
        long startedTime = System.currentTimeMillis();
        queens.start();
        queens.join();
        System.out.println("Solutions: " + Queen2.getSolution());
        System.out.println("Working time: " + (float)(System.currentTimeMillis() - startedTime) / 1000 + " s.");
    }
}