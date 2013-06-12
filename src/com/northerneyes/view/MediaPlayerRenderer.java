package com.northerneyes.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.northerneyes.audio.MediaPlayer;
import com.northerneyes.audio.VisualizationData;
import com.northerneyes.model.IEntity;
import com.northerneyes.model.Player;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 10.06.13
 * Time: 21:25
 * To change this template use File | Settings | File Templates.
 */
public class MediaPlayerRenderer implements IRenderer{
    private final VisualizationData data;
    TextureRegion texture;
    private float ppuX;
    private float ppuY;
    float barWidth;

    public void update(IEntity player)
    {
    }

    public MediaPlayerRenderer(TextureRegion texture, float ppuX, float ppuY) {
        this.texture = texture;
        this.ppuX = ppuX;
        this.ppuY = ppuY;

        data = new VisualizationData(128);

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        MediaPlayer.GetVisualizationData(data);
        barWidth = ((float) 25 / data.Frequences.length)*ppuX;
        spriteBatch.begin();
        for (int i = 0; i < data.Frequences.length; i++) {
            spriteBatch.draw(texture.getTexture(), i * barWidth, 0, barWidth,
                    data.Frequences[i]*10*ppuY, 0, 0, 16, 5, false, false);
        }
        spriteBatch.end();
    }
}
