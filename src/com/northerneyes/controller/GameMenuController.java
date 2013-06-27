package com.northerneyes.controller;

import com.northerneyes.model.Menu.GameMenu;
import com.northerneyes.model.World;

/**
 * Created by George on 24.06.13.
 */
public class GameMenuController implements IMenuController {

    private final GameMenu gameMenu;
    private World world;

    public GameMenuController(World world) {
        this.world = world;
        gameMenu = world.getGameMenu();
    }

    @Override
    public void setPosition(float posX, float posY) {
        gameMenu.setMenuState(posX, posY);
        if(gameMenu.PauseState)
            world.setCurrentMenuType(World.MenuType.PAUSE);
        else
            world.setCurrentMenuType(World.MenuType.GAME);
    }
}
