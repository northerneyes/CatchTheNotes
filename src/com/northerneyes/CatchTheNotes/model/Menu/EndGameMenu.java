package com.northerneyes.CatchTheNotes.model.Menu;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.MyGame;
import com.northerneyes.CatchTheNotes.R;
import com.northerneyes.CatchTheNotes.Services.ScoreManager;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.Message;
import com.northerneyes.CatchTheNotes.model.MessageGroup;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 07.07.13
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
public class EndGameMenu implements IEntity {

    private final Vector2 TextPosition;

    public MessageGroup currentMessageGroup;

    private ArrayDeque<MessageGroup> messageQueue = new ArrayDeque<MessageGroup>();
    private boolean Finished;
    private ScoreManager scoreManager;

    public EndGameMenu(float width, float height, ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
        TextPosition =  new Vector2(width/2, height - 3);

    }

    public void Init()
    {
        messageQueue.clear();
        Finished = false;

        int bonus = scoreManager.getMaxCombo() * 1000;
        String maxCombo = format(MyGame.getAppContext().getString(R.string.max_combo), scoreManager.getMaxCombo());
        String comboBonus = format(MyGame.getAppContext().getString(R.string.combo_bonus), bonus);
        MessageGroup group1 = new MessageGroup();
        group1.add(new Message(maxCombo, 3f, Color.YELLOW, TextPosition.x, TextPosition.y));
        group1.add(new Message(comboBonus, 3f, Color.YELLOW, TextPosition.x, TextPosition.y - 2));
        messageQueue.add(group1);

        String sizeBonus = format(MyGame.getAppContext().getString(R.string.size_bonus),  Math.round(scoreManager.getPlayerSize()) * 1000);
        MessageGroup group2 = new MessageGroup();
        group2.add(new Message(sizeBonus, 2f, Color.YELLOW, TextPosition.x, TextPosition.y));
        messageQueue.add(group2);
    }

    private String format(String string, int maxCombo) {
        return String.format(string, maxCombo);
    }

    @Override
    public void update(float delta) {
        if(currentMessageGroup == null)
        {
            currentMessageGroup = messageQueue.pollFirst();
            currentMessageGroup.show();
        }
        currentMessageGroup.update(delta);
        if(currentMessageGroup.getTTL() == 0)
        {
            MessageGroup group = messageQueue.pollFirst();
            if(group == null)
            {
                Finished = true;
                return;
            }
            currentMessageGroup = group;
            currentMessageGroup.show();
        }
    }

}
