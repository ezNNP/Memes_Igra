package com.devNNP;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private int[] pixels;
    private BufferedImage image;
    public final int SIZEX;
    public final int SIZEY;
    private Sprite[] loadedSprites = null;
    private boolean spritesLoaded = false;

    private int spriteSizeX;

    public SpriteSheet(BufferedImage sheetImage) {
        SIZEX = sheetImage.getWidth();
        SIZEY = sheetImage.getHeight();
        image = sheetImage;

        pixels = new int[SIZEX * SIZEY];
        pixels = sheetImage.getRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZEX);
    }

    public void loadSprites(int spriteSizeX, int spriteSizeY, int padding) {
        this.spriteSizeX = spriteSizeX;
        loadedSprites = new Sprite[(SIZEX / spriteSizeX) * (SIZEY / spriteSizeY)];
        int spriteID = 0;
        for (int y = 0; y < SIZEY; y += spriteSizeY + padding) {
            for (int x = 0; x < SIZEX; x += spriteSizeX + padding) {
                loadedSprites[spriteID] = new Sprite(this, x, y, spriteSizeX, spriteSizeY);
                spriteID++;
            }
        }

        this.spritesLoaded = true;
    }

    public Sprite getSprite(int x, int y) {
        if (spritesLoaded) {
            int spriteID = x + y * (SIZEX / spriteSizeX);
            if (spriteID < loadedSprites.length) {
                return loadedSprites[spriteID];
            } else {
                try {
                    throw new ArrayIndexOutOfBoundsException();
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Can't get sprite on " + spriteID + " position in the loadedSprites array.");
                    return null;
                }
            }
        } else {
            try {
                throw new NullPointerException();
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("Sprites haven't loaded yet...");
                return null;
            }
        }
    }

    public int[] getPixels() {
        return pixels;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Sprite[] getLoadedSprites() {
        return loadedSprites;
    }
}
