package com.northerneyes.CatchTheNotes;

import com.badlogic.gdx.Gdx;
import com.northerneyes.CatchTheNotes.Services.IAppService;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 14.07.13
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
public class AppService implements IAppService {
    @Override
    public void showAdMob(boolean show) {
        Gdx.app.log("Show adMob", Boolean.toString(show));
    }
}
