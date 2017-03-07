package com.devNNP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ServerConnect {
    private String ip;
    private int port = 53585;
    private ServerSocket serverSocket;

    public ServerConnect() {

    }

    public void createServer() {
        ip = "localhost";
        try {
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed() {
        return serverSocket.isClosed();
    }
}
