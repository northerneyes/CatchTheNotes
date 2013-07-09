package com.northerneyes.CatchTheNotes.view.MenuRenderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.Menu.GameMenu;
import com.northerneyes.CatchTheNotes.view.IRenderer;
import com.northerneyes.CatchTheNotes.view.TextRenderer;

/**
 * Created by George on 24.06.13.
 */
public class GameMenuRenderer implements IRenderer {
    private final float size;
    private GameMenu menu;
    private TextRenderer textRenderer;
    private Color color = new Color(1f, 1f, 1f, 1f);

    public GameMenuRenderer(GameMenu menu, TextRenderer textRenderer) {
        this.menu = menu;
        this.textRenderer = textRenderer;
        size =  CatchTheNotes.getContentManager().getDimension("small_size");
        textRenderer.setText(menu.getPauseText(), color, menu.getPausePosition(), size, TextRenderer.TextAlign.LEFT);
        menu.setPauseBounds(textRenderer.getBounds());
    }

    @Override
    public void update(IEntity entity) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        textRenderer.setText(menu.getPauseText(), color, menu.getPausePosition(), size, TextRenderer.TextAlign.LEFT);
        textRenderer.render(spriteBatch);
    }
}
