package com.devNNP.Objects;

import com.devNNP.GameFrames.GameFrame;
import com.devNNP.Handlers.RenderHandler;
import com.devNNP.Helpers.Rectangle;
import com.devNNP.Listeners.KeyBoardListener;
import com.devNNP.Map;
import com.devNNP.Sprite;
import com.devNNP.Tiles;

public class Player implements GameObject {

    private Rectangle playerRectangle;
    private int speed = 10;
    private Sprite sprite;
    private AnimatedSprite animatedSprite;
    // 0 - RIGHT; 1 - LEFT; 2 - UP; 3 - DOWN;
    private int direction = 0;
    private final int jumpSpeed = 30;
    private final int jumpDownSpeed = 10;
    private int jumpUpSpeed = 0;
    private int framesOfAnimation;
    private boolean isJumped = false;
    private GameFrame game;

    public Player(Sprite sprite, int framesOfAnimation, GameFrame game) {
        this.framesOfAnimation = framesOfAnimation;
        this.game = game;
        this.sprite = sprite;
        if (sprite instanceof AnimatedSprite) {
            animatedSprite = (AnimatedSprite) sprite;
        }
        playerRectangle = new Rectangle(0, 0, 16, 16);
        playerRectangle.generateGraphics(3, 0xFF00FF90);
        updateDirection();
    }



    private void updateDirection() {
        if (animatedSprite != null) {
            animatedSprite.setAnimationRange(direction * framesOfAnimation, direction * framesOfAnimation + framesOfAnimation - 1); // TODO: 03.03.2017 Change param 8 to mine own animated sprites
        }
    }

    @Override
    public void render(RenderHandler renderHandler, int xZoom, int yZoom) {
        if (animatedSprite != null) {
            renderHandler.renderSprite(animatedSprite, playerRectangle.getX(), playerRectangle.getY(), xZoom, yZoom, false);
        } else if (sprite != null) {
            renderHandler.renderSprite(sprite, playerRectangle.getX(), playerRectangle.getY(), xZoom, yZoom, false);
        } else {
            renderHandler.renderRectangle(playerRectangle, xZoom, yZoom, false);
        }
    }

