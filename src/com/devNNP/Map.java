package com.devNNP;

import com.devNNP.Handlers.RenderHandler;
import com.devNNP.Helpers.Loader;
import com.devNNP.Helpers.Rectangle;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Map {

    private Tiles tiles;
    private Sprite background;
    private int fillTileID = -1;
    private ArrayList<MappedTile> mappedTiles = new ArrayList<>();
    private HashMap<Integer, String> comments = new HashMap<Integer, String>();
    private File mapFile;

    public Map(File mapFile, Tiles tiles) {
        this.tiles = tiles;
        this.mapFile = mapFile;
        Scanner scanner = null;
        try {
            scanner = new Scanner(mapFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.startsWith("//")) {
                if(line.contains(":")) {
                    String[] splitLine = line.split(":");
                    if (splitLine[0].equalsIgnoreCase("Fill")) {
                        fillTileID = Integer.parseInt(splitLine[1]);
                    }
                } else {
                    String[] splitLine = line.split(",");
                    if (splitLine.length >= 3 && Integer.parseInt(splitLine[0]) != 0) {
                        MappedTile mappedTile = new MappedTile(
                                Integer.parseInt(splitLine[0]),
                                Integer.parseInt(splitLine[1]),
                                Integer.parseInt(splitLine[2])
                        );
                        mappedTiles.add(mappedTile);
                    }
                }
            }
        }
    }

    public Map(Sprite background, File mapFile, Tiles tiles) {
        this.tiles = tiles;
        this.mapFile = mapFile;
        this.background = background;
        Scanner scanner = null;
        try {
            scanner = new Scanner(mapFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.startsWith("//")) {
                if(line.contains(":")) {
                    String[] splitLine = line.split(":");
                    if (splitLine[0].equalsIgnoreCase("Fill")) {
                        fillTileID = Integer.parseInt(splitLine[1]);
                    }
                } else {
                    String[] splitLine = line.split(",");
                    if (splitLine.length >= 3) {
                        MappedTile mappedTile = new MappedTile(
                                Integer.parseInt(splitLine[0]),
                                Integer.parseInt(splitLine[1]),
                                Integer.parseInt(splitLine[2])
                        );
                        mappedTiles.add(mappedTile);
                    }
                }
            }
        }
    }

    public void renderMap(RenderHandler renderHandler, int xZoom, int yZoom) {
        int tileWidth = 16 * xZoom;
        int tileHeight= 16 * yZoom;

        Rectangle camera = renderHandler.getCamera();

        if (background !=  null) {
            renderHandler.renderSprite(background, 0, 0, camera.getWidth(), camera.getHeight(),
                    1, 1, true, camera.getX(), camera.getY());
        }

        for (int y = camera.getY() - tileHeight - (camera.getY() % tileHeight); y < camera.getY() + renderHandler.getCamera().getHeight(); y += tileHeight) {
            for (int x = camera.getX() - tileWidth - (camera.getX() % tileWidth); x < camera.getX() + renderHandler.getCamera().getWidth(); x += tileWidth) {
                tiles.renderTile(fillTileID, renderHandler, x, y, xZoom, yZoom);
            }
        }

        for (int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++) {
            MappedTile mappedTile = mappedTiles.get(tileIndex);
            tiles.renderTile(mappedTile.id, renderHandler, mappedTile.x * tileWidth, mappedTile.y * tileHeight, xZoom, yZoom);

        }
    }

    public MappedTile getTile(int tileX, int tileY) {
        MappedTile toReturn = null;
        for (int i = 0; i < mappedTiles.size(); i++) {
            MappedTile mappedTile = mappedTiles.get(i);
            if (mappedTile.id == 7) {
//                System.out.println("x: " + mappedTile.x + ", y: " + mappedTile.y);
            }
            if (mappedTile.x == tileX && mappedTile.y == tileY) {
                toReturn = mappedTile;
//                System.out.println(mappedTile);
                break;
            }
//            System.out.println(mappedTile);
        }
//        System.out.println(mappedTiles.size());
        return toReturn;
    }

    public void setTile(int tileID, int tileX, int tileY) {
        boolean foundTile = false;
        for (int i = 0; i < mappedTiles.size(); i++) {
            MappedTile mappedTile = mappedTiles.get(i);
            if (mappedTile.x == tileX && mappedTile.y == tileY) {
                mappedTile.id = tileID;
                foundTile = true;
                break;
            }
        }

        if (!foundTile) {
            mappedTiles.add(new MappedTile(tileID, tileX, tileY));
        }
    }

    private void clear(File mapFile) {
        FileWriter fwOb = null;
        try {
            fwOb = new FileWriter(mapFile, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        try {
            fwOb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeTile(int tileX, int tileY)
    {
        for(int i = 0; i < mappedTiles.size(); i++)
        {
            MappedTile mappedTile = mappedTiles.get(i);
            if(mappedTile.x == tileX && mappedTile.y == tileY) {
                mappedTiles.remove(i);
            }
        }
    }

    public void saveMap()
    {
        try
        {
            int currentLine = 0;
            clear(mapFile);

            PrintWriter printWriter = new PrintWriter(mapFile);

            if(fillTileID >= 0) {
                if(comments.containsKey(currentLine))
                {
                    printWriter.println(comments.get(currentLine));
                    currentLine++;
                }
                printWriter.println("Fill:" + fillTileID);
            }

            for(int i = 0; i < mappedTiles.size(); i++) {
                if(comments.containsKey(currentLine))
                    printWriter.println(comments.get(currentLine));

                MappedTile tile = mappedTiles.get(i);
                printWriter.println(tile.id + "," + tile.x + "," + tile.y);
                currentLine++;
            }

            printWriter.close();
            System.out.println(mapFile.getPath());
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
    }

    public class MappedTile {
        private int id, x, y;
        private boolean canGoThrough;

        public MappedTile(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.canGoThrough = canGoThrough;
        }

        public String toString() {
            return "[x: " + x + ", y: " + y + ", id: " + id + "]";
        }


    }
}
