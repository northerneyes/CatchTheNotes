package com.northerneyes.CatchTheNotes.controller;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import com.northerneyes.CatchTheNotes.Services.AudioAssetManager;
import com.northerneyes.CatchTheNotes.model.Menu.MainMenu;
import com.northerneyes.CatchTheNotes.model.World;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 30.06.13
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class MainMenuController  implements IMenuController, IHoverListener, TweenCallback {
    private final MainMenu menu;
    private final PlayerHoverManager playerHoverManager;
    private World world;

    public MainMenuController(World world) {
        this.world = world;
        menu = world.getMainMenu();
        playerHoverManager = world.getPlayerHoverManager();
        playerHoverManager.setListener(this);
    }
    @Override
    public void setPosition(float posX, float posY) {

        int state = menu.getMenuState(posX, posY);
        switch (state)
        {
            case 0:
                AudioAssetManager.playTouchMusic();
                menu.startFade(this);
                break;
            case 1:
            case 2:
            case 3:
                AudioAssetManager.playTouchMusic();
                menu.CurrentSongIndex = state - 1;
                world.setCurrentSong(state);
                break;
        }
    }

    @Override
    public void hoverPosition(int x, int y) {
        playerHoverManager.check(menu.getMenuState(x, y) >= 0, x, y);
        playerHoverManager.setPosition(x, y);
    }

    @Override
    public void hover(float x, float y) {
        int index = menu.getMenuState(x, y);
        menu.HoverSongIndex = index - 1;
        AudioAssetManager.playHoverMusic();
    }

    @Override
    public void unHover() {
        menu.HoverSongIndex = - 1;
    }

    @Override
    public void onEvent(int i, BaseTween<?> baseTween) {
        world.setCurrentMenuType(World.MenuType.START_GAME);
//        menu.init();
    }
}