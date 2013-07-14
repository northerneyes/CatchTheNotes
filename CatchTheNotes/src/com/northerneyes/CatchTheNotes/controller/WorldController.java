package com.northerneyes.CatchTheNotes.controller;

import com.badlogic.gdx.math.Vector2;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.Services.IAppService;
import com.northerneyes.CatchTheNotes.Services.ScoreManager;
import com.northerneyes.CatchTheNotes.audio.IMediaPlayerListener;
import com.northerneyes.CatchTheNotes.audio.MediaPlayer;
import com.northerneyes.CatchTheNotes.audio.VisualizationData;
import com.northerneyes.CatchTheNotes.model.*;
import com.northerneyes.CatchTheNotes.model.Menu.EndGameMenu;
import com.northerneyes.CatchTheNotes.model.Message;
import com.northerneyes.CatchTheNotes.model.Note.NoteType;
import com.northerneyes.CatchTheNotes.model.Player.PulseType;
import com.northerneyes.CatchTheNotes.view.WorldRenderer;

import java.util.HashMap;


public class WorldController implements IMediaPlayerListener {

    public Player player;
    public NotesHolder notesHolder;
    private final MessageHolder messageHolder;
    private World world;
    private ScoreManager scoreManager;
    private final VisualizationData data;
    private IAppService appService;

    public static final int SOURCE_COUNT = 16;
    private static final int FREQ_LENGTH = 256;
    private int bandWidth = 16;
    private int frameCount = 0;
    private float timeCount = 0;
    private int maxShapesOnBoard = 50;
    private float halfWidth = SOURCE_COUNT/2f;
    private int powerDownTime = -1;
    private boolean stopFlag;

    private final float coefX;
    private final float height;

    private float[] oldVolume = new float[FREQ_LENGTH];
    private HashMap<Integer, Float> volumePoints = new HashMap<Integer, Float>();


    private GameMenuController gameMenuController;
    private IMenuController currentMenuController;
    private final PauseMenuController pauseMenuController;
    private final MainMenuController mainMenuController;
    private final EndMenuController endMenuController;
    private boolean yellowMadness = true;

    public WorldController(World world) {
        this.height =  WorldRenderer.CAMERA_HEIGHT;
        this.world = world;
        this.player = world.getPlayer();
        this.notesHolder = world.getNotesHolder();
        this.messageHolder = world.getMessageHolder();
        scoreManager = world.getScoreManager();
        appService = CatchTheNotes.AppService();

        coefX = bandWidth*halfWidth/FREQ_LENGTH;

        gameMenuController = new GameMenuController(world);
        pauseMenuController = new PauseMenuController(world);
        mainMenuController = new MainMenuController(world);
        endMenuController = new EndMenuController(world);

        MediaPlayer.setListener(this);
        if(!world.getSong().equals(""))
        {
            world.setCurrentMenuType(World.MenuType.START_GAME);
        }

        if(Constants.DEBUG_END_MENU)
            endMenuController.Init();
        MediaPlayer.setVisualizationEnabled();
        appService.showAdMob(true);
        data = new VisualizationData(FREQ_LENGTH);
	}

    @Override
    public void onStop() {

        //notesHolder.particles.clear();
        stopFlag = true;
    }

