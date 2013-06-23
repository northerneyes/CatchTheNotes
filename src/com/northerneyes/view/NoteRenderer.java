package com.northerneyes.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.model.IEntity;
import com.northerneyes.model.Note;
import com.northerneyes.model.Player;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.06.13
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public class NoteRenderer implements IRenderer {

    private final float coef;
    private Note note;

    TextureRegion texture;
    private float ppuX;
    private float ppuY;

    public NoteRenderer(TextureRegion texture, float ppuX, float ppuY, float cameraWidth, int sourceCount) {
        this.texture = texture;
        this.ppuY = ppuY;
        this.ppuX = ppuX;
        this.coef = ppuX*(cameraWidth/sourceCount);

    }

    public void setTexture(TextureRegion texture)
    {
        this.texture = texture;
    }

    @Override
    public void update(IEntity entity) {
        note = (Note) entity;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        float x = note.Position.x*coef;
        float y = note.Position.y*ppuY;
        float width = note.Size*ppuX;
        float height = note.Size*ppuY;

        spriteBatch.begin();
        spriteBatch.setColor(note.Color);
        Vector2 origin = new Vector2(texture.getRegionWidth()/ 2, texture.getRegionHeight()/ 2);
        spriteBatch.draw(texture, x - width/2,  y - height/2,
                origin.x, origin.y,
                width, height, 1f, 1f, note.Angle);
        spriteBatch.end();
    }
}
