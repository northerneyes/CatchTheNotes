package com.northerneyes.CatchTheNotes.view.MenuRenderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.Services.SettingsService;
import com.northerneyes.CatchTheNotes.controller.WorldController;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.Menu.MainMenu;
import com.northerneyes.CatchTheNotes.view.IRenderer;
import com.northerneyes.CatchTheNotes.view.TextRenderer;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 30.06.13
 * Time: 11:41
 * To change this template use File | Settings | File Templates.
 */
public class MainMenuRenderer implements IRenderer {
    private final float smallSize;
    private final float mediumSize;
    private final float largeSize;
    private final SettingsService settingService;
    private final float medalSize;
    private final Rectangle[] bounds;
    private float ppuY;
    private final float coef;
    private final NinePatch btnPatch;
//    private final Color btnColor;
//    private final Color btnHoverColor;
//    private final Color btnPressedColor;


    private TextRenderer[] songRenderers;
    private HashMap<Integer, TextureRegion> medalsTextures;
    private MainMenu menu;
    private TextRenderer textRenderer;
    private float ppuX;
    Vector2 textPosition = new Vector2();
    public MainMenuRenderer(HashMap<Integer, TextureRegion> medalsTextures, MainMenu mainMenu, TextRenderer textRenderer, float ppuX, float ppuY, float coeff) {
        settingService =  CatchTheNotes.getSettingService();
        this.medalsTextures = medalsTextures;
        this.menu = mainMenu;
        this.textRenderer = textRenderer;
        this.ppuX = ppuX;
        this.ppuY = ppuY;
        this.coef = coeff;

        btnPatch = new NinePatch( new Texture(Gdx.files.internal("images/btn.9.png")), 16, 16, 16, 16);

        bounds = new Rectangle[4];
        mediumSize =  CatchTheNotes.getContentManager().getDimension("medium_size");
        smallSize =  CatchTheNotes.getContentManager().getDimension("small_size");
        largeSize =   CatchTheNotes.getContentManager().getDimension("large_size");
        medalSize = CatchTheNotes.getContentManager().getDimension("medal_text_size");

        textRenderer.setText(menu.PlayText, menu.PlayTextColor, menu.PlayPosition, mediumSize, TextRenderer.TextAlign.CENTER);
        bounds[0] = textRenderer.getBounds();

        songRenderers = new TextRenderer[3];
        textRenderer.setText(menu.SongText, menu.SongTextColor, menu.SongTextPosition, smallSize, TextRenderer.TextAlign.RIGHT);
        Vector2 position = new Vector2(getShift(textRenderer.getBounds()) + 0.5f, menu.SongTextPosition.y);
        for (int i = 0; i < songRenderers.length; i++)
        {
            songRenderers[i] = new TextRenderer(textRenderer.getFont(), ppuX, ppuY, coeff);

            songRenderers[i].setText(menu.SongsName[i], menu.SongTextColor, position, smallSize, TextRenderer.TextAlign.LEFT);
            menu.SongNamePositions[i] = position;
            bounds[i+1] = songRenderers[i].getBounds();
            position =  new Vector2(getShift(bounds[i+1]) + 0.5f,  menu.SongTextPosition.y);

        }
        mainMenu.setBounds(bounds);
    }

