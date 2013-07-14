package com.northerneyes.CatchTheNotes;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.northerneyes.CatchTheNotes.Services.IContentManager;

public class CatchTheNotesDesktop {

    public static void main(String[] args) {
        new LwjglApplication(new CatchTheNotes(new ContentManager(), new AppService()), "Catch The Notes", 800, 480, true);
    }
}
