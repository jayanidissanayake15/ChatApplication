package org.example.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class ClientController {

    @FXML
    private TextArea txtAreaChat;

    @FXML
    private TextField txtMessage;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public void initialize() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 5000);
                Platform.runLater(() -> txtAreaChat.appendText("Connected to Server.\n"));

                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                String msg;
                while ((msg = reader.readLine()) != null) {
                    String message = msg;
                    Platform.runLater(() -> txtAreaChat.appendText("Server: " + message + "\n"));
                }
            } catch (IOException e) {
                Platform.runLater(() -> txtAreaChat.appendText("Client error: " + e.getMessage() + "\n"));
            }
        }).start();
    }

    @FXML
    private void onSendAction() {
        String msg = txtMessage.getText();
        if (msg.isEmpty() || writer == null) return;

        txtAreaChat.appendText("Client: " + msg + "\n");
        writer.println(msg);
        txtMessage.clear();
    }
}
