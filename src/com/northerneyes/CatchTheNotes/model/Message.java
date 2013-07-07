package com.northerneyes.CatchTheNotes.model;

import aurelienribon.tweenengine.*;

import aurelienribon.tweenengine.equations.Cubic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.accessors.MessageAccessor;
import com.northerneyes.CatchTheNotes.model.IEntity;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 29.06.13
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class Message implements IEntity, TweenCallback {
    private  int TTL = 1;
    public String Text;
    private float Duration;
    Color texColor;
    private float yStartPos;
    public float Visibility = 1f;
    public Vector2 Position;
    private TweenManager tweenManager = new TweenManager();
    private Rectangle bounds = new Rectangle();

    public Message(String text, float duration, Color color, float xPos, float yPos)
    {
        this.Text = text;
        this.Duration = duration;
        this.texColor = color;
        yStartPos = yPos;

        Position = new Vector2(xPos, yStartPos + 2);
    }

    public Message(String text, float duration, Color color, float xPos, float yPos, float yStartPos)
    {
        this.Text = text;
        this.Duration = duration;
        this.texColor = color;
        this.yStartPos = yStartPos;
        Position = new Vector2(xPos, yPos);
    }

    public Message(float duration, float xPos, float yPos) {
        Duration = duration;
        Position = new Vector2(xPos, yPos);
        texColor = Color.YELLOW;
    }

    @Override
    public void update(float delta) {
        tweenManager.update(delta);
    }

    public Color getColor() {
        return texColor;
    }

    public void setColor(Color c) {
            texColor = new Color(c);
    }

    public void show() {
        Timeline.createSequence()
                .push(buildSequence())
                .setCallback(this)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .start(tweenManager);
    }

    public Timeline buildSequence()
    {
        return  Timeline.createSequence()
                .push(Tween.set(this, MessageAccessor.POS_XY).target(Position.x, yStartPos))
                .push(Tween.set(this, MessageAccessor.OPACITY).target(0.8f))
                .beginParallel()
                .push(Tween.to(this, MessageAccessor.POS_XY, Duration).target(Position.x, Position.y).ease(Cubic.OUT))
                .push(Tween.to(this, MessageAccessor.OPACITY, Duration).target(1f).ease(Cubic.OUT))
                .end()
//                .pushPause(0.2f)
                .beginParallel()
                .push(Tween.to(this, MessageAccessor.POS_XY, Duration).target(Position.x, 0).ease(Cubic.IN))
                .push(Tween.to(this, MessageAccessor.OPACITY, Duration).target(0).ease(Cubic.IN))
                .end();
    }
    public int getTTL() {
        return TTL;
    }

    @Override
    public void onEvent(int i, BaseTween baseTween) {
        TTL = 0;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean contains(float x, float y) {
        return bounds.contains(x, y);
    }

    public void setTTL(int TTL) {
        this.TTL = TTL;
    }
}
