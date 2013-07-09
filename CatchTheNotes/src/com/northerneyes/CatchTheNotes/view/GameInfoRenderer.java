package com.northerneyes.CatchTheNotes.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.R;
import com.northerneyes.CatchTheNotes.Services.ScoreManager;
import com.northerneyes.CatchTheNotes.controller.WorldController;
import com.northerneyes.CatchTheNotes.model.IEntity;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 06.07.13
 * Time: 20:22
 * To change this template use File | Settings | File Templates.
 */
public class GameInfoRenderer  implements IRenderer {

    private final float smallSize;
    private ScoreManager scoreManager;
    private TextRenderer textRenderer;
    private float height;
    private Color pointsColor = new Color(203f/255f, 1f, 203/255f, 1f);
    private Color scoreColor = new Color(1f, 1f, 1f, 1f);
    private Vector2 textPosition = new Vector2();
    private Color comboColor = new Color(1f, 1f, 0.66f, 1f);

    public GameInfoRenderer(TextRenderer textRenderer, float ppuX, float ppuY, float CAMERA_WIDTH, float height) {
        this.textRenderer = textRenderer;
        this.height = height;
        smallSize =  CatchTheNotes.getContentManager().getDimension("small_size");
    }

    @Override
    public void update(IEntity scoreManager) {
        this.scoreManager = (ScoreManager)scoreManager;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        String combo = String.format("x%d", scoreManager.getCombo());
            String scorePoints = String.format("%d", scoreManager.getScore());
            String score =   CatchTheNotes.getContentManager().getString("score");
            //TODO: Create Padding!!

            textRenderer.setText(score, scoreColor, textPosition.set(WorldController.SOURCE_COUNT/2f, 1f), smallSize, TextRenderer.TextAlign.RIGHT);
            textRenderer.render(spriteBatch);
            textRenderer.setText(scorePoints,  pointsColor, textPosition.set(WorldController.SOURCE_COUNT/2f + 0.5f, 1f), smallSize, TextRenderer.TextAlign.LEFT);
            textRenderer.render(spriteBatch);

            textRenderer.setText(combo, comboColor, textPosition.set(WorldController.SOURCE_COUNT, height), smallSize, TextRenderer.TextAlign.RIGHT);
            textRenderer.render(spriteBatch);
    }
}
