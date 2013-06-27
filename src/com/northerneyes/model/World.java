package com.northerneyes.model;

import android.view.Menu;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.northerneyes.model.Menu.GameMenu;

public class World {

    public enum MenuType {
        GAME,
        PAUSE;

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
	}

    public MenuType getCurrentMenuType() {
        return currentMenu;
    }

    public void setCurrentMenuType(MenuType type) {
        currentMenu = type;
    }
}
