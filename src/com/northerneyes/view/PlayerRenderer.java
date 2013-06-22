package com.northerneyes.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.northerneyes.controller.WorldController;
import com.northerneyes.model.IEntity;
import com.northerneyes.model.Player;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.06.13
 * Time: 12:18
 * To change this template use File | Settings | File Templates.
 */
public class PlayerRenderer implements IRenderer {

    private final float coef;
    Player player;
    TextureRegion texture;
    private float ppuX;
    private float ppuY;

    public void update(IEntity player)
    {
        this.player = (Player) player;
    }

    public PlayerRenderer(TextureRegion texture, float ppuX, float ppuY, float CAMERA_WIDTH) {
        this.texture = texture;
        this.ppuX = ppuX;
        this.ppuY = ppuY;
        this.coef = ppuX*(CAMERA_WIDTH / WorldController.SOURCE_COUNT);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.setColor(Color.YELLOW);

        spriteBatch.draw(texture, (player.Position.x - player.Size/2)*coef,
                (player.Position.y - player.Size/2)*ppuY, player.Size*ppuX, player.Size*ppuY);
        spriteBatch.end();
    }
}
