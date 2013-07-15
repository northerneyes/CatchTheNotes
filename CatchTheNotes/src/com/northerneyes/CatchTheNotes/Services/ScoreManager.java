package com.northerneyes.CatchTheNotes.Services;

import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.model.Constants;
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
    private final SettingsService settingService;
    private int powerUpCount = 0;
    private int powerDownCount = 0;
    private int purplePowerCount = 0;
    private int yellowMadnessCount = 0;
    private int shapeCount = 0;
    private int score;

    private Player player;
    private int totalShape = 0;


    public ScoreManager(Player player) {
        this.player = player;
        settingService = CatchTheNotes.getSettingService();
    }


    public int getScore() {
        return score;
    }

    public void addPowerUpCount() {
        powerUpCount++;
    }

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
        score = 0;
        powerUpCount = 0;
        powerDownCount = 0;
        purplePowerCount = 0;
        totalShape = 0;
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
        if(Constants.DEBUG_END_MENU)
        {
            return 2;
        }
        return powerDownCount;
    }

    public int getPercentShapes() {
        if(Constants.DEBUG_END_MENU)
        {
            return 37;
        }
        return Math.round(100 * shapeCount / totalShape);
    }

    public void save() {
        //TODO: Save all this stuff
        settingService.saveMaxScore(score);
//        powerUpCount = 0;
//        powerDownCount = 0;
//        purplePowerCount = 0;
//        yellowMadnessCount = 0;
//        shapeCount = 0;
    }

    public void saveMedal(int medal) {
        settingService.saveMedal(medal);
    }

    public int getUnlockLevel() {
        return settingService.getUnlockLevel();
    }

    public void saveUnlockLevel(int unlockLevel) {
        settingService.saveUnlockLevel(unlockLevel);
    }

    public String getNewSong(int unlockLevel, IContentManager contentManager) {
        if(unlockLevel == 2)
            return contentManager.getString("centle");
        if(unlockLevel == 5)
            return contentManager.getString("letting_go");
        return "New Song";
    }

    public void resetPlayerSize() {
        player.clear();
    }

    public void saveRedStars() {
        settingService.saveRedStars();
    }
}
