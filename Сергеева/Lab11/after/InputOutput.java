
import java.net.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputOutput {
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public void close() throws IOException {
		this.in.close();
	}

	public String read() throws IOException {
		String message = in.readLine();
		message = message.trim();
		return message;
	}

	public synchronized void write(String message) {
		System.out.println(message);
	}
}

