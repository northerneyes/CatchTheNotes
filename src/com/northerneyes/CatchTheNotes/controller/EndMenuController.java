package com.northerneyes.CatchTheNotes.controller;

import com.northerneyes.CatchTheNotes.model.Menu.EndGameMenu;
import com.northerneyes.CatchTheNotes.model.World;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 07.07.13
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
public class EndMenuController  implements IMenuController {

    private final EndGameMenu menu;

    public EndMenuController(World world) {
        menu  = world.getEndMenu();
    }

    @Override
    public void setPosition(float posX, float posY) {
        //TODO: go to main menu
    }

    @Override
    public void hoverPosition(int x, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void Init() {
        menu.Init();
    }

    public void update(float delta) {
        menu.update(delta);
    }
}
