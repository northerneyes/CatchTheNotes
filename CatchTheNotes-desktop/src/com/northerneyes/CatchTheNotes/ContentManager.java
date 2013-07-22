package com.northerneyes.CatchTheNotes;

import com.northerneyes.CatchTheNotes.Services.IContentManager;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.07.13
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
 */
public class ContentManager implements IContentManager {

    private HashMap<String, String> textHashMap = new HashMap<String, String>();
    private HashMap<String, String[]> textArrayHashMap = new HashMap<String, String[]>();
    private HashMap<String, Float> dimenHashMap = new HashMap<String, Float>();

    public ContentManager() {
        textHashMap.put("skip_text","[ Skip ]");
        textHashMap.put("app_name", "Music King");
        textHashMap.put("title_activity_main", "Music King");
        textHashMap.put("pause_menu", "Pause");
        textHashMap.put("score", "Score:");
        textHashMap.put("end_game_menu", "End Game");
        textHashMap.put("restart_menu", "Restart");
        textHashMap.put("resume_menu", "Resume");
        textHashMap.put("play_menu", "Play");
        textHashMap.put("song_menu", "Song:");

        textHashMap.put("leave_in_the_wind", "Leave In The Wind");
        textHashMap.put("centle", "Centle");
        textHashMap.put("letting_go", "Letting Go");

        textHashMap.put("yellow_madness", "Yellow Madness!");
        textHashMap.put("puple_power", "Purple Power!");

        textHashMap.put("max_combo", "Max Combo: %d");
        textHashMap.put("combo_bonus", "Combo Bonus: %d");
        textHashMap.put("size_bonus", "Size Bonus: %d");
        textHashMap.put("total_points", "Total Points: %d");
        textHashMap.put("max_score", "Max Score: ");

        textHashMap.put("play_again", "Play Again");
        textHashMap.put("change_options", "Change Options");
        textHashMap.put("super_duper","Super!");
        textHashMap.put("avoided","You avoided all red notes!" );
        textHashMap.put("platinum_medal", "Platinum");
        textHashMap.put("gold_medal", "Gold");
        textHashMap.put("silver_medal", "Silver" );
        textHashMap.put("bronze_medal", "Bronze");
        textHashMap.put("congratulations", "Congratulations!");
        textHashMap.put("you_collected", "You collected %d%% of the notes");
        textHashMap.put("unlocked_more_options", "You unlocked more options!");
        textHashMap.put("new_song", "New Song: %s");
        textHashMap.put("unlocked_all_options", "All options are now unlocked!");

        textArrayHashMap.put("powerdown_messages", new String[]{"Oops!", "Ouch!"});
        textArrayHashMap.put("powerup_messages", new String[]{"Yeah!", "Great!"});



    }

    @Override
    public String getString(String key) {
        return textHashMap.get(key);
    }

    @Override
    public String[] getStringArray(String key) {
        return textArrayHashMap.get(key);
    }

    @Override
    public float getDimension(String key) {
        return dimenHashMap.get(key);
    }

    @Override
    public void setDimensionCoeff(float coeff) {

        dimenHashMap.put("small_size", 0.25f*coeff);
        dimenHashMap.put("medium_size", 0.4f*coeff);
        dimenHashMap.put("large_size", 0.6f*coeff);
        dimenHashMap.put("player_coef_size", 0.075f*coeff);
        dimenHashMap.put("medal_text_size", 1.8f*0.075f*coeff);
    }
}
