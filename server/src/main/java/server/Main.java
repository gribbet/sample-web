package server;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import server.configuration.Configuration;
import server.configuration.ConfigurationReader;
import server.logger.ServerLogger;
import server.server.Server;

public class Main {
	public static void main(String[] args) throws IOException {
		ConfigurationReader.read();
		ServerLogger.configure();
		if (args.length == 0)
			start();
		else if (args[0].equals("stop"))
			stop();
	}

	public static void start() throws IOException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					final Server server = new Server();
					try {
						ServerSocket serverSocket = new ServerSocket(Configuration.stopPort);
						Socket socket = serverSocket.accept();
						socket.getInputStream().read();
					} catch (IOException e) {
						throw new RuntimeException(e);
					} finally {
						server.stop();
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				} finally {

				}
			}
		});
		thread.setDaemon(true);
		thread.start();

		boolean stop = false;
		try {
			stop = System.in.read() > 0;
		} catch (IOException e) {
		}

		if (stop)
			stop();

		try {
			thread.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void stop() throws IOException {
		Socket socket = new Socket(InetAddress.getLocalHost(), Configuration.stopPort);
		OutputStream out = socket.getOutputStream();
		out.write("stop".getBytes());
		out.flush();
		socket.close();
	}
}
