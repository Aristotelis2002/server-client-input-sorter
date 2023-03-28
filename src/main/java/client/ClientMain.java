package client;

import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientMain {
    final static int PORT_NUMBER = 6788;

    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("localhost", PORT_NUMBER);
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream())) {
           
            // Send a message to the server
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose sorting algorithm:\n" + 
                    "1) Single threaded merge sort\n" + 
                    "2) Multi threaded merge sort(Dynamic forkJoinPool)\n" +
                    "3) Single threaded quick sort\n" +
                    "4) Multi threaded quick sort\n" + 
                    "Enter a number between 1 and 4");
            String inputConsole = scanner.nextLine();
            outToServer.writeBytes(inputConsole + System.lineSeparator());
            if (inputConsole.equals("4")) {
                System.out.println("Enter number of threads to sort with");
                inputConsole = scanner.nextLine();
            }
            
            outToServer.writeBytes(inputConsole + System.lineSeparator());
            System.out.println("Enter an array(example:5,4,3,2,1)");
            inputConsole = scanner.nextLine();
            scanner.close();
            outToServer.writeBytes(inputConsole + System.lineSeparator());
            outToServer.flush();

            // Read the response from the server
            String serverResponse = inFromServer.readLine();
            System.out.println("Server: " + serverResponse);
            serverResponse = inFromServer.readLine();
            System.out.println("Server: " + serverResponse);
            // Close the socket
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
