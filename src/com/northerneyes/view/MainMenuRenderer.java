package com.northerneyes.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.northerneyes.CatchTheNotes.MyGame;
import com.northerneyes.CatchTheNotes.R;
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
    private MainMenu menu;
    private TextRenderer textRenderer;

    public MainMenuRenderer(MainMenu mainMenu, TextRenderer textRenderer, float ppuX) {
        this.menu = mainMenu;
        this.textRenderer = textRenderer;

        Rectangle[] bounds = new Rectangle[4];
        smallSize =   Float.parseFloat(MyGame.getAppContext().getResources().getString(R.string.small_size));
        mediumSize =  Float.parseFloat(MyGame.getAppContext().getResources().getString(R.string.medium_size));
        largeSize =   Float.parseFloat(MyGame.getAppContext().getResources().getString(R.string.large_size));
        textRenderer.setText(menu.PlayText, menu.PlayTextColor, menu.PlayPosition, mediumSize, TextRenderer.TextAlign.CENTER);
        bounds[0] = textRenderer.getBounds();

        textRenderer.setText(menu.SongText, menu.SongTextColor, menu.SongTextPosition, smallSize, TextRenderer.TextAlign.RIGHT);

        mainMenu.setBounds(bounds);
        //TODO: setPosition and bounds for songs
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
    }
}
