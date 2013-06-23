package com.northerneyes.view;

import android.graphics.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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
    private TextRenderer textRenderer;
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    private float ppuX;
    private float ppuY;
    private Color backgroundColor = new Color(1f, 1f, 0.66f, 0.5f);
    private Color comboColor = new Color(1f, 1f, 0.66f, 1f);

    public void update(IEntity player)
    {
        this.player = (Player) player;
    }

    public PlayerRenderer(TextureRegion texture, TextRenderer textRenderer, float ppuX, float ppuY, float CAMERA_WIDTH) {
        this.texture = texture;
        this.textRenderer = textRenderer;
        this.ppuX = ppuX;
        this.ppuY = ppuY;
        this.coef = ppuX*(CAMERA_WIDTH / WorldController.SOURCE_COUNT);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        float x = player.Position.x*coef;
        float y = player.Position.y*ppuY;
        float width = player.Size*ppuX;
        float height = player.Size*ppuY;

        //draw background
        Gdx.gl.glEnable(GL10.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.FilledCircle);
        shapeRenderer.setColor(backgroundColor);
        shapeRenderer.filledCircle(x, y, height / 3, 30);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL10.GL_BLEND);

        //draw circle
        spriteBatch.begin();
        spriteBatch.setColor(comboColor);
        spriteBatch.draw(texture, x - width/2,
              y - height/2, width, height);
        spriteBatch.end();

        //draw combo
        String combo = String.format("x%d", player.getCombo());
        textRenderer.setText(combo, comboColor, new Vector2(player.Position.x, player.Position.y), player.Size * 0.2f);
        textRenderer.render(spriteBatch);
    }
}
