package com.northerneyes.CatchTheNotes.view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.Message;
import com.northerneyes.CatchTheNotes.model.MessageHolder;

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

    public MessageHolderRenderer(MessageHolder messageHodler, float ppuX, float ppuY, BitmapFont font, float  coeff) {
        this.messageHodler = messageHodler;
        mediumSize =  CatchTheNotes.getContentManager().getDimension("medium_size");
        textRenderer = new TextRenderer(font,ppuX, ppuY, coeff);
    }

    @Override
    public void update(IEntity entity) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        for (int index = 0; index < messageHodler.messages.size(); index++)
        {
            Message msg= messageHodler.messages.get(index);
            textRenderer.setText(msg.Text, msg.getColor(), msg.Position, mediumSize, TextRenderer.TextAlign.CENTER);
            textRenderer.render(spriteBatch);
        }
    }
}
