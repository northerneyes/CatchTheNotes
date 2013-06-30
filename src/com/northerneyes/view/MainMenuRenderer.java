package com.northerneyes.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.MyGame;
import com.northerneyes.CatchTheNotes.R;
import com.northerneyes.controller.WorldController;
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
    private final float smallSize;
    private final float mediumSize;
    private final float largeSize;
    private final float coef;
    private final TextRenderer[] songRenderers;
    private MainMenu menu;
    private TextRenderer textRenderer;
    private float ppuX;

    public MainMenuRenderer(MainMenu mainMenu, TextRenderer textRenderer, float ppuX, float ppuY, float CAMERA_WIDTH) {
        this.menu = mainMenu;
        this.textRenderer = textRenderer;
        this.ppuX = ppuX;
        this.coef = ppuX*(CAMERA_WIDTH / WorldController.SOURCE_COUNT);

        Rectangle[] bounds = new Rectangle[4];
        smallSize =   Float.parseFloat(MyGame.getAppContext().getResources().getString(R.string.small_size));
        mediumSize =  Float.parseFloat(MyGame.getAppContext().getResources().getString(R.string.medium_size));
        largeSize =   Float.parseFloat(MyGame.getAppContext().getResources().getString(R.string.large_size));
        textRenderer.setText(menu.PlayText, menu.PlayTextColor, menu.PlayPosition, mediumSize, TextRenderer.TextAlign.CENTER);
        bounds[0] = textRenderer.getBounds();

        songRenderers = new TextRenderer[3];
        textRenderer.setText(menu.SongText, menu.SongTextColor, menu.SongTextPosition, smallSize, TextRenderer.TextAlign.RIGHT);
        Vector2 position = new Vector2(getShift(textRenderer.getBounds()) + 0.5f, menu.SongTextPosition.y);
        for (int i = 0; i < songRenderers.length; i++)
        {
            songRenderers[i] = new TextRenderer(ppuX, ppuY, CAMERA_WIDTH);

            songRenderers[i].setText(menu.SongsName[i], menu.SongTextColor, position, smallSize, TextRenderer.TextAlign.LEFT);
            menu.SongNamePositions[i] = position;
            bounds[i+1] = songRenderers[i].getBounds();
            position =  new Vector2(getShift(bounds[i+1]) + 0.5f,  menu.SongTextPosition.y);
        }

        mainMenu.setBounds(bounds);
        //TODO: setPosition and bounds for songs
    }

    private float getShift(Rectangle bound) {
        return bound.x/coef + bound.width/coef;
    }

    @Override
    public void update(IEntity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

       textRenderer.setText(menu.AppNameText, menu.AppNameTextColor, menu.AppNamePosition, largeSize, TextRenderer.TextAlign.CENTER);
       textRenderer.render(spriteBatch);

        textRenderer.setText(menu.PlayText, menu.PlayTextColor, menu.PlayPosition, mediumSize, TextRenderer.TextAlign.CENTER);
        textRenderer.render(spriteBatch);

        textRenderer.setText(menu.SongText, menu.SongTextColor, menu.SongTextPosition, smallSize, TextRenderer.TextAlign.RIGHT);
        textRenderer.render(spriteBatch);

        for (TextRenderer songRenderer : songRenderers) {
            songRenderer.render(spriteBatch);
        }
    }
}
