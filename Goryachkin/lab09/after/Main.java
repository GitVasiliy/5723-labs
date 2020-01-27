import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
    	String[] str = new String[args.length];
      System.out.println(args.length);
    	for (int i = 0; i < args.length; i++){
        BufferedReader r = new BufferedReader (new InputStreamReader(new FileInputStream(args[i])));
        str[i] = r.readLine();
    	}
    	Map<String, Integer> hashMap = new HashMap<String, Integer>();
    	MyThread[] threads = new MyThread[args.length];
    	for(int i = 0; i < threads.length; i++) {
			 threads[i] = new MyThread(str[i], hashMap);
			 threads[i].start();	
  		}
  		for (MyThread i : threads) {
  			i.join();
  		}
  		
      for(Map.Entry<String, Integer> item : hashMap.entrySet())
        System.out.printf("Word: %s  Value: %d \n", item.getKey(), item.getValue());
      }
}