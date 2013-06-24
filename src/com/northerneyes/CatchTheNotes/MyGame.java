package com.northerneyes.CatchTheNotes;

import android.content.Context;

import com.badlogic.gdx.Game;
import com.northerneyes.screens.GameScreen;

public class MyGame extends   Game  {
    public GameScreen game;

    private static Context context;

    public  MyGame(Context context){
        this.context = context;
    }

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void create() {
        // TODO Auto-generated method stub

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
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}*/


}
