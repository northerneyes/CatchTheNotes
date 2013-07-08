package com.northerneyes.CatchTheNotes.Services;

import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.Player;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 06.07.13
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public class ScoreManager implements IEntity {
    private int powerUpCount = 0;
    private int powerDownCount = 0;
    private int purplePowerCount = 0;
    private int yellowMadnessCount = 0;
    private int shapeCount = 70;
    private int score;

    private Player player;
    private int totalShape = 100;

    public ScoreManager(Player player) {
        this.player = player;
    }


    public int getScore() {
        return score;
    }

    public void addPowerUpCount() {
        powerUpCount++;
    }

//    public void addCombo() {
//        player.addCombo();
//    }

    public void addPowerDownCount() {
        powerDownCount++;
    }


    public void addPurplePowerCount() {
        purplePowerCount++;
    }

    public void resetCombo() {
        player.resetCombo();

    }

    public void addYellowMadnessCount() {
        yellowMadnessCount++;
    }

    public void addShapeCount() {
        shapeCount++;
    }

    public void addToScore(float amount) {
        score = Math.round(score + amount*player.getCombo());
    }

    public void addTotalShape(int count) {
        totalShape += count;
    }

    public void clear()
    {
        powerUpCount = 0;
        powerDownCount = 0;
        purplePowerCount = 0;
        yellowMadnessCount = 0;
        shapeCount = 0;
    }

    public int getCombo() {
        return player.getCombo();
    }

    @Override
    public void update(float delta) {

    }

    public int getMaxCombo() {
        return player.getMaxCombo();
    }

    public float getPlayerSize() {
        return player.Size;
    }

    public int getPowerDownCount() {
        return powerDownCount;
    }

    public int getPercentShapes() {
        return Math.round(100 * shapeCount / totalShape);
    }

    public void save() {
        //TODO: Save all this stuff
//        powerUpCount = 0;
//        powerDownCount = 0;
//        purplePowerCount = 0;
//        yellowMadnessCount = 0;
//        shapeCount = 0;
    }

    public void saveMedal(int medal) {
        //TODO:save medals

    }

    public int getUnlockLevel() {
        //TODO: getUnlockLevel
        return 0;
    }

    public void saveUnlockLevel(int unlockLevel) {
        //TODO:save unlock
    }

    public String getNewSong(int unlockLevel) {
        //TODO: load song
        return "New Song";
    }

    public void resetPlayerSize() {
        player.clear();
    }
}
