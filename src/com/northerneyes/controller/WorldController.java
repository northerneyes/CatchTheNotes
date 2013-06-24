package com.northerneyes.controller;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.audio.MediaPlayer;
import com.northerneyes.audio.VisualizationData;
import com.northerneyes.model.Constants;
import com.northerneyes.model.Note;
import com.northerneyes.model.Note.NoteType;
import com.northerneyes.model.NotesHolder;
import com.northerneyes.model.Player;
import com.northerneyes.model.Player.PulseType;
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

    public static boolean DEBUG = true;

    private int powerDownTime = 0;


    private GameMenuController gameMenuController;
    private IMenuController currentMenuController;

    public WorldController(World world) {
		this.player = world.getPlayer();
        this.notesHolder = world.getNotesHolder();

        coefX = bandWidth*halfWidth/FREQ_LENGTH;

        gameMenuController = new GameMenuController(world);
        currentMenuController = gameMenuController;

        if(!DEBUG)
        {
            MediaPlayer.play("audio/Leaves_in_the_Wind.mp3");
            //MediaPlayer.stop();
            MediaPlayer.setVisualizationEnabled();
        }
            data = new VisualizationData(FREQ_LENGTH);
	}

	public void update(float delta) {
		player.update(delta);
        notesHolder.update(delta);
        frameCount++;
        if(DEBUG)
        {
                if(notesHolder.particles.size() == 1 || notesHolder.particles.size() == 0)
                {
                    notesHolder.particles.clear();
                    notesHolder.particles.add(new Note(new Vector2(3, 7), new Vector2(0, 0), 0, 0, NoteType.NORMAL, 1f, 200, 2, 1));
                    notesHolder.particles.add(new Note(new Vector2(5, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_DOWN, 4f, 200, 2, 1));
                   // notesHolder.particles.add(new Note(new Vector2(16, 0), new Vector2(0, 0), 0, 0, NoteType.POWER_DOWN, 4f, 200, 2, 1));
                    notesHolder.particles.add(new Note(new Vector2(8, 10), new Vector2(0, 0), 0, 0, NoteType.POWER_UP, 2f, 200, 3, 1));
                    notesHolder.particles.add(new Note(new Vector2(1, 3), new Vector2(0, 0), 0, 0, NoteType.NORMAL, 1.5f, 200, 4, 1));
                    notesHolder.particles.add(new Note(new Vector2(7, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_UP, 1f, 200, 5, 1));
                    notesHolder.particles.add(new Note(new Vector2(10, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_UP, 1f, 200, 1, 1));
                    notesHolder.particles.add(new Note(new Vector2(12, 7), new Vector2(0, 0), 0, 0, NoteType.YELLOW_MADDNESS, 1f, 200, 2, 1));
                    notesHolder.particles.add(new Note(new Vector2(15, 7), new Vector2(0, 0), 0, 0, NoteType.SUCTION, 1f, 200, 2, 1));
                }
        }
        else
        {
            processMusic();
            if(frameCount % 6 == 0)
            {
                addRainDrops();
            }
        }
            updateRainDrops();

    }

    private void updateRainDrops() {
        if(player.getPower() > 0f)
        {
            player.setPower(player.getPower() - 1);
        }
        for (Note note:notesHolder.particles)
        {
            if (circlesColliding(player.Position, note.Position, note.Type, note.Size, player.Size))
            {
                collectRainDrop(note);
                switch (note.Type)
                {
                    case POWER_DOWN:
                        //explorer
                        break;
                    case YELLOW_MADDNESS:
                        for(Note not:notesHolder.particles)
                        {
                            not.Type = NoteType.POWER_UP;
                        }
                        break;

                }
            }
            else if(note.Type != NoteType.COLLECTED)
            {
                if (powerDownTime > 0 /*&& note.recycled*/)
                {
//                    if (powerDownTime != 1)
//                    {
//                        int pushAmount = 30;
//                        if (powerDownTime < 30)
//                        {
//                            rd.alpha = powerDownTime / 30;
//                            pushAmount = 3000 / powerDownTime;
//                        }
//                        rd.x = rd.x - (rd.originalCursorX - rd.x) / pushAmount;
//                        rd.y = rd.y - (rd.originalCursorY - rd.y) / pushAmount;
//                        rd.yVelocity = 0;
//                    }
//                    else
//                    {
//                        note.TTL = 0;
//                    }

                }
                if (player.getPower() > 0 && note.Type != NoteType.POWER_DOWN)
                {
                    int suctionAmount = 6;
                    if (player.getPower() < 30)
                    {
                        suctionAmount = 180 /  player.getPower();
                    }
                    note.Position.x = note.Position.x + (player.Position.x - note.Position.x) / suctionAmount;
                    note.Position.y = note.Position.y + (player.Position.y - note.Position.y) / suctionAmount;
                    note.Velocity.y = 0;
                }
            }
            else if(note.Visibility > 0) //Collected
            {
                note.Position.x = note.Position.x + (player.Position.x - note.Position.x) / 8;
                note.Position.y = note.Position.y + (player.Position.y - note.Position.y) / 8;
            }

        }
        if (powerDownTime > 0)
        {
            powerDownTime--;
        }


    }

    private void collectRainDrop(Note note) {

//       amount = Math.max(1, maxRadius - rd.size); //score
        switch (note.Type)
        {
            case NORMAL:
               // fadeOutShape(note);
                note.Type = NoteType.COLLECTED;
                player.Type = PulseType.NORMAL;
                break;
            case POWER_UP:
                player.addPowerUpCount();
                player.addCombo();
                player.setSize();
                player.Type = PulseType.GOOD;
//                if (!rd.recycled)
//                {
//                    textList = ["Yeah!", "Great!", "Good job!", "Super!", "Woohoo!", "Fabulous!", "Excellent!", "Wow!", "Amazing!", "Superb!", "Terrific!", "Fantastic!", "Splendid!", "Wonderful!", "Yes!", "Unbelievable!", "Outstanding!", "Remarkable!", "Woot!"];
//                    yPos = 100 + Math.random() * 200;
//                    showText(textList[Math.floor(Math.random() * textList.length)], 500, 13421568, 50, yPos, yPos + 50, 30);
//                }
                break;
            case POWER_DOWN:
                player.addPowerDownCount();
                player.resetCombo();
                player.setSize();
                player.Type = PulseType.BAD;
//                if (!rd.recycled)
//                {
//                    textList = ["Oops!", "Uh oh!", "Ouch!", "Oh no!", "Zowee!", "Yikes!", "Bummer!", "Shucks!", "Too bad!", "Zing!", "Kapow!"];
//                    yPos = 100 + Math.random() * 200;
//                    showText(textList[Math.floor(Math.random() * textList.length)], 500, 13369344, stage.stageWidth - 200, yPos, yPos + 50, 30);
//                }
                powerDownTime = 60;
                break;
            case SUCTION:
                player.addPurplePowerCount();
                player.Type = PulseType.SUCTION;
//                showText("Purple Power!", 2000, 16711935, NaN, 50, NaN, 30);
                player.setPower(200);
                break;
            case YELLOW_MADDNESS:
                //all yellow
                player.addYellowMadnessCount();
                player.Type = PulseType.SUCTION;
//                showText("Yellow Madness!", 2000, 16776960, NaN, 90, NaN, 30);
                break;
        }
        player.addShapeCount();
//        addToScore(amount * combo);
        if(note.Type != NoteType.COLLECTED)
            note.TTL = 0;
    }

    private boolean circlesColliding(Vector2 playerPos, Vector2 notePos, Note.NoteType type, float noteSize, float playerSize) {
        if(type == NoteType.POWER_DOWN)
            return Math.abs(playerPos.x - notePos.x) - 0.8f*playerSize <= 0 &&
                    Math.abs(playerPos.y - notePos.y) - 0.8 * playerSize <= 0;
        else if(type != NoteType.COLLECTED)
            return Math.abs(playerPos.x - notePos.x) - 0.4f*(playerSize + noteSize) <= 0 &&
                    Math.abs(playerPos.y - notePos.y) - 0.4f*(playerSize + noteSize) <= 0;
        else
            return false;

    }


    private void addRainDrops() {

        for (Integer key : volumePoints.keySet() )
        {
            float volume = volumePoints.get(key);
            if (cheatLotsOfShapes || notesHolder.particles.size() < maxShapesOnBoard  || volume > 0.9)
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

    public IMenuController getCurrentMenu() {
        return currentMenuController;
    }
}
