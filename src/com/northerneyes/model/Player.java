package com.northerneyes.model;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player implements IEntity {

	Vector2 	position = new Vector2();
	Vector2 	velocity = new Vector2();
	Rectangle 	bounds = new Rectangle();
    public static final float SIZE = 1f;

    public Player(Vector2 position) {
		
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Vector2 getPosition() {
		return position;
	}

    public void setPosition(float x, float y) {
        position.set(x, y);
    }
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void update(float delta) {

	}
}