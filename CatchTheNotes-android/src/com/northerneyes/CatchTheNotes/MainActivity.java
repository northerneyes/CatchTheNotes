package com.northerneyes.CatchTheNotes;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;



import android.os.Bundle;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class MainActivity extends AndroidApplication {
    private Uri mUri;
    /** Called when the activity is first created. */ 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true;
		config.useGL20 = true;

        Intent intent = getIntent();
        if(intent != null)
        {
            mUri = intent.getData();
            if(mUri != null)
            {
                String scheme = mUri.getScheme();
                if (scheme.equals("file"))
                {
                    String rootPath = Environment.getExternalStorageDirectory().toString();
                    String path = mUri.getPath();
                    String songName = path.replace(rootPath, "");
                    Log.v("Game", songName);

                    //TODO:Load song
                    initialize(new CatchTheNotes(new ContentManager(this), songName), config);
                    return;
                }
            }
        }
            initialize(new CatchTheNotes(new ContentManager(this)), config);
        Log.v("Game", "Activity On create");
    }
    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex( MediaStore.Audio.AudioColumns.DATA);
            return cursor.getString(idx);
        }
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