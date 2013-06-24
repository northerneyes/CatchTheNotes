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
    public enum TextAlign {
        CENTER,
        LEFT,
        RIGHT;

        // Converts from an ordinal value to the ResponseCode
        public static TextAlign valueOf(int index) {
            TextAlign[] values = TextAlign.values();
            if (index < 0 || index >= values.length) {
                return CENTER;
            }
            return values[index];
        }
    }

    private final BitmapFont font;
    private final float coef;
    private String text;
    private Vector2 position;

    private float ppuY;
    private float shiftX = 0f;
    private float shiftY = 0f;

    public TextRenderer(float ppuX, float ppuY, float CAMERA_WIDTH) {

        this.ppuY = ppuY;
        this.coef = ppuX*(CAMERA_WIDTH / WorldController.SOURCE_COUNT);

        font = new BitmapFont(Gdx.files.internal("data/PareBold.fnt"), false);
    }

    public void  setText(String text, Color textColor, Vector2 position, float size, TextAlign textAlign)
    {
        this.text = text;
        this.position = position;

        font.setColor(textColor);
        font.setScale(size);
        BitmapFont.TextBounds bounds = this.font.getBounds(text);

        switch (textAlign)
        {
            case CENTER:
                shiftX = - bounds.width / 2;
                shiftY = bounds.height / 2;
                break;
            case LEFT:
                shiftX = 0;
                shiftY = 0;
                break;
            case RIGHT:
                shiftX = - bounds.width;
                shiftY = 0;
                break;
        }
    }

    @Override
    public void update(IEntity entity) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        this.font.draw(spriteBatch, text, position.x * coef + shiftX, position.y * ppuY + shiftY);
        spriteBatch.end();
    }
}
