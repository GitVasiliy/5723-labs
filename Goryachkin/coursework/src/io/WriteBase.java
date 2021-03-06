package io;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class WriteBase {

	private static BufferedWriter bw;
	
	public static void write(String file, String username, String password) throws IOException {
		open(file);
		StringBuilder sb = new StringBuilder();
		sb.append(username).append("; ").append(password).append("&");
		bw.write(sb.toString());
		close();
	}
	
	private static void close() throws IOException {
		bw.close();
	}
	
	private static void open(String file) throws FileNotFoundException {
		bw = new BufferedWriter(
	            new OutputStreamWriter(
	                    new FileOutputStream(file, true)));
	}
	
}
