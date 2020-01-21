package org.suai.client;


import java.net.ServerSocket;
import java.net.Socket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Closeable;

import java.lang.Thread;

import org.suai.console.ConsoleManager;


public class FileListener extends Thread implements Closeable {

	private ServerSocket server;

	private File file;
	private final int bufferSize = 1024;

	private ConsoleManager console;


	public FileListener(int port, File file, ConsoleManager console) throws IOException {
		this.server = new ServerSocket(port);
		
		this.file = file;

		this.console = console;
	}


	public void close() throws IOException {
		if(!this.server.isClosed()) {
			this.server.close();
		}
	}


	@Override
	public void interrupt() {
		super.interrupt();

		try {
			close();
		}
		catch(IOException exception) {}
	}


	@Override
	public void run() {
		try(
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(this.file));
		) {
			this.console.printSystemMessage("Begin receiving file.", null);

			Socket socket = this.server.accept();
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());

			byte[] data = new byte[this.bufferSize];
			int length = 0;
			
			while((length = bis.read(data, 0, this.bufferSize)) != -1) {
				bos.write(data, 0, length);
			}

			bos.flush();

			this.console.printSystemMessage("End receiving file.", null);
			this.console.printSystemMessage("Complete.", null);
		}
		catch(IOException exception) {
			if(!this.server.isClosed()) {
				this.console.printSystemMessage("Error receiving file." ,exception);
			}
		}

		try {
			close();
		}
		catch(IOException exception) {}
	}

}