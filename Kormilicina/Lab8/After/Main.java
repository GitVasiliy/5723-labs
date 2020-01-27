import java.util.*;
import java.lang.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Random rand = new Random();
        Item[] items = new Item[11];
        for(int i = 0; i < 11; i++){
            items[i] = new Item(rand.nextInt(10000), rand.nextInt(100));
        }

        Pack p1 = new Pack(700, items);

	    try
        {
			long start = System.currentTimeMillis();
			p1.solvePack(1);
			long finish = System.currentTimeMillis();

			System.out.println((" Max cost: " + p1.getMaxCost()) + " ; time of 1 thread: " + (finish - start) + "ms");

			long start1 = System.currentTimeMillis();
			p1.solvePack(2);
			long finish1 = System.currentTimeMillis();

			System.out.println((" Max cost: " + p1.getMaxCost()) + " ; time of 2 thread: " + (finish1 -  start1) + "ms");
			
			/*long start2 = System.currentTimeMillis();
			p1.solvePack(3);
			long finish2 = System.currentTimeMillis();

			System.out.println((" Max cost: " + p1.getMaxCost()) + " ; time of 3 thread: " + (finish2 - start2) + "ms");*/

		}
		catch(Exception e)
		{
			
		}
    }
}