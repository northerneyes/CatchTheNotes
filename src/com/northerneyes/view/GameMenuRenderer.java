package com.northerneyes.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.northerneyes.model.IEntity;
import com.northerneyes.model.Menu.GameMenu;

/**
 * Created by George on 24.06.13.
 */
public class GameMenuRenderer implements IRenderer {
    private GameMenu menu;
    private TextRenderer textRenderer;
    private Color color = new Color(1f, 1f, 1f, 1f);

    public GameMenuRenderer(GameMenu menu, TextRenderer textRenderer) {
        this.menu = menu;
        this.textRenderer = textRenderer;
    }

    @Override
    public void update(IEntity entity) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        textRenderer.setText(menu.getPauseText(), color, menu.getPausePosition(), 0.5f, TextRenderer.TextAlign.LEFT);
        textRenderer.render(spriteBatch);
    }
}