    @Override
    public void update(GameFrame game) {
        KeyBoardListener keyBoardListener = game.getKeyBoardListener();
        boolean isMoved = false;
        int newDirection = direction;
        int x = playerRectangle.getX();
        int y = playerRectangle.getY();
        x = (int) Math.floor((x)/(16.0 * game.getxZoom()));
        y = (int) Math.floor((y)/(16.0 * game.getyZoom()));
        // TODO: 03.03.2017 Change UP to jump
        /*if (keyBoardListener.up()) {
            if (!keyBoardListener.down() && !isObsUp(x, y)) {
                playerRectangle.setY(playerRectangle.getY() - speed);
                newDirection = 2;
                isMoved = true;
            }
        } else if (keyBoardListener.down() && !isObsDown(x, y)) {
            playerRectangle.setY(playerRectangle.getY() + speed);
            newDirection = 3;
            isMoved = true;
        }*/

        isJumped = !isObsDown(x, y);
        System.out.println(isJumped);
        if (!isJumped) {
            if (keyBoardListener.up()) {
                isJumped = true;
                jumpUpSpeed = jumpSpeed;
                jump();
                playerRectangle.setY(playerRectangle.getY() - jumpUpSpeed);
            }
        } else {
            if (!isObsUp(x, y)) {
                playerRectangle.setY(playerRectangle.getY() - jumpUpSpeed);
                jumpUpSpeed--;
            } else {
                jumpUpSpeed = -3;
                playerRectangle.setY(playerRectangle.getY() - jumpUpSpeed);
            }
            if (isObsDown(x, y)) {
                isJumped = false;
                jumpUpSpeed = 0;
            }
        }

//        System.out.println(playerRectangle.getY());

        if (keyBoardListener.left() && !isObsLeft(x, y)) {
            if (!keyBoardListener.right()) {
                playerRectangle.setX(playerRectangle.getX() - speed);
                newDirection = 1;
                isMoved = true;
            }
        } else if (keyBoardListener.right() && !isObsRight(x, y)) {
            playerRectangle.setX(playerRectangle.getX() + speed);
            newDirection = 0;
            isMoved = true;
        }
        updateCamera(game.getRenderHandler().getCamera());
        if (isMoved) {
            animatedSprite.update(game);
        } else {
            animatedSprite.reset();
        }

        if (newDirection != direction) {
            direction = newDirection;
            updateDirection();
        }

//        System.out.println("x: " + playerRectangle.getX() + ", y: " + playerRectangle.getY());
//        isCollised();
    }

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }

    public void updateCamera(Rectangle camera) {
        camera.setX(playerRectangle.getX() - camera.getWidth() / 2);
        camera.setY(playerRectangle.getY() - camera.getHeight() / 2);
    }

    public int getJumpUpSpeed() {
        return jumpUpSpeed;
    }

    public void setJumpUpSpeed(int jumpUpSpeed) {
        this.jumpUpSpeed = jumpUpSpeed;
    }

    public void jump() {
        jumpUpSpeed = jumpSpeed;
    }

    public boolean isCollised() {
        int x = playerRectangle.getX();
        int y = playerRectangle.getY();
//        System.out.println("1) X: " + x + ", Y: " + y);
//        System.out.println(y + game.getCamera().getY());
//        System.out.println(x + game.getCamera().getX());
//        System.out.println(16.0 * game.getxZoom());
        x = (int) Math.floor((x)/(16.0 * game.getxZoom()));
        y = (int) Math.floor((y)/(16.0 * game.getyZoom()));
//        System.out.println("2) X: " + x + ", Y: " + y);
        game.getMap().getTile(x, y); // UP
//        System.out.println(y + (playerRectangle.getY() / 16 / game.yZoom));
        game.getMap().getTile(x, y + (int)Math.round(playerRectangle.getHeight() / 16.0 / game.yZoom) + 1); //DOWN
        System.out.println(Math.round(playerRectangle.getHeight() / 16.0 / game.yZoom));

//        System.out.println("Down: " + game.getMap().getTile(x, y + 1 + playerRectangle.getHeight()));
//        System.out.println("Right: " + game.getMap().getTile(playerRectangle.getX() + 1, playerRectangle.getY()));
//        System.out.println("Up: " + game.getMap().getTile(x, y - 1));
//        System.out.println("Left: " + game.getMap().getTile(playerRectangle.getX() - 1, playerRectangle.getY()));
//        System.out.println("Camera x: " + game.getCamera().getX() + ", camera y: " + game.getCamera().getY());
//        System.out.println("==============================================");
        return false;
    }

    // TODO: 06.03.2017 isObs... methods need to check 2 corners

    private boolean isObsUp(int x, int y) { // or  ||
        Map.MappedTile a = game.getMap().getTile(x, y);
        // TODO: 06.03.2017 Check left upper corner on collision
//        game.getMap().getTile(x + (int)Math.round(playerRectangle.getHeight()) / 16 / game.xZoom, y);
        Map.MappedTile b = game.getMap().getTile(x + (int)Math.round(playerRectangle.getHeight() / 16.0 / game.xZoom), y);
        if (a == null && b == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isObsDown(int x, int y) {
        // TODO: 06.03.2017 Realise method
        Map.MappedTile a = game.getMap().getTile(x, y + (int)Math.round(playerRectangle.getHeight() / 16.0 / game.yZoom) + 1);
        Map.MappedTile b = game.getMap().getTile(x + (int)Math.round(playerRectangle.getHeight() / 16.0 / game.xZoom),
                                            y + (int)Math.round(playerRectangle.getHeight() / 16.0 / game.yZoom) + 1);
        if (a == null && b == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isObsRight(int x, int y) {
        // TODO: 06.03.2017 Realise method
        Map.MappedTile a = game.getMap().getTile(x + (int)Math.round(playerRectangle.getHeight() / 16.0 / game.xZoom),
                                                y + (int)Math.round(playerRectangle.getHeight() / 16.0 / game.yZoom));
        Map.MappedTile b = game.getMap().getTile(x + (int)Math.round(playerRectangle.getHeight() / 16.0 / game.xZoom),
                                                                                                                        y);
        Map.MappedTile c = game.getMap().getTile(x + (int)Math.round(playerRectangle.getHeight() / 16.0 / game.xZoom),
                                        (y + y + (int)Math.round(playerRectangle.getHeight() / 16.0 / game.yZoom)) / 2);
        if (a == null && b == null && c == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isObsLeft(int x, int y) {
        // TODO: 06.03.2017 Realise method
        Map.MappedTile a = game.getMap().getTile(x,
                y + (int)Math.round(playerRectangle.getHeight() / 16.0 / game.yZoom));
        Map.MappedTile b = game.getMap().getTile(x,
                y);
        Map.MappedTile c = game.getMap().getTile(x,
                (y + y + (int)Math.round(playerRectangle.getHeight() / 16.0 / game.yZoom)) / 2);
        if (a == null && b == null && c == null) {
            return false;
        } else {
            return true;
        }
    }
}