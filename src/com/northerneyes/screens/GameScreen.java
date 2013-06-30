package com.northerneyes.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.northerneyes.audio.MediaPlayer;
import com.northerneyes.controller.IMenuController;
import com.northerneyes.controller.WorldController;
import com.northerneyes.model.World;
import com.northerneyes.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {
	private World world;
	private WorldRenderer render;
	private WorldController controller;
    private IMenuController menuController;

    private int width, height;
   // public static  float height = 800f;
  //  public static float width = height *Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
	@Override
	public void show() {
		world = new World();
		render = new WorldRenderer(world);
		controller = new WorldController(world);
        menuController = controller.getCurrentMenu();
		Gdx.input.setInputProcessor(this);
	}

    @Override
	public boolean touchDragged(int x, int y, int pointer) {
		ChangeNavigation(x,y);
		return false;
	}

	
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		return false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	
	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void pause() {
        MediaPlayer.pause();
	}

	@Override
	public void resume() {
        MediaPlayer.resume();
	}

	@Override
	public void dispose() {		
		Gdx.input.setInputProcessor(null);
        MediaPlayer.dispose();
	}


	@Override
	public boolean keyDown(int keycode) {
		return true;
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        menuController = controller.getCurrentMenu();
		controller.update(delta);
		render.render();
	}
	@Override
	public boolean keyUp(int keycode) {
		return true;
	}
	
	private void ChangeNavigation(int x, int y){
        controller.player.Position.set(getGameXPos(x), getGameYPos(y));
	}

    private float getGameYPos(int y) {
        return (height-y)/render.ppuY;
    }

    private float getGameXPos(int x) {
        return WorldController.SOURCE_COUNT*x/(render.ppuX * WorldRenderer.CAMERA_WIDTH);
    }

    @Override
	public boolean touchDown(int x, int y, int pointer, int button) {

		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
        if(menuController != null)
            menuController.setPosition(x, height - y);
		ChangeNavigation(x,y);
		return true;
	} 
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
        return Gdx.app.getType().equals(ApplicationType.Android);
    }
	
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
