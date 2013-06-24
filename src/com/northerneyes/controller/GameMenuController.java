package com.northerneyes.controller;

import com.northerneyes.model.Menu.GameMenu;
import com.northerneyes.model.World;

/**
 * Created by George on 24.06.13.
 */
public class GameMenuController implements IMenuController {

    private final GameMenu gameMenu;

    public GameMenuController(World world) {
        gameMenu = world.getGameMenu();
    }

    @Override
    public void setPosition(float posX, float posY) {

    }
}
