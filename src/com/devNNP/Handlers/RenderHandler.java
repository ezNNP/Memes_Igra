package com.devNNP.Handlers;

import com.devNNP.GameFrames.GameFrame;
import com.devNNP.Helpers.Rectangle;
import com.devNNP.Sprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {

    private BufferedImage view;
    private int[] pixels;
    private Rectangle camera;

    public RenderHandler(int width, int height) {
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

        camera = new Rectangle(0, 0, width, height);

        /*for (int i = 0; i < pixels.length; i++) {
            pixels[i] = (int)(Math.random() * 0xFFFFFF);
        }*/
    }

    public void render(Graphics graphics) {
        /*for (int i = 0; i < pixels.length; i++) {
            pixels[i] = (int)(Math.random() * 0xFFFFFF);
        }*/

        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }

    public void renderRectangle(Rectangle rectangle, Rectangle offset, int xZoom, int yZoom, boolean fixed) {
        int[] imagePixels = rectangle.getPixels();
        renderArray(imagePixels, rectangle.getWidth(), rectangle.getHeight(),
                rectangle.getX() + offset.getX(), rectangle.getY() + offset.getY(), xZoom, yZoom, fixed);
    }

    public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom, boolean fixed) {
        int[] imagePixels = rectangle.getPixels();
        renderArray(imagePixels, rectangle.getWidth(), rectangle.getHeight(),
                rectangle.getX(), rectangle.getY(), xZoom, yZoom, fixed);
    }

    public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed) {
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        renderArray(imagePixels, image.getWidth(), image.getHeight(), xPosition, yPosition, xZoom, yZoom, fixed);
    }

    public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed) {
        renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPosition, yPosition, xZoom, yZoom, fixed);
    }

    public void renderSprite(Sprite sprite, int xPosition, int yPosition, int renderWidth, int renderHeight, int xZoom, int yZoom, boolean fixed, int xOffset, int yOffset) {
        renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), renderWidth, renderHeight, xPosition, yPosition,
                xZoom, yZoom, fixed, xOffset, yOffset);
    }

    private void setPixel(int pixel, int x, int y, boolean fixed) {
        if (!fixed) {
            if (x >= camera.getX() && y >= camera.getY() && x <= camera.getX() + camera.getWidth() && y <= camera.getY() + camera.getHeight()) {
                int pixelIndex = (x - camera.getX()) + (y - camera.getY()) * view.getWidth();
                if (pixels.length > pixelIndex && pixel != GameFrame.alpha)
                    pixels[pixelIndex] = pixel;
            }
        } else {
            if (x >= 0 && y >= 0 && x <= camera.getWidth() && y <= camera.getHeight()) {
                int pixelIndex = x + y * view.getWidth();
                if (pixels.length > pixelIndex && pixel != GameFrame.alpha)
                    pixels[pixelIndex] = pixel;
            }
        }
    }

    public int getPixel(int x, int y) {
        return pixels[x + y * view.getWidth()];
    }

    public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPostioin, int yPosition, int xZoom, int yZoom, boolean fixed) {
        for (int y = 0; y < renderHeight; y++) {
            for (int x = 0; x < renderWidth; x++) {
                for (int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++) {
                    for (int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++) {
                        setPixel(renderPixels[x + y * renderWidth], (x * xZoom) + xPostioin + xZoomPosition,
                                (y * yZoom) + yPosition + yZoomPosition, fixed);
                    }
                }
            }
        }
    }

    public void renderArray(int[] renderPixels, int imageWidth, int imageHeight, int renderWidth, int renderHeight, int xPosition, int yPosition,
                            int xZoom, int yZoom, boolean fixed, int xOffset, int yOffset)
    {
        for(int y = yOffset; y < yOffset + renderHeight; y++)
            for(int x = xOffset; x < xOffset + renderWidth; x++)
                for(int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++)
                    for(int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++)
                        setPixel(renderPixels[x + y * imageWidth], ((x - xOffset) * xZoom) + xPosition + xZoomPosition, (((y - yOffset) * yZoom) + yPosition + yZoomPosition), fixed);
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public Rectangle getCamera() {
        return camera;
    }
}
