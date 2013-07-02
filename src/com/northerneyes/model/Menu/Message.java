package com.northerneyes.model.Menu;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;

import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Cubic;
import aurelienribon.tweenengine.equations.Linear;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.accessors.MessageAccessor;
import com.northerneyes.model.IEntity;
import aurelienribon.tweenengine.equations.Linear.*;

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
    public float Visibility;
    public Vector2 Position;
    private TweenManager tweenManager = new TweenManager();

    public Message(String text, float duration, Color color, float xPos, float yPos)
    {
        this.Text = text;
        this.Duration = duration;
        this.texColor = color;
        Position = new Vector2(xPos, yPos);
    }

    @Override
    public void update(float delta) {
       // tweenManager.update(Gdx.graphics.getDeltaTime());
    }

    public TweenManager getTwenManager()
    {
        return tweenManager;
    }
    public Color getColor() {
        return texColor;
    }

    public void setColor(Color c) {
        texColor.set(c);
    }

    public void show() {
        Tween.from(this, MessageAccessor.POS_XY, Duration)
                .target(Position.x, Position.y + 3)
                .ease(Cubic.INOUT)
                .start(tweenManager)
                .setCallback(this);
    }

    public int getTTL() {
        return TTL;
    }

    @Override
    public void onEvent(int i, BaseTween baseTween) {
        TTL = 0;
    }

}
