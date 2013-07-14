package com.northerneyes.CatchTheNotes.controller;

import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.Services.AudioAssetManager;
import com.northerneyes.CatchTheNotes.Services.IAppService;
import com.northerneyes.CatchTheNotes.audio.MediaPlayer;
import com.northerneyes.CatchTheNotes.model.Menu.PauseMenu;
import com.northerneyes.CatchTheNotes.model.World;

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
    private IAppService appService = CatchTheNotes.AppService();

    public PauseMenuController(World world) {
        this.world = world;
        menu = world.getPauseMenu();
        playerHoverManager = new PlayerHoverManager(world.getPlayer());
        playerHoverManager.setListener(this);
    }
    @Override
    public void setPosition(float posX, float posY) {

        int state = menu.getMenuState(posX, posY);

        switch (state)
        {
            case 0:
                world.setCurrentMenuType(World.MenuType.GAME);
                appService.showAdMob(false);
                break;
            case 1:
                AudioAssetManager.playTouchMusic();
                //TODO: Fade menu
                world.setCurrentMenuType(World.MenuType.START_GAME);
                appService.showAdMob(false);
                break;
            case 2:
                MediaPlayer.dispose();
                world.getMainMenu().init();
                world.setCurrentMenuType(World.MenuType.MAIN_MENU);
                appService.showAdMob(true);
                break;
        }
    }

    @Override
    public void hoverPosition(int x, int y) {
        playerHoverManager.check(menu.getMenuState(x, y) >= 0, x, y);
    }


    @Override
    public void hover(float x, float y) {
        AudioAssetManager.playHoverMusic();
    }

    @Override
    public void unHover() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
