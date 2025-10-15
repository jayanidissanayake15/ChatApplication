package org.example.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {

    @FXML
    private TextArea txtAreaChat;

    @FXML
    private TextField txtMessage;

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public void initialize() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(5000);
                socket = serverSocket.accept();
                Platform.runLater(() -> txtAreaChat.appendText("Client connected.\n"));

                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                String msg;
                while ((msg = reader.readLine()) != null) {
                    String message = msg;
                    Platform.runLater(() -> txtAreaChat.appendText("Client: " + message + "\n"));
                }
            } catch (IOException e) {
                Platform.runLater(() -> txtAreaChat.appendText("Server error: " + e.getMessage() + "\n"));
            }
        }).start();
    }

    @FXML
    private void onSendAction() {
        String msg = txtMessage.getText();
        if (msg.isEmpty() || writer == null) return;

        txtAreaChat.appendText("Server: " + msg + "\n");
        writer.println(msg);
        txtMessage.clear();
    }
}
