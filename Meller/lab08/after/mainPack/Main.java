package mainPack;
import backpack.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        Item i1 = new Item(9,6);
//        Item i2 = new Item(4, 3);
//        Item i3 = new Item(5, 4);
//        Item i4 = new Item(1, 1);
//        Item [] items = new Item[4];
//        items[0] = i1; items[1] = i2; items[2] = i3; items[3] = i4;

        Random rand = new Random();
        Item[] items = new Item[11];
        for(int i = 0; i < 11; i++){
            items[i] = new Item(rand.nextInt(10000), rand.nextInt(90) + rand.nextInt(20));
        }

        Pack p1 = new Pack(700, items);


        long start = System.currentTimeMillis();
        p1.solvePack(1);
        long finish = System.currentTimeMillis();

        System.out.println((p1.getMaxCost()) + " time of 1 thread: " + (finish - start) + "ms");

        long start1 = System.currentTimeMillis();
        p1.solvePack(2);
        long finish1 = System.currentTimeMillis();

        System.out.println((p1.getMaxCost()) + " time of 2 thread: " + (finish1 -  start1) + "ms");



    }
}
