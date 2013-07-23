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
    private static final String RED_STARS = "RedNotes";
    private static final String CURRENT_SONG_INDEX = "CurrentSongIndex";
    private static final String CURRENT_SHAPE_TYPE = "CurrentShapeType";
    private final HashMap<Integer, String> medalSettingsName;

    private static final String MAX_SCORE = "MaxScore";
    private static final String UNLOCK_LEVEL = "UnlockLevel";

    private Preferences preferences;
    private HashMap<Integer, Color> medalColor;
    public int MaxMedals = 4;

    public SettingsService() {
        preferences = getPrefs();

        medalColor = new HashMap<Integer, Color>();
        medalSettingsName = new HashMap<Integer, String>();


        medalColor.put(0, Color.WHITE);
        medalColor.put(1, Color.BLACK);
        medalColor.put(2, Color.WHITE);
        medalColor.put(3, Color.BLACK);

        medalSettingsName.put(0, "Bronze");
        medalSettingsName.put(1, "Silver");
        medalSettingsName.put(2, "Gold");
        medalSettingsName.put(3, "Platinum");

    }

    protected Preferences getPrefs() {
        if(preferences==null){
            preferences = Gdx.app.getPreferences(PREFS_NAME);
        }
        return preferences;
    }


    public void saveMedal(int type) {
        int count = preferences.getInteger(medalSettingsName.get(type));
        preferences.putInteger(medalSettingsName.get(type), ++count);
        preferences.flush();
    }

    public int getMedalCount(int type)
    {
        return  preferences.getInteger(medalSettingsName.get(type));
    }

    public Color getMedalColor(int medalNumber) {
        return medalColor.get(medalNumber);
    }

    public void saveMaxScore(int score) {
        int  maxScore = preferences.getInteger(MAX_SCORE, 0);
        if(score > maxScore) {
            maxScore = score;
            preferences.putInteger(MAX_SCORE, maxScore);
            preferences.flush();
        }
    }

    public int getMaxScore() {
        return preferences.getInteger(MAX_SCORE, 0);
    }

    public int getUnlockLevel() {
        return preferences.getInteger(UNLOCK_LEVEL, 0);
    }

    public void saveUnlockLevel(int unlockLevel) {
        preferences.putInteger(UNLOCK_LEVEL, unlockLevel);
        preferences.flush();
    }

    public void saveRedStars() {
        int count = preferences.getInteger(RED_STARS);
        preferences.putInteger(RED_STARS, ++count);
        preferences.flush();
    }

    public int getRedStars(){
        return preferences.getInteger(RED_STARS, 0);
    }

    public int getCurrentSongIndex() {
        return  preferences.getInteger(CURRENT_SONG_INDEX, 0);
    }

    public void setCurrentSongIndex(int currentSongIndex) {
        preferences.putInteger(CURRENT_SONG_INDEX, currentSongIndex);
        preferences.flush();
    }

    public void setCurrentShapeType(int shapeType) {
        preferences.putInteger(CURRENT_SHAPE_TYPE, shapeType);
        preferences.flush();
    }

    public int getCurrentShapeType() {
        return preferences.getInteger(CURRENT_SHAPE_TYPE, 0);
    }
}
