package com.northerneyes.model;

import aurelienribon.tweenengine.TweenManager;
import com.northerneyes.model.Menu.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 02.07.13
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public class MessageHolder  implements IEntity  {

    public List<Message> messages;
    private final TweenManager tweenManager = new TweenManager();
    public MessageHolder() {
        this.messages = new ArrayList<Message>();
    }

    public void addMessage(Message msg)
    {
        messages.add(msg);
        msg.show();
    }

    @Override
    public void update(float delta) {

        for (int particleIndex = 0; particleIndex < messages.size(); particleIndex++)
        {
            Message msg =  messages.get(particleIndex);
            msg.update(delta);
            if (msg.getTTL() == 0 || msg.Visibility <= 0)
            {
                messages.remove(particleIndex);
                particleIndex--;
            }
        }
    }
}
