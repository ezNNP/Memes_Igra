package com.devNNP.Objects;


import com.devNNP.GameFrames.GameFrame;
import com.devNNP.Handlers.RenderHandler;
import com.devNNP.Helpers.Rectangle;

public interface GameObject {

    public void render(RenderHandler renderHandler, int xZoom, int yZoom);

    public void update(GameFrame game);

    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);
}