    private void closeInfo() {
        int start = songRenderers.length;
        if(settingService.getUnlockLevel() < 1)
            start = 1;
        else if (settingService.getUnlockLevel() >= 1 && settingService.getUnlockLevel() < 5)
            start = 2;
        for (int i = 0; i < songRenderers.length; i++)
        {
            songRenderers[i].setText(menu.SongsName[i]);
            songRenderers[i].shiftRight(0);
        }
        for (int i = start; i < songRenderers.length; i++)
        {
            songRenderers[i].setText("?");
            songRenderers[i].shiftRight(bounds[i+1].width/2 - 5);
        }
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
       menu.update(Gdx.graphics.getDeltaTime());
        closeInfo();
       if(menu.hasMedals())
       {
           renderMedals(spriteBatch);
       }

       textRenderer.setText(menu.AppNameText, menu.AppNameTextColor, menu.AppNamePosition, largeSize, TextRenderer.TextAlign.CENTER);
       textRenderer.render(spriteBatch);

        textRenderer.setText(menu.PlayText, menu.PlayTextColor, menu.PlayPosition, mediumSize, TextRenderer.TextAlign.CENTER);
        textRenderer.render(spriteBatch);

        textRenderer.setText(menu.SongText, menu.SongTextColor, menu.SongTextPosition, smallSize, TextRenderer.TextAlign.RIGHT);
        textRenderer.render(spriteBatch);

//        spriteBatch.draw(btnPatch.getTexture(), menu.getBounds()[1].x, menu.getBounds()[1].y, menu.getBounds()[1].width, menu.getBounds()[1].height );


        for (int i= 0; i < songRenderers.length; i++) {
            spriteBatch.begin();
            btnPatch.setColor(getBtnColor(menu.getCurrentSongIndex(), menu.HoverSongIndex,  i));
            spriteBatch.setColor(getBtnColor(menu.getCurrentSongIndex(), menu.HoverSongIndex, i));
            btnPatch.draw(spriteBatch, menu.getBounds()[i+1].x - 0.4f*ppuX,
                    menu.getBounds()[i+1].y -  0.5f*menu.getBounds()[i+1].height, menu.getBounds()[i+1].width + 0.8f*ppuX, menu.getBounds()[i+1].height*2f);
            spriteBatch.end();

            songRenderers[i].render(spriteBatch);
        }

        int maxScore = settingService.getMaxScore();
        if(maxScore > 0)
        {
            String scorePoints = String.format("%d", settingService.getMaxScore());
            String maxScoreString =   CatchTheNotes.getContentManager().getString("max_score");
            textRenderer.setText(maxScoreString, menu.scoreColor, textPosition.set(WorldController.SOURCE_COUNT/2f, 3f), smallSize, TextRenderer.TextAlign.RIGHT);
            textRenderer.render(spriteBatch);
            textRenderer.setText(scorePoints,  menu.pointsColor, textPosition.set(WorldController.SOURCE_COUNT/2f + 0.2f, 3f), smallSize, TextRenderer.TextAlign.LEFT);
            textRenderer.render(spriteBatch);
        }

    }
    public void renderMedals(SpriteBatch spriteBatch)
    {
        float shift = 0;
        for (int i = 0; i < settingService.MaxMedals; i++)
        {
            if(menu.hasMedal(i))
            {
                renderMedal(spriteBatch, i, menu.MedalsPosition.x - shift, menu.MedalsPosition.y);
                shift += menu.MedalSize / 2;
            }
        }
       
        if(settingService.getRedStars() != 0)
        {
            renderMedal(spriteBatch, 4, menu.MedalsPosition.x - shift, menu.MedalsPosition.y);
        }
    }


    public void renderMedal(SpriteBatch spriteBatch, int type, float x, float y)
    {
        TextureRegion region = medalsTextures.get(type);
        float width = menu.MedalSize*ppuX;
        float height = menu.MedalSize*ppuY;
        spriteBatch.begin();
        spriteBatch.setColor(1f,1f,1f,1f);
        spriteBatch.draw(region, x*coef - width/2,
                y*ppuY - height/2, menu.MedalSize*ppuX,  menu.MedalSize*ppuY);
        spriteBatch.setColor(1);
        spriteBatch.end();
        textRenderer.setText(menu.getMedalText(type, x), medalSize, TextRenderer.TextAlign.CENTER);
        textRenderer.render(spriteBatch);
    }


    private Color getBtnColor(int pressedIndex, int hoverIndex, int index) {

        if( index == pressedIndex)
        {
            return menu.btnPressedColor;
        }
        else if(index == hoverIndex)
        {
            return menu.btnHoverColor;
        }
        return menu.btnColor;
    }

    private static NinePatch processNinePatchFile(String fname) {
        final Texture t = new Texture(Gdx.files.internal(fname));
        final int width = t.getWidth() - 2;
        final int height = t.getHeight() - 2;
        return new NinePatch(new TextureRegion(t, 1, 1, width, height), 3, 3, 3, 3);
    }
}
