package com.northerneyes.CatchTheNotes.view.MenuRenderers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.Services.IContentManager;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.Menu.EndGameMenu;
import com.northerneyes.CatchTheNotes.model.Message;
import com.northerneyes.CatchTheNotes.model.MessageGroup;
import com.northerneyes.CatchTheNotes.view.IRenderer;
import com.northerneyes.CatchTheNotes.view.TextRenderer;

import java.util.HashMap;

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
    private final IContentManager contentManager;
    private float ppuX;
    private float ppuY;
    private final float coeff;
    private HashMap<Integer, TextureRegion> medalsTextures;
    private final EndGameMenu menu;
    private final float smallSize;

    public EndGameMenuRenderer(HashMap<Integer, TextureRegion> medalsTextures, EndGameMenu menu, float ppuX, float ppuY, BitmapFont font, float coeff) {
        this.medalsTextures = medalsTextures;
        this.menu = menu;
        this.ppuX = ppuX;
        this.ppuY = ppuY;
        this.coeff = coeff;
        contentManager = CatchTheNotes.getContentManager();
        mediumSize =  CatchTheNotes.getContentManager().getDimension("medium_size");
        smallSize =  CatchTheNotes.getContentManager().getDimension("small_size");
        textRenderer = new TextRenderer(font, ppuX, ppuY, coeff);

        textRenderer.setBounds(menu.SkipText, smallSize, TextRenderer.TextAlign.CENTER);
        textRenderer.setBounds(menu.PlayAgainText, mediumSize, TextRenderer.TextAlign.CENTER);
        textRenderer.setBounds(menu.ChangeOptionsText, mediumSize, TextRenderer.TextAlign.CENTER);
    }

    @Override
    public void update(IEntity entity) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

        if(menu.Finished)
        {
            textRenderer.setText(menu.PlayAgainText, mediumSize, TextRenderer.TextAlign.CENTER);
            textRenderer.render(spriteBatch);
            textRenderer.setText(menu.ChangeOptionsText, mediumSize, TextRenderer.TextAlign.CENTER);
            textRenderer.render(spriteBatch);
            return;
        }

        textRenderer.setText(menu.SkipText, smallSize, TextRenderer.TextAlign.CENTER);
        textRenderer.render(spriteBatch);

        MessageGroup group =  menu.currentMessageGroup;
        if(group != null)
        {
            for (int index = 0; index < group.size(); index++)
            {
                Message msg = group.get(index);
                if(msg.Text.equals(contentManager.getString("super_duper")))
                {
                    spriteBatch.begin();
                    TextureRegion region = medalsTextures.get(4);
                    float width = menu.MedalSize*ppuX;
                    float height = menu.MedalSize*ppuY;
                    spriteBatch.draw(region, msg.Position.x*coeff - width/2,
                            (msg.Position.y - 5)*ppuY - height/2, menu.MedalSize*ppuX,  menu.MedalSize*ppuY);
                    spriteBatch.end();
                }
                if(msg.Text.equals(contentManager.getString("congratulations")) &&  menu.getMedal() != 0)
                {
                    spriteBatch.begin();
                    TextureRegion region = medalsTextures.get(menu.getMedal() - 1);
                    float width = menu.MedalSize*ppuX;
                    float height = menu.MedalSize*ppuY;
                    spriteBatch.draw(region, msg.Position.x*coeff - width/2,
                            (msg.Position.y - 5)*ppuY - height/2, menu.MedalSize*ppuX,  menu.MedalSize*ppuY);
                    spriteBatch.end();

                    menu.MedalText.Position.set(msg.Position.x, msg.Position.y- 5);
                    textRenderer.setText(menu.MedalText, smallSize, TextRenderer.TextAlign.CENTER);
                    textRenderer.render(spriteBatch);
                }

                textRenderer.setText(msg, mediumSize, TextRenderer.TextAlign.CENTER);
                textRenderer.render(spriteBatch);
            }
        }

    }
}
