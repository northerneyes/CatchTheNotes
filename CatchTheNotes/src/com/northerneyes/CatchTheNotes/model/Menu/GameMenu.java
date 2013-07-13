package com.northerneyes.CatchTheNotes.model.Menu;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.CatchTheNotes;

/**
 * Created by George on 24.06.13.
 */
public class GameMenu{
    private float width;
    private float height;
    private Vector2 pausePosition;
    private Rectangle pauseBounds;
    public boolean PauseState = false;

    public GameMenu(float width, float height) {
        this.height = height;
        this.width = width;

        pausePosition = new Vector2(0.2f, height-0.5f);
    }

    public String getPauseText() {
        return CatchTheNotes.getContentManager().getString("pause_menu");
    }

    public Vector2 getPausePosition()
    {
        return pausePosition;
    }

    public void setPauseBounds(Rectangle bounds) {
        this.pauseBounds = bounds;
    }

    public void setMenuState(float x, float y)
    {
        PauseState = pauseBounds != null && pauseBounds.contains(x, y);
    }

    public boolean getMenuState(float x, float y)
    {
        return  pauseBounds.contains(x, y);
    }
}
