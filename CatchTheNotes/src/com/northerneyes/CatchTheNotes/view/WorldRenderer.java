package com.northerneyes.CatchTheNotes.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.northerneyes.CatchTheNotes.CatchTheNotes;
import com.northerneyes.CatchTheNotes.controller.WorldController;
import com.northerneyes.CatchTheNotes.model.Constants;
import com.northerneyes.CatchTheNotes.model.Note;
import com.northerneyes.CatchTheNotes.model.NotesHolder;
import com.northerneyes.CatchTheNotes.model.World;
import com.northerneyes.CatchTheNotes.view.MenuRenderers.EndGameMenuRenderer;
import com.northerneyes.CatchTheNotes.view.MenuRenderers.GameMenuRenderer;
import com.northerneyes.CatchTheNotes.view.MenuRenderers.MainMenuRenderer;
import com.northerneyes.CatchTheNotes.view.MenuRenderers.PauseMenuRenderer;

import java.util.*;

public class WorldRenderer {

	public static  float CAMERA_HEIGHT = 15f;
    public static float CAMERA_WIDTH = 25f;



	private World world;
	public OrthographicCamera cam;


	public float ppuX;
	public float ppuY;

    private SpriteBatch spriteBatch;
    public Map<String, TextureRegion> textureRegions;

    private PlayerRenderer playerRenderer;
    private Texture atlasTexture;
    private NotesHolderRenderer notesHolderRenderer;
    private MessageHolderRenderer messageHolderRenderer;
    private TextRenderer textRenderer;
    private Music theme;
    private MediaPlayerRenderer mediaRenderer;

    //Menu
    private GameMenuRenderer gameMenuRenderer;
    private PauseMenuRenderer pauseMenuRenderer;
    private MainMenuRenderer mainMenuRenderer;
    private EndGameMenuRenderer endGameMenuRenderer;

    private BitmapFont font;
    private Texture playerCursor;
    private GameInfoRenderer gameInfoRenderer;
    private SeekBarRenderer seekBarRenderer;

    public void SetCamera(float x, float y){
		this.cam.position.set(x, y,0);	
		this.cam.update();
	}
	
	public WorldRenderer(World world) {
		this.world = world;
        CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
        ppuX =  (float)Gdx.graphics.getWidth()  / CAMERA_WIDTH;
        ppuY = (float)Gdx.graphics.getHeight() / CAMERA_HEIGHT;

        CatchTheNotes.getContentManager().setDimensionCoeff((float)Gdx.graphics.getHeight()/768f);
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		SetCamera(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f);
        world.setSize(CAMERA_WIDTH, CAMERA_HEIGHT, WorldController.SOURCE_COUNT);
        world.createWorld();
        spriteBatch = new SpriteBatch();
        textureRegions = new HashMap<String, TextureRegion>();
        loadTextures();
        loadSounds();
	}

    private void loadSounds() {


    }

