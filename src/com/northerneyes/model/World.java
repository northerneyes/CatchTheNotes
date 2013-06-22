package com.northerneyes.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {
	private Player player;
	private NotesHolder notesHolder;
	public float width;
	public float height;


	public Player getPlayer() {
		return player;
	}

    public NotesHolder getNotesHolder(){
        return notesHolder;
    }

	public World() {

	}
    public void setSize(float camera_width, float camera_height) {
        this.width = camera_width;
        this.height = camera_height;
    }

	public void createWorld() {
		player = new Player(new Vector2(3, 7));
        notesHolder = new NotesHolder();
	}
}
