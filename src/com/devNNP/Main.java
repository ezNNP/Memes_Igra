package com.devNNP;

import com.devNNP.GameFrames.GameFrame;

public class Main {
    private static ClientConnect clientConnect = new ClientConnect();
    private static ServerConnect serverConnect = new ServerConnect();
    private static GameFrame gameFrame;

    public static void main(String[] args) {
        gameFrame = new GameFrame();
        Thread gameThread = new Thread(gameFrame);
        gameThread.start();
        /* ========================================================= */
        serverConnect.createServer();
        clientConnect.createConnect();
        System.out.println("Подключение созданно");
        /* ========================================================= */

        /* ========================================================= */
        serverConnect.closeServer();
        clientConnect.closeConnect();
        System.out.println("Подключение закрыто");
    }
}