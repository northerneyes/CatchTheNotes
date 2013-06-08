package com.northerneyes.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {
	public Player player;
	
	public int width;
	public int height;


	public Player getPlayer() {
		return player;
	}
	
	public World() {
		width = 8;
		height = 5;

		createWorld();
	}
	
	public void createWorld() {
		player = new Player(new Vector2(0, 15));
	}
}
