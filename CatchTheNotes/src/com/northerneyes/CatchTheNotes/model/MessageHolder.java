package com.northerneyes.CatchTheNotes.model;


import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.Color;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 02.07.13
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public class MessageHolder  implements IEntity  {

    private final Random random;
    public List<Message> messages;
    private String[] powerUpMessages;
    private String[] powerDownMessages;
    private String yellowMadnessMessage;
    private String powerMessage;
    private Color powerDownColor = new Color(204f/255f, 0, 0, 1);
    private Color powerUpColor = new Color(204f/255f, 204f/255f, 0, 1);

    private final TweenManager tweenManager = new TweenManager();
    public MessageHolder() {
        this.messages = new ArrayList<Message>();
        powerDownMessages = CatchTheNotes.getContentManager().getStringArray("powerdown_messages");
        powerUpMessages =  CatchTheNotes.getContentManager().getStringArray("powerup_messages");
        yellowMadnessMessage =  CatchTheNotes.getContentManager().getString("yellow_madness");
        powerMessage = CatchTheNotes.getContentManager().getString("puple_power");
        random = new Random();
    }

    public void addMessage(Message msg)
    {
        messages.add(msg);
        msg.show();
    }


    public void addMessage(Message msg, Note.NoteType type) {
        switch (type)
        {
            case POWER_UP:
                msg.setColor(powerUpColor);
                msg.Text = powerUpMessages[random.nextInt(powerUpMessages.length)];
                break;
            case POWER_DOWN:
                msg.setColor(powerDownColor);
                msg.Text = powerDownMessages[random.nextInt(powerDownMessages.length)];
                break;
            case YELLOW_MADDNESS:
                msg.setColor(Color.YELLOW);
                msg.Text = yellowMadnessMessage;
                break;
            case SUCTION:
                msg.setColor(Color.MAGENTA);
                msg.Text = powerMessage;
                break;
            default:
                msg.setColor(Color.WHITE);
        }
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
