package com.northerneyes.CatchTheNotes.activities;


import android.util.Log;
import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import android.os.Bundle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.northerneyes.CatchTheNotes.MyGame;
import com.northerneyes.CatchTheNotes.accessors.*;
import com.northerneyes.CatchTheNotes.model.Menu.Message;

public class MainActivity extends AndroidApplication {
	 
    /** Called when the activity is first created. */ 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Tween.setWaypointsLimit(10);
        Tween.setCombinedAttributesLimit(3);
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.registerAccessor(Message.class, new MessageAccessor());
        Tween.registerAccessor(Color.class, new ColorAccessor());

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true;
		config.useGL20 = true;
	
		initialize(new MyGame(this.getApplicationContext()), config);
        Log.v("Game", "Activity On create");
    }
    
	 
}