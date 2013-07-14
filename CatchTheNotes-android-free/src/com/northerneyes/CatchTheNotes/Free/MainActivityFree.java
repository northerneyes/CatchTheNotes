package com.northerneyes.CatchTheNotes.Free;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.ContentManager;
import com.northerneyes.CatchTheNotes.LibApp;
import com.northerneyes.CatchTheNotes.Services.IAppService;

public class MainActivityFree extends LibApp implements IAppService {
    private AdView adView;

    public final static boolean ADMOB_DEBUG = true;

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

        //создём главный слой
        RelativeLayout layout = new RelativeLayout(this);
        //устанавливаем флаги, которые устанавливались в методе initialize() вместо нас
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags( WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        //представление для LibGDX
        View gameView = initializeForView(new CatchTheNotes(new ContentManager(LibApp.getContext()),this), config);

        //представление и настройка AdMob
        adView = new AdView(this, AdSize.BANNER, "a151e26a251fd71");
        AdRequest adRequest = new AdRequest();
        if(ADMOB_DEBUG)
        {
            adRequest.addTestDevice("593D76AF060B3E366D43BCE9E5517190");
        }
        adView.loadAd(adRequest);
        //добавление представление игрык слою
        layout.addView(gameView);

        RelativeLayout.LayoutParams adParams =
                new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        //добавление представление рекламы к слою
        layout.addView(adView, adParams);

       //всё соединяем в одной слое
        setContentView(layout);

        Log.v("Game", "Activity On create");
    }

    @Override
    public void showAdMob(boolean show) {
        handler.sendEmptyMessage(show ? 1 : 0);
    }
    protected Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what ==0)
                adView.setVisibility(View.GONE);
            if(msg.what==1){
                adView.setVisibility(View.VISIBLE);
                AdRequest adRequest = new AdRequest();
                if(ADMOB_DEBUG)
                {
                    adRequest.addTestDevice("593D76AF060B3E366D43BCE9E5517190");
                }
                adView.loadAd(adRequest);
                }
            }
        };
}
