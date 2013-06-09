package com.northerneyes.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    Player player;
    TextureRegion texture;
    private float ppuX;
    private float ppuY;

    public void update(IEntity player)
    {
        this.player = (Player) player;
    }

    public PlayerRenderer(TextureRegion texture, float ppuX, float ppuY) {
        this.texture = texture;
        this.ppuX = ppuX;
        this.ppuY = ppuY;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.setColor(Color.YELLOW);
        spriteBatch.draw(texture, (player.getPosition().x - Player.SIZE/2)*ppuX,
                (player.getPosition().y - Player.SIZE/2)*ppuY, Player.SIZE*ppuX,Player.SIZE*ppuY);
        spriteBatch.end();
    }
}
