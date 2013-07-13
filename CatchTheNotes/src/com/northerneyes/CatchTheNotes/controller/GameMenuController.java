package com.northerneyes.CatchTheNotes.controller;

import com.northerneyes.CatchTheNotes.model.Menu.GameMenu;
import com.northerneyes.CatchTheNotes.model.World;

/**
 * Created by George on 24.06.13.
 */
public class GameMenuController implements IMenuController, IHoverListener {

    private final GameMenu gameMenu;
    private final PlayerHoverManager playerHoverManager;
    private World world;

    public GameMenuController(World world) {
        this.world = world;
        gameMenu = world.getGameMenu();
        playerHoverManager = new PlayerHoverManager(world.getPlayer());
        playerHoverManager.setListener(this);
    }

    @Override
    public void setPosition(float posX, float posY) {
        gameMenu.setMenuState(posX, posY);
        if(gameMenu.PauseState)
            world.setCurrentMenuType(World.MenuType.PAUSE);
        else
            world.setCurrentMenuType(World.MenuType.GAME);
    }

    @Override
    public void hoverPosition(int x, int y) {
        playerHoverManager.check(gameMenu.getMenuState(x, y), x, y);
    }


    @Override
    public void hover(float x, float y) {
    }

    @Override
    public void unHover() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
