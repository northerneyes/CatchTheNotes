package com.northerneyes.CatchTheNotes.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.Services.ScoreManager;
import com.northerneyes.CatchTheNotes.controller.WorldController;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.World;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 06.07.13
 * Time: 20:22
 * To change this template use File | Settings | File Templates.
 */
public class GameInfoRenderer  implements IRenderer {

    private final float smallSize;
    private final String score;
    private final float songSize;
    private ScoreManager scoreManager;
    private World world;
    private TextRenderer textRenderer;
    private float height;
    private Color pointsColor = new Color(203f/255f, 1f, 203/255f, 1f);
    private Color scoreColor = new Color(1f, 1f, 1f, 1f);
    private Vector2 textPosition = new Vector2();
    private Color comboColor = new Color(1f, 1f, 0.66f, 1f);
    private Color songColor = new Color(1f, 1f, 1f, 0.8f);
    public GameInfoRenderer(World world, TextRenderer textRenderer, float ppuX, float ppuY, float coeff, float height) {
        this.world = world;
        this.textRenderer = textRenderer;
        this.height = height;
        score =   CatchTheNotes.getContentManager().getString("score");
        smallSize =  CatchTheNotes.getContentManager().getDimension("small_size");
        songSize =  CatchTheNotes.getContentManager().getDimension("medal_text_size");
    }

    @Override
    public void update(IEntity scoreManager) {
        this.scoreManager = (ScoreManager)scoreManager;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        String combo = String.format("x%d", scoreManager.getCombo());
            String scorePoints = String.format("%d", scoreManager.getScore());


            textRenderer.setText(score, scoreColor, textPosition.set(WorldController.SOURCE_COUNT/2f, 1f), smallSize, TextRenderer.TextAlign.RIGHT);
            textRenderer.render(spriteBatch);
            textRenderer.setText(scorePoints,  pointsColor, textPosition.set(WorldController.SOURCE_COUNT/2f + 0.5f, 1f), smallSize, TextRenderer.TextAlign.LEFT);
            textRenderer.render(spriteBatch);


            textRenderer.setText(world.getFormattedCurrentSong(), songColor, textPosition.set(WorldController.SOURCE_COUNT-0.8f, height - 0.5f), songSize, TextRenderer.TextAlign.RIGHT);
            textRenderer.render(spriteBatch);
            textRenderer.setText(combo, comboColor, textPosition.set(WorldController.SOURCE_COUNT, height), smallSize, TextRenderer.TextAlign.RIGHT);
            textRenderer.render(spriteBatch);
    }
}
