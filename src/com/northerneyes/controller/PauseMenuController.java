package com.northerneyes.controller;

import com.northerneyes.audio.MediaPlayer;
import com.northerneyes.model.Menu.GameMenu;
import com.northerneyes.model.Menu.PauseMenu;
import com.northerneyes.model.World;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 29.06.13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */
public class PauseMenuController implements IMenuController, IHoverListener {
    private final PauseMenu menu;
    private final PlayerHoverManager playerHoverManager;
    private World world;

    public PauseMenuController(World world) {
        this.world = world;
        menu = world.getPauseMenu();
        playerHoverManager = world.getPlayerHoverManager();
        playerHoverManager.setListener(this);
    }
    @Override
    public void setPosition(float posX, float posY) {

        int state = menu.getMenuState(posX, posY);
        switch (state)
        {
            case 0:
                world.setCurrentMenuType(World.MenuType.GAME);
                break;
            case 1:
                world.setCurrentMenuType(World.MenuType.START_GAME);
                break;
            case 2:
                MediaPlayer.dispose();
                world.setCurrentMenuType(World.MenuType.MAIN_MENU);
                break;
        }
    }

    @Override
    public void hoverPosition(int x, int y) {
        playerHoverManager.check(menu.getMenuState(x, y) >= 0, x, y);
    }


    @Override
    public void hover(float x, float y) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void unHover() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
