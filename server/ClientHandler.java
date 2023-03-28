package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import server.sorting.MergeSort;
import server.sorting.MultithreadedMergeSort;
import server.sorting.MultithreadedQuickSort;
import server.sorting.QuickSort;

class ClientHandler {
    private Socket connectionSocket;

    public ClientHandler(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    public void run() {
        try (BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream())) {

            // Read the type of sorting algorithm wanted
            String typeOfAlgorithm = inFromClient.readLine().trim();
            // Read number of threads to use for sorting
            String clientInputThreads = inFromClient.readLine();
            int numberOfThreads = Integer.valueOf(clientInputThreads.trim());
            String clientInput = inFromClient.readLine().trim();
            int[] array = new int[clientInput.split(",").length];
            array = Arrays.stream(clientInput.split(","))
                    .map(String::trim)
                    .map(s -> s.replace("\uFEFF", ""))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            long start = System.currentTimeMillis();
            switch (typeOfAlgorithm) {
                case "1":
                    MergeSort.sort(array, 0, array.length - 1);
                    break;
                case "2":
                    MultithreadedMergeSort.sort(array);
                    break;
                case "3":
                    QuickSort.sort(array, 0, array.length - 1);
                    break;
                case "4":
                    MultithreadedQuickSort.sort(array, numberOfThreads);
                    break;
                default:
                    break;
            }
            long end = System.currentTimeMillis();
            String sortedResult = Arrays.toString(array);
            // Send a response back to the client
            outToClient.writeBytes("Server sorted: " + sortedResult + System.lineSeparator());
            outToClient.writeBytes("It took " + (end - start) + "ms" + System.lineSeparator());
            // Close the socket
            connectionSocket.close();
            System.out.println("Client disconected");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Client handler thread failed");
        }
    }
}