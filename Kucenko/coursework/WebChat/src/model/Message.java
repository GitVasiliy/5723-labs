package model;

public class Message {
	
	private String fromWhom;
	private String date;
	private String message;
	
	public Message(String fromWhom, String date, String message) {
		this.setFromWhom(fromWhom);
		this.setDate(date);
		this.setMessage(message);
	}

	public String getFromWhom() {
		return fromWhom;
	}

	public void setFromWhom(String fromWhom) {
		this.fromWhom = fromWhom;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		if (message.contains("\\<.*?>") == true) {
			
			return message;
		}
		else {
		return message
			.replaceAll("\\<.*?>","");
		}
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
