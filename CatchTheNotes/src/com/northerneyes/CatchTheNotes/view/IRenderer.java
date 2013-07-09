package com.northerneyes.CatchTheNotes.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.northerneyes.CatchTheNotes.model.IEntity;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.06.13
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */
public interface IRenderer {
    public void update(IEntity entity);
    public void render(SpriteBatch spriteBatch);
}
