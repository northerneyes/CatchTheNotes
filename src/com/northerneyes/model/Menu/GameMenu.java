package com.northerneyes.model.Menu;

import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.MyGame;
import com.northerneyes.CatchTheNotes.R;

/**
 * Created by George on 24.06.13.
 */
public class GameMenu{

    private float width;
    private float height;
    private Vector2 pausePosition;
    public GameMenu(float width, float height) {
        this.height = height;
        this.width = width;

        pausePosition = new Vector2(0, height);
    }

    public String getPauseText() {
        return MyGame.getAppContext().getString(R.string.pause_menu);
    }

    public Vector2 getPausePosition() {
        return pausePosition;
    }

}
