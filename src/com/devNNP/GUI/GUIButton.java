package com.devNNP.GUI;

import com.devNNP.GameFrames.GameFrame;
import com.devNNP.Handlers.RenderHandler;
import com.devNNP.Helpers.Rectangle;
import com.devNNP.Objects.GameObject;
import com.devNNP.Sprite;

public abstract class GUIButton implements GameObject {

    protected Sprite sprite;
    protected Rectangle region;
    protected boolean fixed;

    public GUIButton(Sprite sprite, Rectangle region, boolean fixed) {
        this.sprite = sprite;
        this.region = region;
        this.fixed = fixed;
    }

    @Override
    public void render(RenderHandler renderHandler, int xZoom, int yZoom) {

    }

    public void render(RenderHandler renderHandler, int xZoom, int yZoom, Rectangle interfaceRegion) {
        renderHandler.renderSprite(sprite, region.getX() + interfaceRegion.getX(), region.getY() + interfaceRegion.getY(),
                xZoom, yZoom, fixed);
    }

    @Override
    public void update(GameFrame game) {

    }

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom)
    {
        if(mouseRectangle.intersects(region)) {
            activate();
            return true;
        }

        return false;
    }

    public abstract void activate();
}
