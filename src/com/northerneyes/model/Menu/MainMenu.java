package com.northerneyes.model.Menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.MyGame;
import com.northerneyes.CatchTheNotes.R;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 30.06.13
 * Time: 9:09
 * To change this template use File | Settings | File Templates.
 */
public class MainMenu{

    public final Vector2 AppNamePosition;
    public final Vector2 PlayPosition;
    public final Vector2 SongTextPosition;

    public final Vector2[] SongNamePositions;

    public String AppNameText = MyGame.getAppContext().getString(R.string.app_name);
    public String PlayText = MyGame.getAppContext().getString(R.string.play_menu);
    public String SongText = MyGame.getAppContext().getString(R.string.song_menu);

    private Rectangle[] bounds;
    public Color AppNameTextColor = new Color(1f, 1f, 203f/255f, 1f);
    public Color PlayTextColor = new Color(1f, 1f, 1f, 1f);
    public Color SongTextColor = new Color(1f, 1f, 1f, 1f);

    public String SongsName[] =   {
            MyGame.getAppContext().getString(R.string.leave_in_the_wind),
            MyGame.getAppContext().getString(R.string.centle),
            MyGame.getAppContext().getString(R.string.letting_go)
    };

    public MainMenu(float width, float height) {
        AppNamePosition = new Vector2(width/2, height - 2);
        PlayPosition = new Vector2(AppNamePosition.x, AppNamePosition.y - 3);
        SongTextPosition = new Vector2(2, PlayPosition.y - 2);
        SongNamePositions = new Vector2[3];
    }

    public void setBounds(Rectangle[] bounds)
    {
        this.bounds =  bounds;
    }

    public Rectangle[] getBounds()
    {
        return this.bounds;
    }

    public int getMenuState(float x, float y)
    {
        for(int i = 0; i < bounds.length; i++)
        {
            if(bounds[i].contains(x, y))
            {
                return i;
            }
        }
        return -1;
    }
}
