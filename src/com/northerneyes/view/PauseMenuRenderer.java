package com.northerneyes.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.northerneyes.model.IEntity;
import com.northerneyes.model.Menu.GameMenu;
import com.northerneyes.model.Menu.PauseMenu;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 29.06.13
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */
public class PauseMenuRenderer implements IRenderer {

    private PauseMenu menu;
    private TextRenderer textRenderer;

    public PauseMenuRenderer(PauseMenu menu,  TextRenderer textRenderer) {
        this.menu = menu;
        this.menu = menu;
        this.textRenderer = textRenderer;
        textRenderer.setText(menu.getResumeText(), menu.ResumeTextColor, menu.ResumePosition, 0.8f, TextRenderer.TextAlign.CENTER);
        menu.setResumeBounds(textRenderer.getBounds());
        textRenderer.setText(menu.getRestartText(), menu.RestartTextColor, menu.RestartPosition, 0.8f, TextRenderer.TextAlign.CENTER);
        menu.setRestartBounds(textRenderer.getBounds());
        textRenderer.setText(menu.getEndGameText(), menu.EndGameTextColor, menu.EndGamePosition, 0.8f, TextRenderer.TextAlign.CENTER);
        menu.setEndGameBounds(textRenderer.getBounds());
    }

    @Override
    public void update(IEntity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        textRenderer.setText(menu.getResumeText(), menu.ResumeTextColor, menu.ResumePosition, 0.8f, TextRenderer.TextAlign.CENTER);
        textRenderer.render(spriteBatch);
        textRenderer.setText(menu.getRestartText(), menu.RestartTextColor, menu.RestartPosition, 0.8f, TextRenderer.TextAlign.CENTER);
        textRenderer.render(spriteBatch);
        textRenderer.setText(menu.getEndGameText(), menu.EndGameTextColor, menu.EndGamePosition, 0.8f, TextRenderer.TextAlign.CENTER);
        textRenderer.render(spriteBatch);
    }
}
