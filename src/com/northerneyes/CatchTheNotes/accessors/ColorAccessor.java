package com.northerneyes.CatchTheNotes.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.Color;
import com.northerneyes.CatchTheNotes.model.Menu.Message;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 06.07.13
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
public class ColorAccessor implements TweenAccessor<Color> {

    public static final int OPACITY = 1;

    @Override
    public int getValues(Color target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case OPACITY: returnValues[0] = target.a; return 1;

            default: assert false; return -1;
        }
    }

    @Override
    public void setValues(Color target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case OPACITY:
                target.a = newValues[0];
                break;

            default: assert false;
        }
    }
}
