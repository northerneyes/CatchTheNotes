package com.northerneyes.CatchTheNotes.controller;

import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.Services.AudioAssetManager;
import com.northerneyes.CatchTheNotes.Services.IAppService;
import com.northerneyes.CatchTheNotes.audio.MediaPlayer;
import com.northerneyes.CatchTheNotes.model.Constants;
import com.northerneyes.CatchTheNotes.model.Menu.EndGameMenu;
import com.northerneyes.CatchTheNotes.model.Menu.MainMenu;
import com.northerneyes.CatchTheNotes.model.World;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 07.07.13
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
public class EndMenuController  implements IMenuController, IHoverListener {

    private final EndGameMenu menu;
    private final PlayerHoverManager playerHoverManager;
    private final MainMenu mainMenu;
    private final World world;
    private IAppService appService = CatchTheNotes.AppService();

    public EndMenuController(World world) {
        this.world = world;
        this.mainMenu = world.getMainMenu();
        menu  = world.getEndMenu();
        playerHoverManager = new PlayerHoverManager(world.getPlayer());
        playerHoverManager.setListener(this);
    }

    @Override
    public void setPosition(float posX, float posY) {
        int state = menu.getMenuState(posX, posY);

        switch (state)
        {
            case 0:
                menu.skip();
                break;
            case 1:
                AudioAssetManager.playTouchMusic();
                if(Constants.DEBUG_END_MENU)
                {
                    menu.Init();
                }
                else
                {
                    world.setCurrentMenuType(World.MenuType.START_GAME);
                    appService.showAdMob(false);
                }
                break;
            case 2:
                mainMenu.init();
                world.setCurrentMenuType(World.MenuType.MAIN_MENU);
                appService.showAdMob(true);
                break;
        }
    }

    @Override
    public void hoverPosition(int x, int y) {
        playerHoverManager.check(menu.getMenuState(x, y) >= 0, x, y);
    }

    public void Init() {
        menu.Init();
    }

    public void update(float delta) {
        menu.update(delta);
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
