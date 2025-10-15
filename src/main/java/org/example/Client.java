package org.example;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a message to send: ");
        String userMessage = scanner.nextLine();

        try {
            Socket remoteSocket = new Socket("localhost", 3000);
            DataOutputStream dataOutputStream = new DataOutputStream(remoteSocket.getOutputStream());

            dataOutputStream.writeUTF(userMessage);
            dataOutputStream.flush();

            remoteSocket.close();
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
