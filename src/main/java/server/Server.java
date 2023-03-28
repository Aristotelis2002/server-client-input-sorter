package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    final static int PORT_NUMBER = 6788;
    final static int THREAD_COUNT = 5;

    public static void main(String[] args) {
        try (ServerSocket welcomeSocket = new ServerSocket(PORT_NUMBER)) {
            // Create a server socket and bind it to a port
            ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
            System.out.println("Server listening on port 6788...");

            while (true) {
                // Accept a client connection
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Client connected");
                threadPool.submit(() -> {
                    ClientHandler handler = new ClientHandler(connectionSocket);
                    handler.run();
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server failed");
        }

    }

}
