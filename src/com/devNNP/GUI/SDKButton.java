package com.devNNP.GUI;

import com.devNNP.GameFrames.GameFrame;
import com.devNNP.Handlers.RenderHandler;
import com.devNNP.Helpers.Rectangle;
import com.devNNP.Sprite;

public class SDKButton extends GUIButton {

    private GameFrame game;
    private int tileID;
    private boolean isGreen = false;

    public SDKButton(GameFrame game, int tileID, Sprite tileSprite, Rectangle region) {
        super(tileSprite, region, true);
        this.game = game;
        this.tileID = tileID;
        region.generateGraphics(0xFFDB3D);
    }

    @Override
    public void activate() {
        game.changeTile(tileID);
    }

    @Override
    public void render(RenderHandler renderHandler, int xZoom, int yZoom, Rectangle interfaceRectangle) {
//        Rectangle offsetRectangle = new Rectangle(region.getX() + interfaceRectangle.getX(), region.getY() + interfaceRectangle.getY(),
//                region.getWidth(), region.getHeight());

        renderHandler.renderRectangle(region, interfaceRectangle, 1, 1, fixed);
        renderHandler.renderSprite(sprite,
                            region.getX() + interfaceRectangle.getX() + (xZoom - (xZoom - 1)) * region.getWidth() / 2 / xZoom,
                            region.getY() + interfaceRectangle.getY() + (yZoom - (yZoom - 1)) * region.getHeight() / 2 / yZoom,
                            xZoom - 1,
                            yZoom - 1,
                            fixed);
    }

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
        if (mouseRectangle.intersects(region)) {
//            System.out.println(mouseRectangle);
            activate(); // TODO: 04.03.2017 Realise activate function
            return true;
        }
        return false;
    }

    @Override
    public void update(GameFrame game)
    {
        if(tileID == game.getSelectedTile())
        {
            if(!isGreen)
            {
                region.generateGraphics(0x67FF3D);
                isGreen = true;
            }
        }
        else
        {
            if(isGreen)
            {
                region.generateGraphics(0xFFDB3D);
                isGreen = false;
            }
        }
    }
}
