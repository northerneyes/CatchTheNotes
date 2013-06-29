package com.northerneyes.model.Menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.model.IEntity;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 29.06.13
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class Message implements IEntity {
    public String Text;
    private int duration;
    Color texColor;
    private float xPos;
    private float yPos;
    private float startYPos;
    public float Visibility;
    public Vector2 Position;

    public Message(String text, int duration, Color color, float xPos, float yPos, float startYPos)
    {
        this.Text = text;
        this.duration = duration;
        this.texColor = color;
        this.xPos = xPos;
        this.yPos = yPos;
        this.startYPos = startYPos;
    }

    @Override
    public void update(float delta) {
        duration--;

    }
}
