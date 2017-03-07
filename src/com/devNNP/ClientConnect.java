package com.devNNP;

import java.io.IOException;
import java.net.Socket;

public class ClientConnect {
    private String ip;
    private int port = 53585;
    private Socket socket;

    public ClientConnect() {

    }

    public void createConnect() {
        ip = "localhost";
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed() {
        return socket.isClosed();
    }
}
