package com.northerneyes.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.controller.WorldController;
import com.northerneyes.model.IEntity;

import java.util.Vector;

/**
 * Created by George on 23.06.13.
 */
public class TextRenderer implements IRenderer {

    private final BitmapFont font;
    private final float coef;
    private String text;
    private Color textColor;
    private Vector2 position;
    private float size;

    private float ppuX;
    private float ppuY;
    private float CAMERA_WIDTH;

    public TextRenderer(float ppuX, float ppuY, float CAMERA_WIDTH) {
        this.ppuX = ppuX;
        this.ppuY = ppuY;
        this.CAMERA_WIDTH = CAMERA_WIDTH;
        this.coef = ppuX*(CAMERA_WIDTH / WorldController.SOURCE_COUNT);

        font = new BitmapFont(Gdx.files.internal("data/PareBold.fnt"), false);
    }

    public void  setText(String text, Color textColor, Vector2 position, float size)
    {
        this.text = text;
        this.textColor = textColor;
        this.position = position;
        this.size = size;
    }

    @Override
    public void update(IEntity entity) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        font.setColor(textColor);
        font.setScale(size);
        BitmapFont.TextBounds bounds = this.font.getBounds(text);
        this.font.draw(spriteBatch, text, position.x * coef - bounds.width / 2, position.y * ppuY + bounds.height / 2);
        spriteBatch.end();
    }
}
