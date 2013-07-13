package com.northerneyes.CatchTheNotes.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.northerneyes.CatchTheNotes.audio.VisualizationData;
import com.northerneyes.CatchTheNotes.controller.WorldController;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.World;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 13.07.13
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 */
public class SeekBarRenderer implements IRenderer {
    private World world;
    TextureRegion texture;
    private float ppuX;
    private float ppuY;
    private float coeff;
    private float height;
    float barWidth;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Color backgroundColor = new Color(204f/255f, 204f/255f, 1f, 0.8f);

    public SeekBarRenderer(World world, float ppuX, float ppuY,  float coeff, float height) {
        this.world = world;
        this.ppuX = ppuX;
        this.ppuY = ppuY;
        this.coeff = coeff;
        this.height = height;
        barWidth = 0.4f;
    }

    @Override
    public void update(IEntity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        //draw background
        Gdx.gl.glEnable(GL10.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.FilledRectangle);
        shapeRenderer.setColor(backgroundColor);
        shapeRenderer.filledRect(0, (height - barWidth)*ppuY,coeff* WorldController.SOURCE_COUNT*world.SongPosition, barWidth*ppuY);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL10.GL_BLEND);
    }
}
