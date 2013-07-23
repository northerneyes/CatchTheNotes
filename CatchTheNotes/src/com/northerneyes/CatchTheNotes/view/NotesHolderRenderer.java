package com.northerneyes.CatchTheNotes.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.northerneyes.CatchTheNotes.model.IEntity;
import com.northerneyes.CatchTheNotes.model.Note;
import com.northerneyes.CatchTheNotes.model.Note.ShapeType;
import com.northerneyes.CatchTheNotes.model.NotesHolder;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 09.06.13
 * Time: 20:26
 * To change this template use File | Settings | File Templates.
 */
public class NotesHolderRenderer implements IRenderer {

    private HashMap<ShapeType, List<TextureRegion>> shapesTexture;
    private NotesHolder notesHolder;
    private float ppuX;
    private float ppuY;


    private NoteRenderer noteRenderer;
    public NotesHolderRenderer(HashMap<ShapeType, List<TextureRegion>> notes, float ppuX, float ppuY, float CAMERA_WIDTH, int sourceCount) {
        this.shapesTexture = notes;
        this.ppuX = ppuX;
        this.ppuY = ppuY;

        noteRenderer = new NoteRenderer(ppuX, ppuY, CAMERA_WIDTH, sourceCount);
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
                List<TextureRegion> texture = shapesTexture.get(particle.ShapeViewType);
                noteRenderer.setTexture(texture.get(particle.ViewType));
                noteRenderer.update(particle);
                noteRenderer.render(spriteBatch);
            }

    }
}
