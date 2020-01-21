package org.suai.client;


import java.net.Socket;
import java.net.InetAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Closeable;

import org.suai.console.ConsoleManager;


public class FileSender implements Closeable {

	private Socket socket;

	private File file;
	private final int bufferSize = 1024;

	private ConsoleManager console;


	public FileSender(InetAddress address, int port, File file, ConsoleManager console) throws IOException {
		this.socket = new Socket(address, port);

		this.file = file;

		this.console = console;
	}


	public void close() throws IOException {
		if(!this.socket.isClosed()) {
			this.socket.close();
		}
	}


	public void sendFile() {
		try(
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(this.file));
		) {
			this.console.printSystemMessage("Begin sending file.", null);

			BufferedOutputStream bos = new BufferedOutputStream(this.socket.getOutputStream());

			byte[] data = new byte[this.bufferSize];
			int length = 0;
			
			while((length = bis.read(data, 0, this.bufferSize)) != -1) {
				bos.write(data, 0, length);
			}

			bos.flush();

			this.console.printSystemMessage("End sending file.", null);
			this.console.printSystemMessage("Complete.", null);
		}
		catch(IOException exception) {
			if(!this.socket.isClosed()) {
				this.console.printSystemMessage("Error sending file.", exception);
			}
		}
	}

}