    private void loadTextures() {

        font = new BitmapFont(Gdx.files.internal("data/PareBold256_spacing.fnt"), false);
        textRenderer = new TextRenderer(font, ppuX, ppuY,  ppuX*(CAMERA_WIDTH/WorldController.SOURCE_COUNT));

        atlasTexture  = new Texture(Gdx.files.internal("images/atlas_glow.png"));
        playerCursor = new Texture(Gdx.files.internal("images/cursor_atlas.png"));
        TextureAtlas endGameAtlas = new TextureAtlas(Gdx.files.internal("images/medalsTexture.atlas"));
        HashMap<Integer, TextureRegion> medalsTextures = new HashMap<Integer, TextureRegion>();
        medalsTextures.put(0, endGameAtlas.findRegion("bronze"));
        medalsTextures.put(1, endGameAtlas.findRegion("silver"));
        medalsTextures.put(2, endGameAtlas.findRegion("gold"));
        medalsTextures.put(3, endGameAtlas.findRegion("platinum"));
        medalsTextures.put(4, endGameAtlas.findRegion("red_star"));

        TextureRegion regions[][] = TextureRegion.split(atlasTexture, atlasTexture.getWidth()/8, atlasTexture.getHeight() / 8);
        TextureRegion cursorRegions[][] = TextureRegion.split(playerCursor, playerCursor.getWidth()/2, playerCursor.getHeight());
        float coeff = ppuX*(CAMERA_WIDTH/WorldController.SOURCE_COUNT);
        playerRenderer = new PlayerRenderer(Arrays.asList(cursorRegions[0]).subList(0, 2), textRenderer , ppuX, ppuY, coeff, CAMERA_HEIGHT);
        messageHolderRenderer = new MessageHolderRenderer(world.getMessageHolder(), ppuX, ppuY, font, coeff);
        gameInfoRenderer = new GameInfoRenderer(world, textRenderer,  ppuX, ppuY, coeff, CAMERA_HEIGHT);
        seekBarRenderer = new SeekBarRenderer(world, ppuX, ppuY, coeff, CAMERA_HEIGHT);

        gameMenuRenderer = new GameMenuRenderer(world.getGameMenu(), textRenderer);
        pauseMenuRenderer = new PauseMenuRenderer(world.getPauseMenu(), textRenderer);
        mainMenuRenderer = new MainMenuRenderer(medalsTextures, world.getMainMenu(), textRenderer, ppuX, ppuY, coeff);
        endGameMenuRenderer = new EndGameMenuRenderer(medalsTextures, world.getEndMenu(), ppuX, ppuY, font, coeff);

//        ArrayList<TextureRegion> notes = new ArrayList<TextureRegion>();
        HashMap<Note.ShapeType, List<TextureRegion>> shapesTextures = new HashMap<Note.ShapeType, List<TextureRegion>>();
        shapesTextures.put(Note.ShapeType.MUSIC, Arrays.asList(regions[0]).subList(0, NotesHolder.NOTE_TYPE_COUNT));
        shapesTextures.put(Note.ShapeType.FLORAL, Arrays.asList(regions[1]).subList(0, NotesHolder.FLORAL_TYPE_COUNT));
        shapesTextures.put(Note.ShapeType.SKY, Arrays.asList(regions[2]).subList(0, NotesHolder.SKY_TYPE_COUNT));
        shapesTextures.put(Note.ShapeType.ABSTRACT, Arrays.asList(regions[3]).subList(0, NotesHolder.ABSTRACT_TYPE_COUNT));

        notesHolderRenderer = new NotesHolderRenderer(shapesTextures, ppuX, ppuY, CAMERA_WIDTH, WorldController.SOURCE_COUNT);

        if(Constants.DEBUG)
        {
            Texture colors = new Texture(Gdx.files.internal("images/colors-borders.png"));
            mediaRenderer = new MediaPlayerRenderer(new TextureRegion(colors), ppuX, ppuY);
        }
    }

    public void render() {
        drawNotes();
        drawMenuController();
        drawPlayer();

      //  mediaRenderer.render(spriteBatch);
	}

    private void drawMenuController() {
        switch (world.getCurrentMenuType())
        {
            case GAME:
                messageHolderRenderer.update(world.getMessageHolder());
                messageHolderRenderer.render(spriteBatch);
                gameMenuRenderer.render(spriteBatch);

                gameInfoRenderer.update(world.getScoreManager());
                gameInfoRenderer.render(spriteBatch);

                seekBarRenderer.render(spriteBatch);
                break;
            case PAUSE:
                pauseMenuRenderer.render(spriteBatch);

                gameInfoRenderer.update(world.getScoreManager());
                gameInfoRenderer.render(spriteBatch);

                seekBarRenderer.render(spriteBatch);
                break;
            case MAIN_MENU:
                mainMenuRenderer.render(spriteBatch);
                break;
            case END_GAME:
                endGameMenuRenderer.render(spriteBatch);
                break;
        }
    }

    private void drawNotes() {
        notesHolderRenderer.update(world.getNotesHolder());
        notesHolderRenderer.render(spriteBatch);
    }

    private void drawPlayer() {
        playerRenderer.update(world.getPlayer());
        playerRenderer.render(spriteBatch);
	}
	
}
