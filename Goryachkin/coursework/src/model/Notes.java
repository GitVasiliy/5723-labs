package model;

import java.util.ArrayList;

public class Notes {

	private ArrayList<String> notes;

	public Notes() {
		notes = new ArrayList<String>();
	}
	
	public ArrayList<String> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}
	
	public void addNote(String note) {
		notes.add(note);
	}
	
	public Integer size() {
		return notes.size();
	}
	
	public String getNote(int index) {
		return notes.get(index);
	}
	
	public void setNote(String note, int index) {
		notes.set(index, note);
	}
	
	public void removeNote(int index) {
		notes.remove(index);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(notes.size() == 0) return "";
		for(int i = 0; i < notes.size(); i++) {
			sb.append(notes.get(i));
			sb.append("&");
		}
		return sb.toString();
	}
	
}
