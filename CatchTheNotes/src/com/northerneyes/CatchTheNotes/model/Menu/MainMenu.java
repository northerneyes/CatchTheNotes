package com.northerneyes.CatchTheNotes.model.Menu;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Cubic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.Services.SettingsService;
import com.northerneyes.CatchTheNotes.accessors.ColorAccessor;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.Message;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 30.06.13
 * Time: 9:09
 * To change this template use File | Settings | File Templates.
 */
public class MainMenu implements IEntity{

    public final Vector2 AppNamePosition;
    public final Vector2 PlayPosition;
    public final Vector2 SongTextPosition;

    public final Vector2[] SongNamePositions;
    public final Vector2 MedalsPosition;
    private final SettingsService settingService;

    public String AppNameText =  CatchTheNotes.getContentManager().getString("app_name");
    public String PlayText = CatchTheNotes.getContentManager().getString("play_menu");
    public String SongText =  CatchTheNotes.getContentManager().getString("song_menu");

    private Rectangle[] bounds;
    public Color AppNameTextColor = new Color(1f, 1f, 203f/255f, 1f);
    public Color PlayTextColor = new Color(1f, 1f, 1f, 1f);
    public Color SongTextColor = new Color(1f, 1f, 1f, 1f);
    public Color btnColor = new Color(1f, 1f, 1f, 0.4f);
    public Color btnHoverColor = new Color(1f, 1f, 1f, 0.5f);
    public Color btnPressedColor = new Color(1f, 1f, 1f, 0.6f);
    public Color pointsColor = new Color(203f/255f, 1f, 203/255f, 1f);
    public Color scoreColor = new Color(1f, 1f, 1f, 1f);

    public String SongsName[] =   {
            CatchTheNotes.getContentManager().getString("leave_in_the_wind"),
            CatchTheNotes.getContentManager().getString("centle"),
            CatchTheNotes.getContentManager().getString("letting_go")
    };

    public int CurrentSongIndex = 0;
    public int HoverSongIndex = -1;
    private TweenManager tweenManager = new TweenManager();
    public float MedalSize = 2.2f;

    public MainMenu(float width, float height) {
            settingService =  CatchTheNotes.getSettingService();

        MedalsPosition = new Vector2(width - 0.7f, height - 0.7f);

        AppNamePosition = new Vector2(width/2, height - 2);
        PlayPosition = new Vector2(AppNamePosition.x, AppNamePosition.y - 3);
        SongTextPosition = new Vector2(2, PlayPosition.y - 2);
        SongNamePositions = new Vector2[3];
    }

    public Message getMedalText(int type, float xPos)
    {
        if(type == 4)
            return new Message(String.format("x%d", settingService.getRedStars()), 2000,
                    settingService.getMedalColor(0), xPos, MedalsPosition.y);
        else
            return new Message(String.format("x%d", settingService.getMedalCount(type)), 2000,
               settingService.getMedalColor(type), xPos, MedalsPosition.y);
    }

    public void setBounds(Rectangle[] bounds)
    {
        this.bounds =  bounds;
    }

    public Rectangle[] getBounds()
    {
        return this.bounds;
    }

    public int getMenuState(float x, float y)
    {
        for(int i = 0; i < bounds.length; i++)
        {
            if(bounds[i].contains(x, y))
            {
                return i;
            }
        }
        return -1;
    }

    public void startFade(TweenCallback listener) {
        Timeline.createSequence()
                .push(Tween.set(AppNameTextColor, ColorAccessor.OPACITY).target(1f))
                .push(Tween.set(PlayTextColor, ColorAccessor.OPACITY).target(1f))
                .push(Tween.set(SongTextColor, ColorAccessor.OPACITY).target(1f))
                .push(Tween.set(btnColor, ColorAccessor.OPACITY).target(0.4f))
                .push(Tween.set(btnHoverColor, ColorAccessor.OPACITY).target(0.5f))
                .push(Tween.set(btnPressedColor, ColorAccessor.OPACITY).target(0.6f))
                .push(Tween.set(pointsColor, ColorAccessor.OPACITY).target(1f))
                .push(Tween.set(scoreColor, ColorAccessor.OPACITY).target(1f))
                .beginParallel()
                    .push(Tween.to(AppNameTextColor, ColorAccessor.OPACITY, 2f).target(0).ease(Cubic.OUT))
                    .push(Tween.to(PlayTextColor, ColorAccessor.OPACITY, 2f).target(0).ease(Cubic.OUT))
                    .push(Tween.to(SongTextColor, ColorAccessor.OPACITY, 2f).target(0).ease(Cubic.OUT))
                    .push(Tween.to(btnColor, ColorAccessor.OPACITY, 2f).target(0).ease(Cubic.OUT))
                    .push(Tween.to(btnHoverColor, ColorAccessor.OPACITY, 2f).target(0).ease(Cubic.OUT))
                    .push(Tween.to(btnPressedColor, ColorAccessor.OPACITY, 2f).target(0).ease(Cubic.OUT))
                    .push(Tween.to(pointsColor, ColorAccessor.OPACITY, 2f).target(0).ease(Cubic.OUT))
                    .push(Tween.to(scoreColor, ColorAccessor.OPACITY, 2f).target(0).ease(Cubic.OUT))
                .end()
                .setCallback(listener)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .start(tweenManager);
    }

    public void init() {
        AppNameTextColor.a = 1;
        PlayTextColor.a = 1;
        SongTextColor.a = 1;
        btnColor.a = 0.4f;
        btnHoverColor.a = 0.5f;
        btnPressedColor.a = 0.6f;
        pointsColor.a = 1;
        scoreColor.a = 1;
    }

    @Override
    public void update(float delta) {
        tweenManager.update(delta);
    }

    public boolean hasMedals() {
        for(int i = 0; i < settingService.MaxMedals; i++)
        {
            if(settingService.getMedalCount(i) > 0)
                return true;
        }
        return false;
    }

    public boolean hasMedal(int type) {
        return settingService.getMedalCount(type) > 0;
    }
}
