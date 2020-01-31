package io;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import model.Notes;

public class WriteNotes {

	private static BufferedWriter bw;
	
	public static void write(String file, Notes notes) throws IOException {
		open(file);
		bw.write(notes.toString());
		close();
	}
	
	private static void close() throws IOException {
		bw.close();
	}
	
	private static void open(String file) throws FileNotFoundException {
		bw = new BufferedWriter(
	            new OutputStreamWriter(
	                    new FileOutputStream(file)));
	}
	
}
