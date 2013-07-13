package com.northerneyes.CatchTheNotes;

import android.content.Context;
import com.northerneyes.CatchTheNotes.Services.IContentManager;
import com.northerneyes.CatchTheNotes.R;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.07.13
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */
public class ContentManager implements IContentManager {
    private final Context context;
    private HashMap<String, String> textHashMap = new HashMap<String, String>();
    private HashMap<String, String[]> textArrayHashMap = new HashMap<String, String[]>();
    private HashMap<String, Float> dimenHashMap = new HashMap<String, Float>();
    private float dimenCoeff;

    public ContentManager(Context context) {
        this.context = context;
        textHashMap.put("skip_text", context.getString(R.string.skip_text));
        textHashMap.put("app_name", context.getString(R.string.app_name));
        textHashMap.put("title_activity_main", context.getString(R.string.title_activity_main));
        textHashMap.put("pause_menu", context.getString(R.string.pause_menu));
        textHashMap.put("score", context.getString(R.string.score));
        textHashMap.put("end_game_menu", context.getString(R.string.end_game_menu));
        textHashMap.put("restart_menu", context.getString(R.string.restart_menu));
        textHashMap.put("resume_menu", context.getString(R.string.resume_menu));
        textHashMap.put("play_menu", context.getString(R.string.play_menu));
        textHashMap.put("song_menu", context.getString(R.string.song_menu));
        textHashMap.put("leave_in_the_wind", context.getString(R.string.leave_in_the_wind));
        textHashMap.put("centle", context.getString(R.string.centle));
        textHashMap.put("letting_go", context.getString(R.string.letting_go));

        textHashMap.put("yellow_madness", context.getString(R.string.yellow_madness));
        textHashMap.put("puple_power", context.getString(R.string.puple_power));

        textHashMap.put("max_combo", context.getString(R.string.max_combo));
        textHashMap.put("combo_bonus", context.getString(R.string.combo_bonus));
        textHashMap.put("size_bonus", context.getString(R.string.size_bonus));
        textHashMap.put("total_points", context.getString(R.string.total_points));
        textHashMap.put("skip_text", context.getString(R.string.skip_text));
        textHashMap.put("max_score", context.getString(R.string.max_score));

        textHashMap.put("play_again", context.getString(R.string.play_again));
        textHashMap.put("change_options", context.getString(R.string.change_options));
        textHashMap.put("super_duper", context.getString(R.string.super_duper));
        textHashMap.put("avoided", context.getString(R.string.avoided));
        textHashMap.put("platinum_medal", context.getString(R.string.platinum_medal));
        textHashMap.put("gold_medal", context.getString(R.string.gold_medal));
        textHashMap.put("silver_medal", context.getString(R.string.silver_medal));
        textHashMap.put("bronze_medal", context.getString(R.string.bronze_medal));
        textHashMap.put("congratulations", context.getString(R.string.congratulations));
        textHashMap.put("you_collected", context.getString(R.string.you_collected));
        textHashMap.put("unlocked_more_options", context.getString(R.string.unlocked_more_options));
        textHashMap.put("new_song", context.getString(R.string.new_song));
        textHashMap.put("unlocked_all_options", context.getString(R.string.unlocked_all_options));

        textArrayHashMap.put("powerdown_messages", context.getResources().getStringArray(R.array.powerdown_messages));
        textArrayHashMap.put("powerup_messages", context.getResources().getStringArray(R.array.powerup_messages));


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
        this.dimenCoeff = coeff;

        dimenHashMap.put("small_size", 0.25f*coeff);
        dimenHashMap.put("medium_size", 0.4f*coeff);
        dimenHashMap.put("large_size", 0.6f*coeff);
        dimenHashMap.put("player_coef_size", 0.075f*coeff);
        dimenHashMap.put("medal_text_size", 2f*0.075f*coeff);
    }
}
