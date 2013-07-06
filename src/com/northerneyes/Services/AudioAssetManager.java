package com.northerneyes.Services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 06.07.13
 * Time: 10:20
 * To change this template use File | Settings | File Templates.
 */
public class AudioAssetManager {
    private static Music buttonHover;
    private static Music buttonTouch;

    public static void playTouchMusic()
    {
        buttonTouch.play();
    }

    public static void playHoverMusic()
    {
        buttonHover.play();
    }

    public static void dispose()
    {
        buttonHover.dispose();
        buttonTouch.dispose();
    }

    public static void init() {
        buttonHover = Gdx.audio.newMusic(Gdx.files.internal("audio/button_hover.mp3"));
        buttonTouch = Gdx.audio.newMusic(Gdx.files.internal("audio/button_touch.mp3"));
    }
}
