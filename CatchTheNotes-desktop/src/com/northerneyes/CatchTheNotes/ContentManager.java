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
}
