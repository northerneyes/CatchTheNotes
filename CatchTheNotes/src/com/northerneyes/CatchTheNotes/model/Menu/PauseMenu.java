package com.northerneyes.CatchTheNotes.model.Menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.R;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 29.06.13
 * Time: 17:50
 * To change this template use File | Settings | File Templates.
 */
public class PauseMenu {

    public final Vector2 ResumePosition;
    public final Vector2 RestartPosition;
    public final Vector2 EndGamePosition;
    //private final  height;
    private Rectangle resumeBounds;
    private Rectangle restartBounds;
    private Rectangle endGameBounds;
    public Color ResumeTextColor = new Color(1f, 1f, 203f/255f, 1f);
    public Color RestartTextColor = new Color(203f/255f, 1f, 1f, 1f);
    public Color EndGameTextColor = new Color(1f, 203f/255f, 1f, 1f);

    public PauseMenu(float width, float height) {
        Vector2 center = new Vector2(width/2, height/2);
        ResumePosition = new Vector2(center.x, center.y + height/4);
        RestartPosition = center;
        EndGamePosition = new Vector2(center.x, center.y - height/4);
    }

    public String getResumeText()
    {
        return  CatchTheNotes.getContentManager().getString("resume_menu");
    }

    public String getRestartText()
    {
        return  CatchTheNotes.getContentManager().getString("restart_menu");
    }

    public String getEndGameText()
    {
        return  CatchTheNotes.getContentManager().getString("end_game_menu");
    }

    public void setResumeBounds(Rectangle bounds) {
        this.resumeBounds = bounds;
    }

    public void setRestartBounds(Rectangle bounds) {
        this.restartBounds = bounds;
    }

    public void setEndGameBounds(Rectangle bounds) {
        this.endGameBounds = bounds;
    }

    public int getMenuState(float x, float y)
    {
        if(resumeBounds.contains(x, y))
            return 0;
        if(restartBounds.contains(x, y))
            return 1;
        if(endGameBounds.contains(x, y))
            return 2;
        return -1;
    }


}
