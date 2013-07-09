package com.northerneyes.CatchTheNotes.controller;

import com.northerneyes.CatchTheNotes.model.Player;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 04.07.13
 * Time: 23:15
 * To change this template use File | Settings | File Templates.
 */
public class PlayerHoverManager {
    private boolean hover = false;
    private final Player player;
    private IHoverListener listener;
    private int x;
    private int y;

    public PlayerHoverManager(Player player) {
        this.player = player;
    }

    public void check(boolean menuState, float x, float y)
    {
        if(menuState && !hover)
        {
            hover = true;
            listener.hover(x, y);
            player.State = Player.PlayerState.HOVER;
        }
        else if(!menuState && hover)
        {
            hover = false;
            listener.unHover();
            player.State = Player.PlayerState.NORMAL;
        }
    }

    public void setListener(IHoverListener listener) {

        this.listener = listener;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
