package com.northerneyes.CatchTheNotes;


import android.util.Log;



import android.os.Bundle;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class MainActivity extends AndroidApplication {
	 
    /** Called when the activity is first created. */ 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true;
		config.useGL20 = true;
		initialize(new CatchTheNotes(new ContentManager(this)), config);
        Log.v("Game", "Activity On create");
    }


    @Override
    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeLifecycleListener(LifecycleListener lifecycleListener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}