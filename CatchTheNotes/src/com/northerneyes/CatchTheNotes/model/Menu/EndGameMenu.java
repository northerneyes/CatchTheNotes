package com.northerneyes.CatchTheNotes.model.Menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.Services.IContentManager;
import com.northerneyes.CatchTheNotes.Services.ScoreManager;
import com.northerneyes.CatchTheNotes.Services.SettingsService;
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
    private final IContentManager contentManager;

    public MessageGroup currentMessageGroup;

    private ArrayDeque<MessageGroup> messageQueue = new ArrayDeque<MessageGroup>();
    public boolean Finished;
    private ScoreManager scoreManager;
    private Color color1 = new Color(102f/255f, 102f/255f, 204/255f, 1f);
    private Color color2 = new Color(153f/255f, 153f/255f, 204/255f, 1f);
    private Color totalPointsColor = new Color(221f/255f, 221f/255f, 1f, 1f);
    private  Color noRedsNotesColor = new Color(1f, 153f/255f, 153f/255f, 1f);
    private Color percentColor = new Color(1f, 1f, 204/255f, 1f);
    public Message SkipText;
    public Message PlayAgainText;
    public Message ChangeOptionsText;
    private int medalNumber;
    public float MedalSize = 4f;
    public Vector2 MedalPosition;
    public Message MedalText;
    private SettingsService settings;

    public EndGameMenu(float width, float height, ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
        TextPosition =  new Vector2(width/2, height - 5);
        centerPosition  = new Vector2(width/2, height/2);
        contentManager = CatchTheNotes.getContentManager();

        SkipText = new Message(contentManager.getString("skip_text"), 2f, Color.GRAY, TextPosition.x, TextPosition.y + 4);

        PlayAgainText = new Message(contentManager.getString("play_again"), 2f, Color.WHITE, centerPosition.x, centerPosition.y + 1 + 2);
        ChangeOptionsText = new Message(contentManager.getString("change_options"), 2f, Color.WHITE, centerPosition.x, centerPosition.y - 1 + 2);
        settings = CatchTheNotes.getSettingService();

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
        scoreManager.addToScore(scoreManager.getPlayerSize() * 1000 + bonus);
        String maxCombo = format(contentManager.getString("max_combo"), scoreManager.getMaxCombo());
        String comboBonus = format(contentManager.getString("combo_bonus"), bonus);
        String sizeBonus = format(contentManager.getString("size_bonus"),  Math.round(scoreManager.getPlayerSize()) * 1000);
        String totalPoints = format(contentManager.getString("total_points"), Math.round(scoreManager.getScore()));

        scoreManager.resetPlayerSize();

        messageQueue.add(new MessageGroup()
                .add(new Message(maxCombo, 3f, color1, TextPosition.x, TextPosition.y + 1, 0))
                .add(new Message(comboBonus, 3f, color1, TextPosition.x, TextPosition.y - 1, 0))
                .add(new Message(sizeBonus, 3f, color2, TextPosition.x, TextPosition.y - 3, 0))
                .add(new Message(totalPoints, 3f, totalPointsColor, TextPosition.x, TextPosition.y - 5, 0)));

        if(scoreManager.getPowerDownCount() == 0)
        {
            //TODO:noRedsSprite?
            messageQueue.add(new MessageGroup()
                    .add(new Message(contentManager.getString("super_duper"), 3f, noRedsNotesColor, TextPosition.x, TextPosition.y + 1, 0))
                    .add(new Message(contentManager.getString("avoided"), 3f, noRedsNotesColor, TextPosition.x, TextPosition.y - 1, 0)));
        }

        int medal = loadMedal();
        unlockLevels();
        if(medal > 0)
            scoreManager.saveMedal(medal);
        scoreManager.save();
        scoreManager.clear();

    }

    private void unlockLevels() {
        int unlockLevel = scoreManager.getUnlockLevel();
        scoreManager.saveUnlockLevel(++unlockLevel);

        if(unlockLevel == 2)
        {
        messageQueue.add(new MessageGroup()
                .add(new Message(contentManager.getString("unlocked_more_options"), 3f, percentColor, TextPosition.x, TextPosition.y + 1, 0))
                .add(new Message(format(contentManager.getString("new_song"), scoreManager.getNewSong(unlockLevel)),
                        3f, percentColor, TextPosition.x, TextPosition.y - 1, 0)));
        }
        else if (unlockLevel == 5)
        {
            messageQueue.add(new MessageGroup()
                    .add(new Message(contentManager.getString("unlocked_all_options"), 3f, percentColor, TextPosition.x, TextPosition.y + 1, 0))
                    .add(new Message(format(contentManager.getString("new_song"), scoreManager.getNewSong(unlockLevel)),
                            3f, percentColor, TextPosition.x, TextPosition.y - 1, 0)));
        }

    }

    private int loadMedal() {
        medalNumber = -1;
        int percent = scoreManager.getPercentShapes();
        if(percent > 30)
        {
            String medalName = "";

            if(percent > 75)
            {
                medalNumber = 3;
                medalName = contentManager.getString("platinum_medal");
            }
            else if (percent > 60)
            {
                medalNumber = 2;
                medalName = contentManager.getString("gold_medal");
            }
            else if(percent > 40)
            {
                medalNumber = 1;
                medalName =contentManager.getString("silver_medal");
            }
            else
            {
                medalNumber = 0;
                medalName = contentManager.getString("bronze_medal");
            }

            messageQueue.add(new MessageGroup()
                    .add(new Message(contentManager.getString("congratulations"), 3f, percentColor, TextPosition.x, TextPosition.y + 1, 0))
                    .add(new Message(format(contentManager.getString("you_collected"), percent), 3f, percentColor, TextPosition.x, TextPosition.y - 1, 0)));
            MedalPosition = new Vector2(TextPosition.x, TextPosition.y - 5);

            MedalText = new Message(String.format("x%d", getMedalCount() + 1), 2000, settings.getMedalColor(medalNumber), MedalPosition.x, MedalPosition.y);
        }
        return medalNumber;
    }

    public int getMedal() {
        return medalNumber;
    }
    public int getMedalCount() {
        return settings.getMedalCount(medalNumber);
    }

    private String format(String string, int value) {
        return String.format(string, value);
    }
    private String format(String string, String value) {
        return String.format(string, value);
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
