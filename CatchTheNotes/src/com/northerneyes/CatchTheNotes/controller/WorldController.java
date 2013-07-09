package com.northerneyes.CatchTheNotes.controller;

import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.Services.ScoreManager;
import com.northerneyes.CatchTheNotes.audio.MediaPlayer;
import com.northerneyes.CatchTheNotes.audio.VisualizationData;
import com.northerneyes.CatchTheNotes.model.*;
import com.northerneyes.CatchTheNotes.model.Menu.EndGameMenu;
import com.northerneyes.CatchTheNotes.model.Message;
import com.northerneyes.CatchTheNotes.model.Note.NoteType;
import com.northerneyes.CatchTheNotes.model.Player.PulseType;
import com.northerneyes.CatchTheNotes.view.WorldRenderer;

import java.util.HashMap;


public class WorldController {
    private final EndMenuController endMenuController;
    public Player player;
    public static final int SOURCE_COUNT = 16;
    public NotesHolder notesHolder;

    private static final int FREQ_LENGTH = 256;
    private final VisualizationData data;
    private final float coefX;
    private final float height;
    private final MessageHolder messageHolder;

    private float[] oldVolume = new float[FREQ_LENGTH];
    private HashMap<Integer, Float> volumePoints = new HashMap<Integer, Float>();
    private int bandWidth = 16;
    private int frameCount = 0;
    private boolean cheatLotsOfShapes = false;
    private int maxShapesOnBoard = 50;

    private float halfWidth = SOURCE_COUNT/2f;

    public static boolean DEBUG = true;
    public static boolean DEBUG_END_MENU = true;

    private int powerDownTime = -1;

    private GameMenuController gameMenuController;
    private IMenuController currentMenuController;
    private final PauseMenuController pauseMenuController;
    private final MainMenuController mainMenuController;

    private World world;
    private ScoreManager scoreManager;

    public WorldController(World world) {
        this.height =  WorldRenderer.CAMERA_HEIGHT;
        this.world = world;
        this.player = world.getPlayer();
        this.notesHolder = world.getNotesHolder();
        this.messageHolder = world.getMessageHolder();
        scoreManager = world.getScoreManager();
       // this.messageHolder = world.getMessageHolder();
        coefX = bandWidth*halfWidth/FREQ_LENGTH;

        gameMenuController = new GameMenuController(world);
        pauseMenuController = new PauseMenuController(world);
        mainMenuController = new MainMenuController(world);
        endMenuController = new EndMenuController(world);

       // currentMenuController = gameMenuController;

        if(DEBUG_END_MENU)
            endMenuController.Init();

        if(!DEBUG)
        {
//            MediaPlayer.play("audio/Leaves_in_the_Wind.mp3");
            //MediaPlayer.stop();
            MediaPlayer.setVisualizationEnabled();
        }
            data = new VisualizationData(FREQ_LENGTH);
	}

