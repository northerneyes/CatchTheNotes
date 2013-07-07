package com.northerneyes.CatchTheNotes.model.Menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.MyGame;
import com.northerneyes.CatchTheNotes.R;
import com.northerneyes.CatchTheNotes.Services.ScoreManager;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.Message;
import com.northerneyes.CatchTheNotes.model.MessageGroup;

import java.util.ArrayDeque;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 07.07.13
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
public class EndGameMenu implements IEntity {

    private final Vector2 TextPosition;
    private final Vector2 centerPosition;

    public MessageGroup currentMessageGroup;

    private ArrayDeque<MessageGroup> messageQueue = new ArrayDeque<MessageGroup>();
    public boolean Finished;
    private ScoreManager scoreManager;
    private Color color1 = new Color(102f/255f, 102f/255f, 204/255f, 1f);
    private Color color2 = new Color(153f/255f, 153f/255f, 204/255f, 1f);
    public Message SkipText;
    public Message PlayAgainText;
    public Message ChangeOptionsText;

    public EndGameMenu(float width, float height, ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
        TextPosition =  new Vector2(width/2, height - 5);
        centerPosition  = new Vector2(width/2, height/2);

        SkipText = new Message(MyGame.getAppContext().getString(R.string.skip_text), 2f, Color.GRAY, TextPosition.x, TextPosition.y + 4);

        PlayAgainText = new Message(MyGame.getAppContext().getString(R.string.play_again), 2f, Color.WHITE, centerPosition.x, centerPosition.y + 1);
        ChangeOptionsText = new Message(MyGame.getAppContext().getString(R.string.change_options), 2f, Color.WHITE, centerPosition.x, centerPosition.y - 1);

        PlayAgainText.setTTL(0);
        ChangeOptionsText.setTTL(0);
    }

    public void Init()
    {
        messageQueue.clear();
        Finished = false;
        SkipText.setTTL(1);
        PlayAgainText.setTTL(0);
        ChangeOptionsText.setTTL(0);

        int bonus = scoreManager.getMaxCombo() * 1000;
        String maxCombo = format(MyGame.getAppContext().getString(R.string.max_combo), scoreManager.getMaxCombo());
        String comboBonus = format(MyGame.getAppContext().getString(R.string.combo_bonus), bonus);
        MessageGroup group1 = new MessageGroup();
        group1.add(new Message(maxCombo, 2f, color1, TextPosition.x, TextPosition.y + 1, 0));
        group1.add(new Message(comboBonus, 2f, color1, TextPosition.x, TextPosition.y - 1, 0));
        messageQueue.add(group1);

        String sizeBonus = format(MyGame.getAppContext().getString(R.string.size_bonus),  Math.round(scoreManager.getPlayerSize()) * 1000);
        MessageGroup group2 = new MessageGroup();
        group2.add(new Message(sizeBonus, 2f, color2, TextPosition.x, TextPosition.y, 0));
        messageQueue.add(group2);
    }

    private String format(String string, int maxCombo) {
        return String.format(string, maxCombo);
    }

    @Override
    public void update(float delta) {
        if(Finished)
            return;

        if(currentMessageGroup == null || currentMessageGroup.getTTL() == 0)
        {
            setCurrentMessageGroup(delta);
        }
        currentMessageGroup.update(delta);
    }

    private void setCurrentMessageGroup(float delta) {
        MessageGroup group = messageQueue.pollFirst();
        if(group == null)
        {
            Finished = true;
            SkipText.setTTL(0);
            PlayAgainText.setTTL(1);
            ChangeOptionsText.setTTL(1);
            return;
        }

        group.update(delta);
        currentMessageGroup = group;
        currentMessageGroup.show();
    }

    public void skip() {
        currentMessageGroup.clear();
        currentMessageGroup.TTL = 0;
        messageQueue.clear();
       // PlayAgainText.setTTL(1);
       // ChangeOptionsText.setTTL(1);
    }

    public int getMenuState(float x, float y) {
        if(SkipText.contains(x, y) && SkipText.getTTL() == 1)
            return 0;
        if(PlayAgainText.contains(x, y) && PlayAgainText.getTTL() == 1)
            return 1;
        if(ChangeOptionsText.contains(x, y) && ChangeOptionsText.getTTL() == 1)
            return 2;
        return -1;
    }
}
