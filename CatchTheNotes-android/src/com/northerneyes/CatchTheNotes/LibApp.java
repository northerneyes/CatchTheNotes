package com.northerneyes.CatchTheNotes;

import android.content.Context;
import com.badlogic.gdx.backends.android.AndroidApplication;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 14.07.13
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */
public class LibApp  extends AndroidApplication {
    /** Instance of the current application. */
    private static LibApp instance;

    /**
     * Constructor.
     */
    public LibApp() {
        instance = this;
    }

    /**
     * Gets the application context.
     *
     * @return the application context
     */
    public static Context getContext() {
        return instance;
    }
}
