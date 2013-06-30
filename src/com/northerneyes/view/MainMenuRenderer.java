package com.northerneyes.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.northerneyes.model.IEntity;
import com.northerneyes.model.Menu.MainMenu;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 30.06.13
 * Time: 11:41
 * To change this template use File | Settings | File Templates.
 */
public class MainMenuRenderer implements IRenderer{
    private MainMenu mainMenu;
    private TextRenderer textRenderer;

    public MainMenuRenderer(MainMenu mainMenu, TextRenderer textRenderer, float ppuX) {
        this.mainMenu = mainMenu;
        this.textRenderer = textRenderer;
    }

    @Override
    public void update(IEntity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