    public void Stop()
    {
        endMenuController.Init();
        appService.showAdMob(true);
        world.SongPosition = 0;
        timeCount = 0;
        world.setCurrentMenuType(World.MenuType.END_GAME);
        stopFlag  = false;
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
                frameCount = 0;
                timeCount = 0;
                yellowMadness = true;
                if(!Constants.DEBUG)
                {
                    MediaPlayer.play(world.getCurrentSong(), world.IsExternalFile());
                }
                player.clear();
                scoreManager.clear();

                world.setCurrentMenuType(World.MenuType.GAME);
                return;
            case END_GAME:
                MediaPlayer.stop();
                frameCount = 0;
                timeCount = 0;
                endMenuController.update(delta);
                currentMenuController = endMenuController;
                world.setCurrentMenuType(World.MenuType.END_GAME);
                return;
            case GAME:
                MediaPlayer.resume();
                currentMenuController = gameMenuController;
                notesHolder.update(delta);
                messageHolder.update(delta);
                frameCount++;
                if(Constants.DEBUG)
                {
                    if(notesHolder.particles.size() == 1 || notesHolder.particles.size() == 0)
                    {
                        scoreManager.addTotalShape(8);
                        notesHolder.particles.clear();
//                        notesHolder.particles.add(new Note(new Vector2(3, 7), new Vector2(0, 0), 0, 0, NoteType.NORMAL, 1f, 200, 2, 1));
//                        notesHolder.particles.add(new Note(new Vector2(5, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_DOWN, 1.5f, 200, 2, 1));
//                        notesHolder.particles.add(new Note(new Vector2(7, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_UP, 2f, 200, 3, 1));
//                        notesHolder.particles.add(new Note(new Vector2(8, 7), new Vector2(0, 0), 0, 0, NoteType.NORMAL, 2.5f, 200, 4, 1));
//                        notesHolder.particles.add(new Note(new Vector2(10, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_UP, 3f, 200, 1, 1));
//                        notesHolder.particles.add(new Note(new Vector2(12, 7), new Vector2(0, 0), 0, 0, NoteType.YELLOW_MADDNESS, 3.5f, 200, 2, 1));
//                        notesHolder.particles.add(new Note(new Vector2(15, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_DOWN, 4f, 200, 2, 1));
                        notesHolder.particles.add(new Note(new Vector2(1, 7), new Vector2(0, 0), 0, 0, NoteType.NORMAL, 4f, 200, 2, 1));
                        notesHolder.particles.add(new Note(new Vector2(2, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_DOWN, 4f, 200, 2, 1));
                        notesHolder.particles.add(new Note(new Vector2(3, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_UP, 1f, 200, 3, 1));
                        notesHolder.particles.add(new Note(new Vector2(4, 7), new Vector2(0, 0), 0, 0, NoteType.NORMAL, 3.5f, 200, 4, 1));
                        notesHolder.particles.add(new Note(new Vector2(5, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_UP, 3.5f, 200, 1, 1));
                        notesHolder.particles.add(new Note(new Vector2(6, 7), new Vector2(0, 0), 0, 0, NoteType.YELLOW_MADDNESS, 1f, 200, 2, 1));
                        notesHolder.particles.add(new Note(new Vector2(7, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_DOWN, 3f, 200, 2, 1));
                        notesHolder.particles.add(new Note(new Vector2(8, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_DOWN, 3f, 200, 2, 1));
                        notesHolder.particles.add(new Note(new Vector2(9, 7), new Vector2(0, 0), 0, 0, NoteType.YELLOW_MADDNESS, 1f, 200, 2, 1));
                        notesHolder.particles.add(new Note(new Vector2(10, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_DOWN, 2.5f, 200, 2, 1));
                        notesHolder.particles.add(new Note(new Vector2(11, 7), new Vector2(0, 0), 0, 0, NoteType.POWER_DOWN, 2.5f, 200, 2, 1));
                    }
                }
                else
                {
                    processMusic();
                    timeCount += delta;
                    if(frameCount % 6 == 0)
                    {
                        world.SongPosition = MediaPlayer.getPosition(timeCount);
                        addRainDrops();
                    }
                }
                updateRainDrops();
                break;
            case PAUSE:
                MediaPlayer.pause();
                currentMenuController = pauseMenuController;
        }
    }

    private void updateRainDrops() {
        if(stopFlag && notesHolder.particles.size() == 0)
        {
            Stop();
            return;
        }

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
            if (notesHolder.particles.size() < maxShapesOnBoard  || volume > 0.9)
            {
                addRainDrop((1f + key / (float) FREQ_LENGTH) * halfWidth + (float) (Math.random() * coefX), volume);
                addRainDrop((1f - key / (float) FREQ_LENGTH) * halfWidth + (float) (Math.random() * coefX), volume);
            }
        }
        volumePoints.clear();
    }

    private void addRainDrop(float x, float _amp)
    {
        float amp = _amp + 0.2f;

        Note.NoteType type = Note.NoteType.NORMAL;
        if(amp > 0.5f)
        {
            int selector = (int) (Math.random() * 1200);
            if (selector < 20) // враг
                type = Note.NoteType.POWER_UP;
            else if (selector < 40) // желтый
                type = Note.NoteType.POWER_DOWN;
            else if (selector < 42 &&  player.getPower() == 0) // пурпурный
                type = Note.NoteType.SUCTION;
            else if (selector == 42 &&  player.getPower() == 0 && yellowMadness)
            {
                type = Note.NoteType.YELLOW_MADDNESS;
                yellowMadness = false;
            }
        }
        scoreManager.addTotalShape(1);
        notesHolder.beat(x, height, -amp, type);
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
