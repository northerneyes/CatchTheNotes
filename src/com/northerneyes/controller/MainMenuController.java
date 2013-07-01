package com.northerneyes.controller;

import com.northerneyes.model.Menu.MainMenu;
import com.northerneyes.model.Menu.PauseMenu;
import com.northerneyes.model.World;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 30.06.13
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class MainMenuController  implements IMenuController  {
    private final MainMenu menu;
    private World world;

    public MainMenuController(World world) {
        this.world = world;
        menu = world.getMainMenu();
    }
    @Override
    public void setPosition(float posX, float posY) {

        int state = menu.getMenuState(posX, posY);
        switch (state)
        {
            case 0:
                world.setCurrentMenuType(World.MenuType.START_GAME);
                break;
            case 1:
            case 2:
            case 3:
                world.setCurrentSong(state);
                break;
        }
    }
}
