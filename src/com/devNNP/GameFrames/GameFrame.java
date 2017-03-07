package com.devNNP.GameFrames;

import com.devNNP.*;
import com.devNNP.GUI.GUI;
import com.devNNP.GUI.GUIButton;
import com.devNNP.GUI.SDKButton;
import com.devNNP.Handlers.RenderHandler;
import com.devNNP.Helpers.*;
import com.devNNP.Listeners.KeyBoardListener;
import com.devNNP.Listeners.MouseEventListener;
import com.devNNP.Objects.AnimatedSprite;
import com.devNNP.Objects.GameObject;
import com.devNNP.Objects.Player;

import javax.swing.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

//public class GameFrame extends JWindow implements Runnable {
public class GameFrame extends JFrame implements Runnable {
    public static final int alpha = 0xFFFF00DC;
    public static final int obstruction = 0xFF0a0a0a;

    private Canvas canvas = new Canvas();
    private RenderHandler renderHandler;
    private GameObject[] objects;
    private KeyBoardListener keyBoardListener = new KeyBoardListener(this);
    private MouseEventListener mouseEventListener = new MouseEventListener(this);

    private Rectangle testRect = new Rectangle(30, 30, 100, 100);
    private BufferedImage testImage;
    private Sprite testSprite;
    private SpriteSheet testSheet;
    private Tiles testTiles;
    private Map testMap;
    private Player testPlayer;
    private AnimatedSprite testAnimSprite;
    private SpriteSheet testPlayerSheet;
    private int selectedTileID = 2;


    private AnimatedSprite myTestSprite;
    private SpriteSheet myTestSheet;

    public final int xZoom = 2;
    public final int yZoom = 2;

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;



    public GameFrame() {
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setBounds(0, 0, WIDTH, HEIGHT);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
        setVisible(true);

        setLocationRelativeTo(null);
        setFocusableWindowState(true);

        add(canvas);
        setVisible(true);

        canvas.createBufferStrategy(3);
        canvas.addKeyListener(keyBoardListener);
        canvas.addFocusListener(keyBoardListener);
        canvas.addMouseListener(mouseEventListener);
        canvas.addMouseMotionListener(mouseEventListener);

        renderHandler = new RenderHandler(getWidth(), getHeight());

        BufferedImage sheetImage = Loader.loadImage("/ProductionFields/TileSheet.png");
        testSheet = new SpriteSheet(sheetImage);
        testSheet.loadSprites(16, 16, 0);

        testTiles = new Tiles(Loader.loadFile("/ProductionFields/Tiles.txt"), testSheet);

        testMap = new Map(Loader.loadFile("/ProductionFields/Map2.txt"), testTiles);

        BufferedImage testPlayerSheetImage = Loader.loadImage("/TestFields/Player.png");
        testPlayerSheet = new SpriteSheet(testPlayerSheetImage);
        testPlayerSheet.loadSprites(20, 26, 0);

        testAnimSprite = new AnimatedSprite(testPlayerSheet, 5);

        objects = new GameObject[2]; // TODO: 03.03.2017 Change number of obj
        testPlayer = new Player(testAnimSprite, 8, this);

//        BufferedImage myTestImage = Loader.loadImage("/ProductionFields/Player.png");
//        myTestSheet = new SpriteSheet(myTestImage);
//        myTestSheet.loadSprites(50, 50, 0);
//        myTestSprite = new AnimatedSprite(myTestSheet, 5);
//        testPlayer = new Player(myTestSprite, 5);

        objects[0] = testPlayer;

        testRect.generateGraphics(3, 12234);

        GUIButton[] buttons = new GUIButton[testTiles.getSize()];
        Sprite[] tileSprites = testTiles.getSprites();

        for (int i = 0; i < buttons.length; i++) {
            Rectangle tileRectangle = new Rectangle(0, i * (16 * xZoom + 2), 16 * xZoom, 16 * yZoom);
            buttons[i] = new SDKButton(this, i, tileSprites[i], tileRectangle);
        }

        GUI gui = new GUI(buttons, 5, 5, true);

        objects[1] = gui;
//        testImage = ImageLoader.loadImage("/TestFields/GrassTile.png");
//        testSprite = new Sprite(testImage);
//        testSprite = testSheet.getSprite(4 ,1);
        testMap.setTile(7, 0, 0);
        testMap.setTile(8, 5, 5);
    }

