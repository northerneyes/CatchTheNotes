package com.northerneyes.view;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.northerneyes.controller.WorldController;
import com.northerneyes.model.NotesHolder;
import com.northerneyes.model.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    private TextRenderer textRenderer;
    private Music theme;
    private MediaPlayerRenderer mediaRenderer;

    private TweenManager tweenManager = new TweenManager();
    //Menu
    private GameMenuRenderer gameMenuRenderer;
    private PauseMenuRenderer pauseMenuRenderer;
    private MainMenuRenderer mainMenuRenderer;
    private BitmapFont font;

    public void SetCamera(float x, float y){
		this.cam.position.set(x, y,0);	
		this.cam.update();
	}
	
	public WorldRenderer(World world) {
		this.world = world;
        CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
        ppuX =  (float)Gdx.graphics.getWidth()  / CAMERA_WIDTH;
        ppuY = (float)Gdx.graphics.getHeight() / CAMERA_HEIGHT;
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
        textRenderer = new TextRenderer(font, ppuX, ppuY, CAMERA_WIDTH);

        atlasTexture  = new Texture(Gdx.files.internal("images/atlas_glow.png"));


        TextureRegion regions[][] = TextureRegion.split(atlasTexture, atlasTexture.getWidth()/8, atlasTexture.getHeight() / 8);

        playerRenderer = new PlayerRenderer(regions[4][2], textRenderer , ppuX, ppuY, CAMERA_WIDTH, CAMERA_HEIGHT);

        gameMenuRenderer = new GameMenuRenderer(world.getGameMenu(), textRenderer);
        pauseMenuRenderer = new PauseMenuRenderer(world.getPauseMenu(), textRenderer);
        mainMenuRenderer = new MainMenuRenderer(world.getMainMenu(), textRenderer, ppuX, ppuY, CAMERA_WIDTH);
        ArrayList<TextureRegion> notes = new ArrayList<TextureRegion>();
        notes.addAll(Arrays.asList(regions[0]).subList(0, NotesHolder.NOTE_TYPE_COUNT));

        notesHolderRenderer = new NotesHolderRenderer(notes, ppuX, ppuY, CAMERA_WIDTH, WorldController.SOURCE_COUNT);

        if(WorldController.DEBUG)
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
                gameMenuRenderer.render(spriteBatch);
                break;
            case PAUSE:
                pauseMenuRenderer.render(spriteBatch);
                break;
            case MAIN_MENU:
                mainMenuRenderer.render(spriteBatch);
                break;
        }
    }

    private void drawNotes() {
      //  renderer.setProjectionMatrix(cam.combined);

        notesHolderRenderer.update(world.getNotesHolder());
        notesHolderRenderer.render(spriteBatch);
    }

    private void drawPlayer() {
		//renderer.setProjectionMatrix(cam.combined);

        playerRenderer.update(world.getPlayer());
        playerRenderer.render(spriteBatch);
	}
	
}
