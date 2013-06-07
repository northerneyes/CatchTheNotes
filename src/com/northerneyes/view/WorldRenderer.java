package com.northerneyes.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.northerneyes.model.Player;
import com.northerneyes.model.World;

public class WorldRenderer {

	public static  float CAMERA_HEIGHT = 800f;
    public static float CAMERA_WIDTH = 480f;

	private World world;
	public OrthographicCamera cam;
	ShapeRenderer renderer = new ShapeRenderer();
	
	public float width;
	public float height;
	public float ppuX;
	public float ppuY;
	
	public void setSize (float w, float h) {
        this.width = w;
        this.height = h;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
	}
	
	public void SetCamera(float x, float y){
		this.cam.position.set(x, y,0);	
		this.cam.update();
	}
	
	public WorldRenderer(World world) {
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		SetCamera(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f);
	}

	public void render() {
		drawPlayer() ;
	}

	private void drawPlayer() {
		renderer.setProjectionMatrix(cam.combined);
		Player player = world.getPlayer();
		renderer.begin(ShapeType.FilledCircle);
		
		Rectangle rect = player.getBounds();
		float x1 = player.getPosition().x; // + rect.x;
		float y1 = player.getPosition().y;// + rect.y;
		renderer.setColor(new Color(Color.WHITE));
        renderer.filledCircle(x1, y1, 30f);
        renderer.end();
//        renderer.begin(ShapeType.Rectangle);
//		renderer.rect(x1, y1, rect.width, rect.height);
//		renderer.end();
	}
	
}
