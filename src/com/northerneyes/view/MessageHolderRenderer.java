package com.northerneyes.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.northerneyes.CatchTheNotes.MyGame;
import com.northerneyes.CatchTheNotes.R;
import com.northerneyes.model.IEntity;
import com.northerneyes.model.Menu.Message;
import com.northerneyes.model.MessageHolder;
import com.northerneyes.model.Note;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 02.07.13
 * Time: 23:01
 * To change this template use File | Settings | File Templates.
 */
public class MessageHolderRenderer implements IRenderer  {
    private final float mediumSize;
    private TextRenderer textRenderer;
    private MessageHolder messageHodler;

    public MessageHolderRenderer(float ppuX, float ppuY,BitmapFont font, float  CAMERA_WIDTH) {

        mediumSize =  Float.parseFloat(MyGame.getAppContext().getResources().getString(R.string.medium_size));
        textRenderer = new TextRenderer(font,ppuX, ppuY, CAMERA_WIDTH);
    }

    @Override
    public void update(IEntity entity) {
        messageHodler = (MessageHolder)entity;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        for (int index = 0; index < messageHodler.messages.size(); index++)
        {
            Message msg= messageHodler.messages.get(index);
            msg.getTwenManager().update(Gdx.graphics.getDeltaTime());
            textRenderer.setText(msg.Text, msg.getColor(), msg.Position, mediumSize, TextRenderer.TextAlign.CENTER);
            textRenderer.render(spriteBatch);
        }
    }
}
