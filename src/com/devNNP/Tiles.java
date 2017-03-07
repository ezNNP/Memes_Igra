package com.devNNP;

import com.devNNP.Handlers.RenderHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tiles {
    private SpriteSheet spriteSheet;
    private ArrayList<Tile> tiles = new ArrayList<>();

    public Tiles(File tilesFile, SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
        Scanner scanner = null;
        try {
//            System.out.println(tilesFile.getPath().substring(6));
            scanner = new Scanner(tilesFile);
        } catch (Exception e) {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.startsWith("//")) {
                String[] splitLine = line.split("-");
                String tileName = splitLine[0];
                int spriteX = Integer.parseInt(splitLine[1]);
                int spriteY = Integer.parseInt(splitLine[2]);
                boolean canGoThrough = true;
                int[] pix = spriteSheet.getSprite(spriteX, spriteY).getPixels();
                for (int i = 0; i < pix.length; i++) {
                    if (pix[i] == 0x0a0a0a) {
                        canGoThrough = false;
                        break;
                    }
                }
                Tile tile = new Tile(tileName, spriteSheet.getSprite(spriteX, spriteY), canGoThrough);
                tiles.add(tile);
            }
        }
    }

    public void renderTile(int tileID, RenderHandler renderer, int xPosition, int yPosition, int xZoom, int yZoom) {
        if (tileID >= 0 && tiles.size() > tileID) {
            renderer.renderSprite(tiles.get(tileID).sprite, xPosition, yPosition, xZoom, yZoom, false);
        } else {
            try {
                throw new ArrayIndexOutOfBoundsException();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSize() {
        return tiles.size();
    }

    public Sprite[] getSprites() {
        Sprite[] sprites = new Sprite[getSize()];

        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = tiles.get(i).sprite;
        }

        return sprites;
    }


    public class Tile {
        private String tileName;
        private Sprite sprite;
        private boolean canGoThrough;

        public Tile(String tileName, Sprite sprite, boolean canGoThrough) {
            this.tileName = tileName;
            this.sprite = sprite;
            this.canGoThrough = canGoThrough;
        }

        public boolean getThroughable() {
            return canGoThrough;
        }
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}