    public void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);

//        renderHandler.renderImage(); <<< render some Images
//        renderHandler.renderImage(testImage, 50, 50, 3, 3);
//        renderHandler.renderSprite(testSprite, 50, 50, 3, 3);
//        renderHandler.renderRectangle(testRect, 1, 1);
//        testTiles.renderTile(0, renderHandler, 0, 0, 3, 20);
        testMap.renderMap(renderHandler, xZoom, yZoom);
        for (int i = 0; i < objects.length; i++) {
            objects[i].render(renderHandler, xZoom, yZoom);
        }
//        renderHandler.renderSprite(testAnimSprite, 30, 30, xZoom, yZoom);
//        renderHandler.renderSprite(myTestSprite, 30, 70, xZoom, yZoom);

        renderHandler.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
        renderHandler.clear();
    }

    public void update() {
        for (int i = 0; i < objects.length; i++) {
            objects[i].update(this);
        }
    }

    @Override
    public void run() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        int i = 0;
        int x = 0;

        long lastTime = System.nanoTime();
        double nanoSecondConversion = 1000000000.0 / 60;
        double changeInSeconds = 0;

        while (true) {
            long now = System.nanoTime();

            changeInSeconds += (now - lastTime) / nanoSecondConversion;

            while (changeInSeconds >= 1) {
                update();
                changeInSeconds = 0;
//                render();
            }

            render();
            lastTime = now;
        }
    }

    public void leftClick(int x, int y) {
        /*Rectangle mouseRectangle = new Rectangle(x, y, 1, 1);
        for (int i = 0; i < objects.length; i++) {
            objects[i].handleMouseClick(mouseRectangle, renderHandler.getCamera(), xZoom, yZoom);
        }

        x = (int)Math.floor((x + renderHandler.getCamera().getX()) / (16.0 * xZoom));
        y = (int)Math.floor((y + renderHandler.getCamera().getY()) / (16.0 * yZoom));
        testMap.setTile(2, x, y);*/

        Rectangle mouseRectangle = new Rectangle(x, y, 1, 1);
        boolean stoppedChecking = false;

        for(int i = 0; i < objects.length; i++)
            if(!stoppedChecking)
                stoppedChecking = objects[i].handleMouseClick(mouseRectangle, renderHandler.getCamera(), xZoom, yZoom);

        if(!stoppedChecking)
        {
            x = (int) Math.floor((x + renderHandler.getCamera().getX())/(16.0 * xZoom));
            y = (int) Math.floor((y + renderHandler.getCamera().getY())/(16.0 * yZoom));
            testMap.setTile(selectedTileID, x, y);
        }
    }

    public void rightClick(int x, int y)
    {
        x = (int) Math.floor((x + renderHandler.getCamera().getX())/(16.0 * xZoom));
        y = (int) Math.floor((y + renderHandler.getCamera().getY())/(16.0 * yZoom));
        testMap.removeTile(x, y);
    }

    public void handleControl(boolean[] keys) {
        if(keys[KeyEvent.VK_S])
            testMap.saveMap();
    }

    public void changeTile(int tileID) {
        selectedTileID = tileID;
    }

    public int getSelectedTile()
    {
        return selectedTileID;
    }

    public MouseEventListener getMouseEventListener() {
        return mouseEventListener;
    }

    public KeyBoardListener getKeyBoardListener() {
        return keyBoardListener;
    }

    public RenderHandler getRenderHandler() {
        return renderHandler;
    }

    public Map getMap() {
        return testMap;
    }

    public Rectangle getCamera() {
        return renderHandler.getCamera();
    }

    public int getxZoom() {
        return xZoom;
    }

    public int getyZoom() {
        return yZoom;
    }
}
