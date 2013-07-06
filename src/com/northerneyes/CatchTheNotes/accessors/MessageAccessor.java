package com.northerneyes.CatchTheNotes.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.Color;
import com.northerneyes.CatchTheNotes.model.Menu.Message;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 02.07.13
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public class MessageAccessor  implements TweenAccessor<Message> {
    public static final int POS_XY = 1;
    public static final int OPACITY = 5;

    @Override
    public int getValues(Message target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POS_XY:
                returnValues[0] = target.Position.x;
                returnValues[1] = target.Position.y;
                return 2;

            case OPACITY: returnValues[0] = target.getColor().a; return 1;

            default: assert false; return -1;
        }
    }

    @Override
    public void setValues(Message target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POS_XY: target.Position.set(newValues[0], newValues[1]); break;

            case OPACITY:
                Color c = target.getColor();
                c.set(c.r, c.g, c.b, newValues[0]);
                //target.setColor(c);
                break;

            default: assert false;
        }
    }
}
