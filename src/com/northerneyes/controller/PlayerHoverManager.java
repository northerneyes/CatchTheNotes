package com.northerneyes.controller;

import com.northerneyes.model.Player;

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

    public PlayerHoverManager(Player player) {
        this.player = player;
    }

    public void check(boolean menuState)
    {
        if(menuState && !hover)
        {
            hover = true;
            listener.hover();
            player.State = Player.PlayerState.HOVER;
        }
        else if(!menuState && hover)
        {
            hover = false;
            player.State = Player.PlayerState.NORMAL;
        }
    }

    public void setListener(IHoverListener listener) {

        this.listener = listener;
    }
}
