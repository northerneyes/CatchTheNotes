package com.northerneyes.CatchTheNotes.Free;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.ContentManager;
import com.northerneyes.CatchTheNotes.LibApp;

public class MainActivityFree extends LibApp {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useWakelock = true;
        config.useGL20 = true;
        initialize(new CatchTheNotes(new ContentManager(LibApp.getContext())), config);
        Log.v("Game", "Activity On create");
    }
}
