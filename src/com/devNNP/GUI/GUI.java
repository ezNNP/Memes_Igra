package com.devNNP.GUI;

import com.devNNP.GameFrames.GameFrame;
import com.devNNP.Handlers.RenderHandler;
import com.devNNP.Helpers.Rectangle;
import com.devNNP.Objects.GameObject;
import com.devNNP.Sprite;
import com.sun.istack.internal.Nullable;

public class GUI implements GameObject {

    private Sprite backgroundSprite;
    private GUIButton[] buttons;
    private Rectangle GUIRectangle = new Rectangle();
    private boolean fixed;

    public GUI(@Nullable Sprite backgroundSprite, GUIButton[] buttons, int x, int y, boolean fixed) {
        this.backgroundSprite = backgroundSprite;
        this.buttons = buttons;
        this.fixed = fixed;
        GUIRectangle.setX(x);
        GUIRectangle.setY(y);
        if (backgroundSprite != null) {
            GUIRectangle.setWidth(backgroundSprite.getWidth());
            GUIRectangle.setHeight(backgroundSprite.getHeight());
        }
    }

    public GUI(GUIButton[] buttons, int x, int y, boolean fixed) {
        this(null, buttons, x, y, fixed);
    }

    @Override
    public void render(RenderHandler renderHandler, int xZoom, int yZoom) {
        if (backgroundSprite != null) {
            renderHandler.renderSprite(backgroundSprite, GUIRectangle.getX(), GUIRectangle.getY(), xZoom, yZoom, fixed);
        }

        if (buttons != null) {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].render(renderHandler, xZoom, yZoom, GUIRectangle);
            }
        }
    }

    @Override
    public void update(GameFrame game) {
        if (buttons != null) {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].update(game);
            }
        }
    }

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {

        boolean stopChecking = false;

        if (!fixed) {
            mouseRectangle = new Rectangle(mouseRectangle.getX() + camera.getX(), mouseRectangle.getY() + camera.getY(), 1, 1);
        } else {
            mouseRectangle = new Rectangle(mouseRectangle.getX(), mouseRectangle.getY(), 1, 1);
        }

        if (GUIRectangle.getWidth() == 0 || GUIRectangle.getHeight() == 0 || mouseRectangle.intersects(GUIRectangle)) {
//            mouseRectangle.setX(mouseRectangle.getX() - GUIRectangle.getX());
//            mouseRectangle.setY(mouseRectangle.getY() - GUIRectangle.getY());

            for (int i = 0; i < buttons.length; i++) {
                boolean result = buttons[i].handleMouseClick(mouseRectangle, camera, xZoom, yZoom);
                if (stopChecking == false) {
                    stopChecking = result;
                }
            }
        }
        return stopChecking;
    }
}
