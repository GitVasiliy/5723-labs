package model;

import java.util.ArrayList;

public class Chat {

	private static ArrayList<Message> chat = new ArrayList<Message>();
	
	public static void addMessage(Message message) {
		chat.add(message);
	}
	
	public static Message getMessage(int index) {
		return chat.get(index);
	}
	
	public static void removeMessage(int index) {
		chat.remove(index);
	}
	
	public static int size() {
		return chat.size();
	}
}
