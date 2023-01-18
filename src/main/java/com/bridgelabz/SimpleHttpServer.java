package com.bridgelabz;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
	public static int DEFAULT_PORT = 9000;
	public static int port;
	private HttpServer httpServer;

	private void start(int port) {
		this.port = port;
		try {
			httpServer = HttpServer.create(new InetSocketAddress(port), 0);
			System.out.println("Server Started at: " + port);
			httpServer.createContext("/", new Handlers.RootHandler());
			httpServer.createContext("/echoHeader ", new Handlers.EchoHeaderHandler());
			httpServer.createContext("/echoGet ", new Handlers.EchoGetHandlers());
			httpServer.createContext("/echoPost ", new Handlers.EchoPostHandlers());
			httpServer.setExecutor(null);
			httpServer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SimpleHttpServer httpsServer = new SimpleHttpServer();
		httpsServer.start(DEFAULT_PORT);
	}
}
