package com.northerneyes.CatchTheNotes.model;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 07.07.13
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public class MessageGroup  implements IEntity, TweenCallback {

    private ArrayList<Message> messages;
    private TweenManager tweenManager = new TweenManager();
    private  int TTL = 1;
    public MessageGroup() {
        messages = new ArrayList<Message>();
    }

    public void show()
    {
        Timeline parallel = Timeline.createParallel();

        for(Message msg: messages)
        {
            parallel = parallel.push(msg.buildSequence());
        }

        parallel.setCallback(this)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .start(tweenManager);
    }

    public void add(Message msg)
    {
        messages.add(msg);
    }

    public int getTTL() {
        return TTL;
    }

    @Override
    public void update(float delta) {
        tweenManager.update(delta);
    }

    @Override
    public void onEvent(int i, BaseTween<?> baseTween) {
        TTL  = 0;
    }

    public int size() {
        return messages.size();
    }

    public Message get(int index) {
        return messages.get(index);
    }
}
