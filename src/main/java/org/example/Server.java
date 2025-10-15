package org.example;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your message : ");
        String urmessage = scanner.nextLine();

        try {
            ServerSocket serverSocket = new ServerSocket(3000);
            System.out.println("Server Started");

            Socket localSocket = serverSocket.accept();
            System.out.println("Client Accepted");

            DataInputStream dataInputStream = new DataInputStream(localSocket.getInputStream());
            String message = dataInputStream.readUTF();
            System.out.println("Client : " + message);
            localSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}