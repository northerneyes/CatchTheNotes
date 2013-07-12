package com.northerneyes.CatchTheNotes.Services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 10.07.13
 * Time: 20:06
 * To change this template use File | Settings | File Templates.
 */
public class SettingsService {
    private static final String PREFS_NAME = "CatchTheNotes";

    private Preferences preferences;
    private HashMap<Integer, Integer> medals;
    private HashMap<Integer, Color> medalColor;
    public int MaxMedals = 4;
    private int maxScore;
    private int unlockLevel;

    public SettingsService() {
        medals = new HashMap<Integer, Integer>();
        medalColor = new HashMap<Integer, Color>();

        medals.put(0, 0);
        medals.put(1, 0);
        medals.put(2, 0);
        medals.put(3, 0);

        medalColor.put(0, Color.WHITE);
        medalColor.put(1, Color.BLACK);
        medalColor.put(2, Color.WHITE);
        medalColor.put(3, Color.BLACK);
    }

    protected Preferences getPrefs() {
        if(preferences==null){
            preferences = Gdx.app.getPreferences(PREFS_NAME);
        }
        return preferences;
    }


    public void saveMedal(int type) {
        int count = medals.get(type);
        medals.put(type, ++count);

    }

    public int getMedalCount(int type)
    {
        return medals.get(type);
    }

    public Color getMedalColor(int medalNumber) {
        return medalColor.get(medalNumber);
    }

    public void saveMaxScore(int score) {
        int maxScore = this.maxScore;
        if(score > maxScore)
            maxScore = score;
    }

    public int getUnlockLevel() {
        return unlockLevel;  //To change body of created methods use File | Settings | File Templates.
    }

    public void saveUnlockLevel(int unlockLevel) {
        this.unlockLevel = unlockLevel;
    }
}
