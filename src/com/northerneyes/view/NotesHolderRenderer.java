package com.northerneyes.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.northerneyes.model.IEntity;
import com.northerneyes.model.Note;
import com.northerneyes.model.NotesHolder;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.06.13
 * Time: 20:26
 * To change this template use File | Settings | File Templates.
 */
public class NotesHolderRenderer implements IRenderer {

    private ArrayList<TextureRegion> notesTexture;
    private NotesHolder notesHolder;
    private float ppuX;
    private float ppuY;

    private NoteRenderer noteRenderer;
    public NotesHolderRenderer(ArrayList<TextureRegion> notes, float ppuX, float ppuY) {
        this.notesTexture = notes;
        this.ppuX = ppuX;
        this.ppuY = ppuY;

        noteRenderer = new NoteRenderer(notes.get(0), ppuX, ppuY);
    }

    @Override
    public void update(IEntity entity) {
       notesHolder = (NotesHolder) entity;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        for (int index = 0; index < notesHolder.particles.size(); index++)
        {
           Note particle = notesHolder.particles.get(index);
            noteRenderer.setTexture(notesTexture.get(particle.ViewType));
            noteRenderer.update(particle);
            noteRenderer.render(spriteBatch);
        }
    }
}
