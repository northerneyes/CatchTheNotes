package com.northerneyes.model;

import android.view.Menu;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.northerneyes.controller.MainMenuController;
import com.northerneyes.model.Menu.GameMenu;
import com.northerneyes.model.Menu.MainMenu;
import com.northerneyes.model.Menu.PauseMenu;

public class World {


    private PauseMenu pauseMenu;
    private MainMenu mainMenu;
    private String Songs[] = {"audio/Leaves_in_the_Wind.mp3", "audio/Centle.mp3", "audio/Letting_Go.mp3"};
    private String currentSong = "";

    public void setCurrentSong(int state) {
        currentSong = Songs[state - 1];
    }

    public String getCurrentSong()
    {
        if(currentSong.equals(""))
            return Songs[0];
        else
            return currentSong;
    }

    public enum MenuType {
        GAME,
        PAUSE,
        START_GAME,
        MAIN_MENU;

        // Converts from an ordinal value to the ResponseCode
        public static MenuType valueOf(int index) {
            MenuType[] values = MenuType.values();
            if (index < 0 || index >= values.length) {
                return GAME;
            }
            return values[index];
        }
    }

	private Player player;
	private NotesHolder notesHolder;
	public float width;
	public float height;
    private int sourceCount;
    private MenuType currentMenu = MenuType.GAME;
    private GameMenu gameMenu;

    public GameMenu getGameMenu() {
        return gameMenu;
    }

    public Player getPlayer() {
		return player;
	}

    public NotesHolder getNotesHolder(){
        return notesHolder;
    }

//    public MessageHolder getMessageHolder() {
//
//    }

	public World() {

	}

    public void setSize(float camera_width, float camera_height, int sourceCount) {
        this.width = camera_width;
        this.height = camera_height;
        this.sourceCount = sourceCount;
    }

	public void createWorld() {

		player = new Player(new Vector2(0, 0));
        notesHolder = new NotesHolder();
        gameMenu = new GameMenu(sourceCount, height);
        mainMenu = new MainMenu(sourceCount, height);
        //TODO:create message holder
        pauseMenu = new PauseMenu(sourceCount, height);
	}

    public PauseMenu getPauseMenu() {
        return pauseMenu;
    }

    public MenuType getCurrentMenuType() {
        return currentMenu;
    }

    public void setCurrentMenuType(MenuType type) {
        currentMenu = type;
    }


    public MainMenu getMainMenu() {
        return mainMenu;
    }
}
