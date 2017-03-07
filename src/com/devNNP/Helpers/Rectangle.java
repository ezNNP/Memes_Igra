package com.devNNP.Helpers;

import com.devNNP.GameFrames.GameFrame;
import com.devNNP.Main;

public class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;
    private int[] pixels;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle() {
        this(0, 0, 0, 0);
    }

    public boolean intersects(Rectangle otherRectangle) {
        if (otherRectangle.getX() - x <= otherRectangle.getWidth()
                && y - otherRectangle.getY() <= otherRectangle.getHeight() && y - otherRectangle.getY() > 0
                && x - otherRectangle.getX() <= otherRectangle.getWidth() && x - otherRectangle.getX() > 0
                && otherRectangle.getY() - y <= otherRectangle.getHeight()) {
            return true;
        }

        // TODO: 05.03.2017 Write the same intersect on horizontal check
        // TODO: 05.03.2017 Check this later

        /*if (x > otherRectangle.getX() + otherRectangle.getWidth() || otherRectangle.getWidth() > x + width) {
            return false;
        }

        if (y > otherRectangle.getY() + otherRectangle.getHeight() || otherRectangle.getHeight() > y + height) {
            return false;
        }*/

        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void generateGraphics(int color) {
        pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = color;
            }
        }
    }

    public int[] getPixels() {
        if (pixels != null) {
            return pixels;
        } else {
            try {
                throw new NullPointerException();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void generateGraphics(int borderWidth, int color) {
        pixels = new int[width * height];
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = GameFrame.alpha;
        }
        for(int y = 0; y < borderWidth; y++)
            for(int x = 0; x < width; x++)
                pixels[x + y * width] = color;

        for(int y = 0; y < height; y++)
            for(int x = 0; x < borderWidth; x++)
                pixels[x + y * width] = color;

        for(int y = 0; y < height; y++)
            for(int x = width - borderWidth; x < width; x++)
                pixels[x + y * width] = color;

        for(int y = height - borderWidth; y < height; y++)
            for(int x = 0; x < width; x++)
                pixels[x + y * width] = color;
    }
}
