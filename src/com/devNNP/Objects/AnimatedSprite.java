package com.devNNP.Objects;

import com.devNNP.GameFrames.GameFrame;
import com.devNNP.Handlers.RenderHandler;
import com.devNNP.Helpers.Rectangle;
import com.devNNP.Sprite;
import com.devNNP.SpriteSheet;

import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements GameObject{

    private Sprite[] sprites;
    private int currentSprite;
    private int speed;
    private int counter = 0;

    private int startSprite = 0;
    private int endSprite;

    /**
     * @param sheet takes array of sprites
     * @param positions takes rectangle of 1 sprite
     * @param speed how many frames to change sprite
     */

    public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed) {
        sprites = new Sprite[positions.length];
        this.speed = speed;

        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Sprite(sheet, positions[i].getX(), positions[i].getY(), positions[i].getWidth(), positions[i].getHeight());
        }
        this.endSprite = positions.length - 1;
    }

    /**
     * @param sheet takes SpriteSheet
     * @param speed how many frames to change sprite
     */

    public AnimatedSprite(SpriteSheet sheet, int speed) {
        sprites = sheet.getLoadedSprites();
        this.speed = speed;
        this.endSprite = sprites.length - 1;
    }

    /**
     * @param images takes Array of sprites
     * @param speed how many frames to change sprite
     */
    public AnimatedSprite(BufferedImage[] images, int speed) {
        sprites = new Sprite[images.length];
        this.speed = speed;
        for (int i = 0; i < images.length; i++) {
            sprites[i] = new Sprite(images[i]);
        }
        this.startSprite = images.length - 1;
    }

    public void setAnimationRange(int startSprite, int endSprite) {
        this.startSprite = startSprite;
        this.endSprite = endSprite;
        reset();
    }

    @Override
    public void render(RenderHandler renderHandler, int xZoom, int yZoom) {

    }

    public void reset() {
        counter = 0;
        currentSprite = startSprite;
    }



    @Override
    public void update(GameFrame game) {
        counter++;
        if (counter >= speed) {
            counter = 0;
            incrementSprite();
        }
    }

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }

    public void incrementSprite() {
        currentSprite++;
        if (currentSprite >= endSprite) {
            currentSprite = startSprite;
        }
    }

    public int getWidth() {
        return sprites[currentSprite].getWidth();
    }

    public int getHeight() {
        return sprites[currentSprite].getHeight();
    }

    public int[] getPixels() {
        return sprites[currentSprite].getPixels();
    }
}
