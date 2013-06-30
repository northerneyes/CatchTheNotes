package com.northerneyes.model.Menu;

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
    public final Vector2 SongPosition;

    public final Vector2[] SongNamePositions;

    public String AppNameText = MyGame.getAppContext().getString(R.string.app_name);
    public String PlayText = MyGame.getAppContext().getString(R.string.play_menu);
    public String SongText = MyGame.getAppContext().getString(R.string.song_menu);

    private Rectangle[] bounds;
    public MainMenu(float width, float height) {
        AppNamePosition = new Vector2(width/2, height - 2);
        PlayPosition = new Vector2(AppNamePosition.x, AppNamePosition.y - 2);
        SongPosition = new Vector2(6, PlayPosition.y - 2);

        SongNamePositions = new Vector2[2];
    }

    public void SetBounds(Rectangle[] bounds)
    {
        this.bounds =  bounds;
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
