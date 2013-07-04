package com.northerneyes.view;

import android.graphics.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.MyGame;
import com.northerneyes.CatchTheNotes.R;
import com.northerneyes.controller.WorldController;
import com.northerneyes.model.IEntity;
import com.northerneyes.model.Player;
import com.northerneyes.view.TextRenderer.TextAlign;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.06.13
 * Time: 12:18
 * To change this template use File | Settings | File Templates.
 */
public class PlayerRenderer implements IRenderer {

    private final float coef;
    private final TextureRegion circleTexture;
    private final TextureRegion squareTexture;
    Player player;
    private TextRenderer textRenderer;
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    private float ppuX;
    private float ppuY;
    private float CAMERA_WIDTH;
    private float height;
    private Color backgroundColor = new Color(1f, 1f, 0.66f, 0.5f);
    private Color comboColor = new Color(1f, 1f, 0.66f, 1f);

    private Color badPulseColor = new Color(102f/255f, 0f, 0f, 0.5f);
    private Color goodPulseColor = new Color(1f, 1f, 0.66f, 0.3f);
    private Color normalPulseColor = comboColor;
    private Color suctionPulseColor = new Color(1f, 0f, 1f, 0.5f);

    private Color pointsColor = new Color(203f/255f, 1f, 203/255f, 1f);
    private Color scoreColor = new Color(1f, 1f, 1f, 1f);

    private Vector2 textPosition = new Vector2();
    private float smallSize;
    private float playerCoeffSize;

    public void update(IEntity player)
    {
        this.player = (Player) player;
    }

    public PlayerRenderer(List<TextureRegion> texture, TextRenderer textRenderer, float ppuX, float ppuY, float CAMERA_WIDTH, float height) {
        this.circleTexture = texture.get(1);
        this.squareTexture = texture.get(0);
        this.textRenderer = textRenderer;
        this.ppuX = ppuX;
        this.ppuY = ppuY;
        this.CAMERA_WIDTH = CAMERA_WIDTH;
        this.height = height;
        this.coef = ppuX*(CAMERA_WIDTH / WorldController.SOURCE_COUNT);

        smallSize =   Float.parseFloat(MyGame.getAppContext().getResources().getString(R.string.small_size));
        playerCoeffSize =   Float.parseFloat(MyGame.getAppContext().getResources().getString(R.string.player_coef_size));
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

        String combo = String.format("%dx", player.getCombo());

        if(player.ShowGameInfo)
        {
            String scorePoints = String.format("%d", player.getScore());
            String score = MyGame.getAppContext().getString(R.string.score);
            //TODO: Create Padding!!

            textRenderer.setText(score, scoreColor, textPosition.set(WorldController.SOURCE_COUNT/2f, 1f), smallSize, TextAlign.RIGHT);
            textRenderer.render(spriteBatch);
            textRenderer.setText(scorePoints,  pointsColor, textPosition.set(WorldController.SOURCE_COUNT/2f + 0.5f, 1f), smallSize, TextAlign.LEFT);
            textRenderer.render(spriteBatch);

            textRenderer.setText(combo, comboColor, textPosition.set(WorldController.SOURCE_COUNT, height), smallSize, TextAlign.RIGHT);
            textRenderer.render(spriteBatch);
        }

        float x = player.Position.x*coef;
        float y = player.Position.y*ppuY;
        float width = player.Size*ppuX;
        float height = player.Size*ppuY;

        if(player.State == Player.PlayerState.NORMAL)
        {
            //draw background
            Gdx.gl.glEnable(GL10.GL_BLEND);
            Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

            shapeRenderer.begin(ShapeRenderer.ShapeType.FilledCircle);
            shapeRenderer.setColor(backgroundColor);
            shapeRenderer.filledCircle(x, y, height*0.45f, 30);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL10.GL_BLEND);

            spriteBatch.begin();
            spriteBatch.setColor(comboColor);
            spriteBatch.draw(circleTexture, x - width/2,
                      y - height/2, width, height);
            spriteBatch.end();
        }
        else
        {
            //draw background
            Gdx.gl.glEnable(GL10.GL_BLEND);
            Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

            shapeRenderer.begin(ShapeRenderer.ShapeType.FilledRectangle);
            shapeRenderer.setColor(backgroundColor);
            shapeRenderer.filledRect(x, y, height*0.45f, height*0.45f);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL10.GL_BLEND);

            spriteBatch.begin();
            spriteBatch.setColor(comboColor);
            spriteBatch.draw(squareTexture, x - width/2,
                    y - height/2, width, height);
            spriteBatch.end();
        }
        
        //draw combo
        textRenderer.setText(combo, comboColor, textPosition.set(player.Position.x, player.Position.y), player.Size * playerCoeffSize, TextAlign.CENTER);
        textRenderer.render(spriteBatch);

        //and draw outline
        if(player.Type != Player.PulseType.NONE)
        {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Circle);
                shapeRenderer.setColor(getOutlineColor(player.Type));
                shapeRenderer.circle(x, y, player.getPulseCoef() *  height*0.45f, 30);
                shapeRenderer.end();

            if(player.Type != Player.PulseType.NORMAL)
            {
                Gdx.gl.glEnable(GL10.GL_BLEND);
                Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.begin(ShapeRenderer.ShapeType.FilledCircle);
                shapeRenderer.setColor(getOutlineColor(player.Type));
                shapeRenderer.filledCircle(x, y, player.getPulseCoef() *  height*0.45f, 30);
                shapeRenderer.end();
                Gdx.gl.glDisable(GL10.GL_BLEND);
            }
        }

    }

    private Color getOutlineColor(Player.PulseType type) {
        switch (type)
        {
            case GOOD:
                return goodPulseColor;
            case BAD:
                return badPulseColor;
            case SUCTION:
                return suctionPulseColor;
            case NORMAL:
                return normalPulseColor;
        }
        return normalPulseColor;
    }
}
