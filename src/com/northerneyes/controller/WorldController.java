package com.northerneyes.controller;

import com.northerneyes.model.Player;
import com.northerneyes.model.World;



public class WorldController {

	public Player player;

	public WorldController(World world) {
		this.player = world.getPlayer();
	}

	public void update(float delta) {
		player.update(delta);
	}

}
