package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadBase {
	
private static BufferedReader br;
	
	public static  ArrayList<String []> readFile(String file) throws IOException {
		open(file);
		ArrayList<String []> result = new ArrayList<>();
		String line  = br.readLine();
		String [] loginpass = line.split("&");
		for(int i = 0; i < loginpass.length; i++) {
			String [] data = loginpass[i].split("; "); 
			if(data.length != 2) {
				throw new IllegalArgumentException("username; password");
			}
			result.add(data);
		}
		close();
		return result;
	}
	
	public static void close() throws IOException {
		br.close();
	}
	
	public static void open(String file) throws FileNotFoundException {
		br = new BufferedReader(
	            new InputStreamReader(
	                    new FileInputStream(file)));
	}
	
}
