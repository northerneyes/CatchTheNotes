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
import com.northerneyes.view.TextRenderer.TextAlign;
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
    private float CAMERA_WIDTH;
    private Color backgroundColor = new Color(1f, 1f, 0.66f, 0.5f);
    private Color comboColor = new Color(1f, 1f, 0.66f, 1f);

    private Color badPulseColor = new Color(102f/255f, 0f, 0f, 0.5f);
    private Color goodPulseColor = new Color(1f, 1f, 0.66f, 0.3f);
    private Color normalPulseColor = comboColor;
    private Color suctionPulseColor = new Color(1f, 0f, 1f, 0.5f);

    private Color scoreColor = new Color(203f/255f, 1f, 203/255f, 1f);

    private Vector2 textPosition = new Vector2();
    public void update(IEntity player)
    {
        this.player = (Player) player;
    }

    public PlayerRenderer(TextureRegion texture, TextRenderer textRenderer, float ppuX, float ppuY, float CAMERA_WIDTH) {
        this.texture = texture;
        this.textRenderer = textRenderer;
        this.ppuX = ppuX;
        this.ppuY = ppuY;
        this.CAMERA_WIDTH = CAMERA_WIDTH;
        this.coef = ppuX*(CAMERA_WIDTH / WorldController.SOURCE_COUNT);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

        if(player.ShowGameInfo)
        {
            String score = String.format("%d", player.getScore());
            textRenderer.setText(score, scoreColor, textPosition.set(WorldController.SOURCE_COUNT/2, 1f), 0.5f, TextAlign.CENTER);
            textRenderer.render(spriteBatch);
        }

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
        textRenderer.setText(combo, comboColor, textPosition.set(player.Position.x, player.Position.y), player.Size * 0.15f, TextAlign.CENTER);
        textRenderer.render(spriteBatch);

        //and draw outline
        if(player.Type != Player.PulseType.NONE)
        {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Circle);
                shapeRenderer.setColor(getOutlineColor(player.Type));
                shapeRenderer.circle(x, y, player.getPulseCoef() * height / 3, 30);
                shapeRenderer.end();

            if(player.Type != Player.PulseType.NORMAL)
            {
                Gdx.gl.glEnable(GL10.GL_BLEND);
                Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.begin(ShapeRenderer.ShapeType.FilledCircle);
                shapeRenderer.setColor(getOutlineColor(player.Type));
                shapeRenderer.filledCircle(x, y, player.getPulseCoef() * height / 3, 30);
                shapeRenderer.end();
                Gdx.gl.glDisable(GL10.GL_BLEND);
            }
        }

    }

    private Color getOutlineColor(Player.PulseType type) {
        switch (type)
        {
            case GOOD:
                return goodPulseColor;
            case BAD:
                return badPulseColor;
            case SUCTION:
                return suctionPulseColor;
            case NORMAL:
                return normalPulseColor;
        }
        return normalPulseColor;
    }
}
