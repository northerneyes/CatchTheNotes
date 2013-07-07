package com.northerneyes.CatchTheNotes.view.MenuRenderers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.northerneyes.CatchTheNotes.MyGame;
import com.northerneyes.CatchTheNotes.R;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.Menu.EndGameMenu;
import com.northerneyes.CatchTheNotes.model.Message;
import com.northerneyes.CatchTheNotes.model.MessageGroup;
import com.northerneyes.CatchTheNotes.view.IRenderer;
import com.northerneyes.CatchTheNotes.view.TextRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 07.07.13
 * Time: 12:10
 * To change this template use File | Settings | File Templates.
 */
public class EndGameMenuRenderer  implements IRenderer {

    private final float mediumSize;
    private final TextRenderer textRenderer;
    private final EndGameMenu menu;

    public EndGameMenuRenderer(EndGameMenu menu, float ppuX, float ppuY, BitmapFont font, float  CAMERA_WIDTH) {
        this.menu = menu;
        mediumSize =  Float.parseFloat(MyGame.getAppContext().getResources().getString(R.string.medium_size));
        textRenderer = new TextRenderer(font, ppuX, ppuY, CAMERA_WIDTH);
    }

    @Override
    public void update(IEntity entity) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        MessageGroup group =  menu.currentMessageGroup;
        if(group != null)
        {
            for (int index = 0; index < group.size(); index++)
            {
                Message msg = group.get(index);
                textRenderer.setText(msg.Text, msg.getColor(), msg.Position, mediumSize, TextRenderer.TextAlign.CENTER);
                textRenderer.render(spriteBatch);
            }
        }
    }
}