	public void update(float delta) {
		player.update(delta);

        switch (world.getCurrentMenuType())
        {
            case MAIN_MENU:
                MediaPlayer.dispose();
                player.clear();
                scoreManager.clear();
                currentMenuController = mainMenuController;

                DEBUG = false;
                notesHolder.update(delta);
                frameCount++;
                if (frameCount % 20 == 0)
                {
                    notesHolder.beat((float)(Math.random() * SOURCE_COUNT), height, -(float)Math.random(), NoteType.NORMAL);
                }
                updateRainDrops();
                return;
            case START_GAME:  //Restart
                MediaPlayer.stop();
                if(!DEBUG)
                {
                    MediaPlayer.play(world.getCurrentSong());
                }
                player.clear();
                scoreManager.clear();

                world.setCurrentMenuType(World.MenuType.GAME);
                return;
            case END_GAME:
                MediaPlayer.stop();
                endMenuController.update(delta);
                currentMenuController = endMenuController;
                world.setCurrentMenuType(World.MenuType.END_GAME);
                return;
            case GAME:
                DEBUG = true;
                currentMenuController = gameMenuController;
                notesHolder.update(delta);
                messageHolder.update(delta);
                frameCount++;
                if(DEBUG)
                {
                    if(notesHolder.particles.size() == 1 || notesHolder.particles.size() == 0)
                    {
                        scoreManager.addTotalShape(8);
                        notesHolder.particles.clear();
                        notesHolder.particles.add(new Note(new Vector2(3, 7), new Vector2(0, 0), 0, 0, NoteType.NORMAL, 1f, 200, 2, 1));
                        notesHolder.particles.add(new Note(new Vector2(5, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_DOWN, 4f, 200, 2, 1));
                        // notesHolder.particles.add(new Note(new Vector2(16, 0), new Vector2(0, 0), 0, 0, NoteType.POWER_DOWN, 4f, 200, 2, 1));
                        notesHolder.particles.add(new Note(new Vector2(8, 10), new Vector2(0, 0), 0, 0, NoteType.POWER_UP, 2f, 200, 3, 1));
                        notesHolder.particles.add(new Note(new Vector2(1, 3), new Vector2(0, 0), 0, 0, NoteType.NORMAL, 1.5f, 200, 4, 1));
                        notesHolder.particles.add(new Note(new Vector2(7, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_UP, 1f, 200, 5, 1));
                        notesHolder.particles.add(new Note(new Vector2(10, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_UP, 1f, 200, 1, 1));
                        notesHolder.particles.add(new Note(new Vector2(12, 7), new Vector2(0, 0), 0, 0, NoteType.YELLOW_MADDNESS, 1f, 200, 2, 1));
                        notesHolder.particles.add(new Note(new Vector2(15, 11), new Vector2(0, 0), 0, 0, NoteType.POWER_DOWN, 2f, 200, 2, 1));
                        // notesHolder.particles.add(new Note(new Vector2(15, 7), new Vector2(0, 0), 0, 0, NoteType.SUCTION, 1f, 200, 2, 1));
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
                break;
            case PAUSE:
                currentMenuController = pauseMenuController;
        }
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
                int origCombo = player.getCombo();
                float origCursorSize = player.Size;
                collectRainDrop(note);
                switch (note.Type)
                {
                    case POWER_DOWN:
                        int count = (int) Math.floor(0.9 * (origCombo - player.getCombo()));
                        int k = 0;
                        while (k < count)
                        {
                            float xPos;
                            if (Math.random() > 0.5)
                            {
                                xPos = (float) (player.Position.x - origCursorSize/3f  - Math.random() * 0.5f - 0.2f);
                            }
                            else
                            {
                                xPos = (float) (player.Position.x + origCursorSize/3f  + Math.random() * 0.5f + 0.2f);
                            }

                            float yPos = (float) (player.Position.y + origCursorSize + Math.random() * 0.5f + 0.2f);
                            notesHolder.addRecycledParticle(new Note(new Vector2(xPos, yPos), NoteType.POWER_UP, 0.7f, 2, true));
                            ++k;
                        }
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
                if(powerDownTime == 0 && note.Recycled)
                {
                    note.Visibility = 0f;
                    powerDownTime = -1;
                }
                if (powerDownTime > 0 && note.Recycled)
                {
                        int pushAmount = 60;
                        if (powerDownTime < 60)
                        {
                            note.Visibility = powerDownTime / 60;
                                pushAmount = 3000 / powerDownTime;
                        }
                        note.Position.x = note.Position.x - (player.Position.x - note.Position.x) /  pushAmount;
                        note.Position.y = note.Position.y - (player.Position.y - note.Position.y) /  pushAmount;

                        note.Velocity.y = 0;
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
                note.Position.x = note.Position.x + (player.Position.x - note.Position.x) / 6;
                note.Position.y = note.Position.y + (player.Position.y - note.Position.y) / 6;
            }

        }
        if (powerDownTime > 0)
        {
            powerDownTime--;
        }


    }

    private void collectRainDrop(Note note) {

        float amount = Math.max(1, 3.5f*(Note.MAX_SIZE - note.Size)); //score
        switch (note.Type)
        {
            case NORMAL:
                note.Type = NoteType.COLLECTED;
                player.Type = PulseType.NORMAL;
                break;
            case POWER_UP:
                scoreManager.addPowerUpCount();
                player.addCombo();
                player.setSize();
                player.Type = PulseType.GOOD;
                if (!note.Recycled)
                {
                    float yPos = (float) (1 + Math.random() * 8f);
                    messageHolder.addMessage(new Message(0.25f, 4, yPos), NoteType.POWER_UP);
                }
                break;
            case POWER_DOWN:
                scoreManager.addPowerDownCount();
                scoreManager.resetCombo();
                player.setSize();
                player.Type = PulseType.BAD;
                if (!note.Recycled)
                {
                    float yPos = (float) (1 + Math.random() * 8f);
                    messageHolder.addMessage(new Message(0.25f, SOURCE_COUNT - 4, yPos), NoteType.POWER_DOWN);
                }
                powerDownTime = 120;
                break;
            case SUCTION:
                scoreManager.addPurplePowerCount();
                player.Type = PulseType.SUCTION;
                messageHolder.addMessage(new Message(0.5f, SOURCE_COUNT/2, 7), NoteType.SUCTION);
                player.setPower(200);
                break;
            case YELLOW_MADDNESS:
                scoreManager.addYellowMadnessCount();
                player.Type = PulseType.SUCTION;
                messageHolder.addMessage(new Message(0.5f, SOURCE_COUNT/2, 7), NoteType.YELLOW_MADDNESS);
                break;
        }
        scoreManager.addShapeCount();
        scoreManager.addToScore(amount);
        if(note.Type != NoteType.COLLECTED)
            note.TTL = 0;
    }

    private boolean circlesColliding(Vector2 playerPos, Vector2 notePos, Note.NoteType type, float noteSize, float playerSize) {
        if(type == NoteType.POWER_DOWN)
            return Math.abs(playerPos.x - notePos.x) - 0.4f*playerSize <= 0 &&
                    Math.abs(playerPos.y - notePos.y) - 0.4f * playerSize <= 0;
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
                scoreManager.addTotalShape(2);
                notesHolder.beat((1f + key / (float) FREQ_LENGTH) * halfWidth + (float) (Math.random() * coefX), height, -volume);
                notesHolder.beat((1f - key / (float)FREQ_LENGTH) * halfWidth + (float)(Math.random() * coefX), height, -volume);
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
