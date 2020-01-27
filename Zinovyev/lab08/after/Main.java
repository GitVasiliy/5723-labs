import threads.*;

import java.util.Random;

public class Main {

    public static void main(String[] args){
        try{  
            int [] array1 = new int[100000000];
            int [] array2 = new int[100000000];
            Random random = new Random();
            for(int i = 0; i < 100000000; i++) {
            	int temp = random.nextInt();
            	array1[i] = temp;
            	array2[i] = temp;
            }
            
            long start = System.nanoTime();
            ThreadsMergeSort.sort(array1, 1);
            long finish = System.nanoTime();
            System.out.println("Time of mergesort for 1 thread = " + (finish - start));
            
            start = System.nanoTime();
            ThreadsMergeSort.sort(array2, 8);
            finish = System.nanoTime();
            System.out.println("Time of mergesort for 8 thread = " + (finish - start));
        }
        catch(RuntimeException ex){
            ex.getMessage();
        }
    }
    
}
