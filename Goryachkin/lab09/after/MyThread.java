import java.lang.Thread;
import java.util.*;
import java.io.*;

public class MyThread extends Thread {

	private String str;
	private Map hashMap;
	
	public MyThread(String str, Map hashMap) 
	{
		this.str = str;
		this.hashMap = hashMap;
	}
	
	@Override
	public void run() {
		String[] subStr;
    	String delimeter = " ";
    	subStr = str.split(delimeter);
    	synchronized (hashMap){
		    for(int j = 0; j < subStr.length; j++)
		    	if (hashMap.containsKey(subStr[j])) 
		    		hashMap.put(subStr[j], (int)hashMap.get(subStr[j]) + 1);	
		    	else
		    		hashMap.put(subStr[j], 1);
		}    	
	}
}