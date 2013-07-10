package com.northerneyes.CatchTheNotes;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.northerneyes.CatchTheNotes.Services.AudioAssetManager;
import com.northerneyes.CatchTheNotes.Services.IContentManager;
import com.northerneyes.CatchTheNotes.Services.SettingsService;
import com.northerneyes.CatchTheNotes.accessors.ColorAccessor;
import com.northerneyes.CatchTheNotes.accessors.MessageAccessor;
import com.northerneyes.CatchTheNotes.accessors.SpriteAccessor;
import com.northerneyes.CatchTheNotes.model.Message;
import com.northerneyes.CatchTheNotes.screens.GameScreen;

public class CatchTheNotes extends   Game  {
    private static SettingsService preferences;
    public GameScreen game;

    private static IContentManager contentManager;

    public CatchTheNotes(IContentManager contentManager){
        Tween.setWaypointsLimit(10);
        Tween.setCombinedAttributesLimit(3);
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.registerAccessor(Message.class, new MessageAccessor());
        Tween.registerAccessor(Color.class, new ColorAccessor());

        CatchTheNotes.contentManager = contentManager;
    }

    public static IContentManager getContentManager() {
        return contentManager;
    }


    public static SettingsService getSettingService() {
       if(preferences ==null){
            preferences = new SettingsService();
        }
        return preferences;
    }

    @Override
    public void create() {
        // TODO Auto-generated method stub

        AudioAssetManager.init();
        game = new GameScreen();
        setScreen(game);
    }
	/*
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}*/

	@Override
	public void dispose() {
        AudioAssetManager.dispose();
	}


}
