package com.northerneyes.CatchTheNotes.view.MenuRenderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.Menu.PauseMenu;
import com.northerneyes.CatchTheNotes.view.IRenderer;
import com.northerneyes.CatchTheNotes.view.TextRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 29.06.13
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */
public class PauseMenuRenderer implements IRenderer {

    private final float mediumSize;
    private PauseMenu menu;
    private TextRenderer textRenderer;

    public PauseMenuRenderer(PauseMenu menu,  TextRenderer textRenderer) {
        this.menu = menu;
        this.menu = menu;
        this.textRenderer = textRenderer;
        mediumSize =  CatchTheNotes.getContentManager().getDimension("medium_size");
        textRenderer.setText(menu.getResumeText(), menu.ResumeTextColor, menu.ResumePosition, mediumSize, TextRenderer.TextAlign.CENTER);
        menu.setResumeBounds(textRenderer.getBounds());
        textRenderer.setText(menu.getRestartText(), menu.RestartTextColor, menu.RestartPosition, mediumSize, TextRenderer.TextAlign.CENTER);
        menu.setRestartBounds(textRenderer.getBounds());
        textRenderer.setText(menu.getEndGameText(), menu.EndGameTextColor, menu.EndGamePosition, mediumSize, TextRenderer.TextAlign.CENTER);
        menu.setEndGameBounds(textRenderer.getBounds());
    }

    @Override
    public void update(IEntity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        textRenderer.setText(menu.getResumeText(), menu.ResumeTextColor, menu.ResumePosition, mediumSize, TextRenderer.TextAlign.CENTER);
        textRenderer.render(spriteBatch);
        textRenderer.setText(menu.getRestartText(), menu.RestartTextColor, menu.RestartPosition, mediumSize, TextRenderer.TextAlign.CENTER);
        textRenderer.render(spriteBatch);
        textRenderer.setText(menu.getEndGameText(), menu.EndGameTextColor, menu.EndGamePosition, mediumSize, TextRenderer.TextAlign.CENTER);
        textRenderer.render(spriteBatch);
    }
}
