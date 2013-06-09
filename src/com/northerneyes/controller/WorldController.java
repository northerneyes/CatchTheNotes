package com.northerneyes.controller;

import com.northerneyes.model.NotesHolder;
import com.northerneyes.model.Player;
import com.northerneyes.model.World;

import java.util.Random;


public class WorldController {

	public Player player;
    public NotesHolder notesHolder;
    public Random random=  new Random();

	public WorldController(World world) {
		this.player = world.getPlayer();
        this.notesHolder = world.getNotesHolder();
	}

	public void update(float delta) {
		player.update(delta);
        notesHolder.update(delta);

        for (int a = 0; a < 128; a++)
        {
            if(random.nextInt(1000) > 990)
                notesHolder.beat(random.nextFloat()*25);
        }
	}

}
