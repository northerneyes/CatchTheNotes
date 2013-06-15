package com.northerneyes.controller;

import com.northerneyes.audio.MediaPlayer;
import com.northerneyes.audio.VisualizationData;
import com.northerneyes.model.Constants;
import com.northerneyes.model.NotesHolder;
import com.northerneyes.model.Player;
import com.northerneyes.model.World;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;


public class WorldController {

    private static final int FREQ_LENGTH = 256;
    public static final int SOURCE_COUNT = 16;
    private final VisualizationData data;
    private final float coefX;
    public Player player;
    public NotesHolder notesHolder;
    private float[] oldVolume = new float[FREQ_LENGTH];
    private HashMap<Integer, Float> volumePoints = new HashMap<Integer, Float>();
    private int bandWidth = 16;
    private int frameCount = 0;
    private boolean cheatLotsOfShapes = false;
    private int maxShapesOnBoard = 50;

    private float halfWidth = SOURCE_COUNT/2f;

    public WorldController(World world) {
		this.player = world.getPlayer();
        this.notesHolder = world.getNotesHolder();

        coefX = bandWidth*halfWidth/FREQ_LENGTH;
        MediaPlayer.play("audio/Leaves_in_the_Wind.mp3");
        //MediaPlayer.stop();
        MediaPlayer.setVisualizationEnabled();
        data = new VisualizationData(FREQ_LENGTH);
	}

	public void update(float delta) {
		player.update(delta);
        notesHolder.update(delta);
        frameCount++;
        processMusic();
        if(frameCount % 3 == 0)
        {
            addRainDrops();
        }

    }

    private void addRainDrops() {

        for (Integer key : volumePoints.keySet() )
        {
            float volume = volumePoints.get(key);
            if (cheatLotsOfShapes || notesHolder.particles.size() < maxShapesOnBoard * 2 || volume > 0.9)
            {
                notesHolder.beat((1f + key / (float) FREQ_LENGTH) * halfWidth + (float) (Math.random() * coefX), 0, volume);
                notesHolder.beat((1f - key / (float)FREQ_LENGTH) * halfWidth + (float)(Math.random() * coefX), 0, volume);
            }
        }
        volumePoints.clear();
    }

    public void processMusic()
    {
        MediaPlayer.GetVisualizationData(data);
        float volume;
        int k = 0;
        for (int i = 0; i < FREQ_LENGTH; i = i+ bandWidth)
        {
            volume = data.Frequences[k];
            if (volume > 0.1)
            {
                volume = volume + 0.5f * Math.max(0, 0.5f - volume);
            }
            volume = volume + 0.1f;
            if (volume > 0.3 && (oldVolume[i] == 0 || volume > 3 * oldVolume[i]))
            {
                volumePoints.put(i, volume);
            }
            oldVolume[i] = volume;
            k++;
        }
    }

}
