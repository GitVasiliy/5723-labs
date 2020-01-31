package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Notes;

public class ReadNotes {
	
private static BufferedReader br;
	
	public static  Notes readFile(String file) throws IOException {
		open(file);
		Notes notes = new Notes();
		String line = br.readLine();
		String [] data = line.split("&");
		for(int i = 0; i < data.length; i++) {
			if(data[i] != null) {
				notes.addNote(data[i]);
			}
		}
		close();
		return notes;
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
