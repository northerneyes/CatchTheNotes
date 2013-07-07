package com.northerneyes.CatchTheNotes.view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.northerneyes.CatchTheNotes.MyGame;
import com.northerneyes.CatchTheNotes.R;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.Menu.EndGameMenu;
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

    public MessageHolderRenderer(MessageHolder messageHodler, float ppuX, float ppuY,BitmapFont font, float  CAMERA_WIDTH) {
        this.messageHodler = messageHodler;
        mediumSize =  MyGame.getAppContext().getResources().getDimension(R.dimen.medium_size);
        textRenderer = new TextRenderer(font,ppuX, ppuY, CAMERA_WIDTH);
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
