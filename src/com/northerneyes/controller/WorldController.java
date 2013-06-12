package com.northerneyes.controller;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.analysis.KissFFT;
import com.badlogic.gdx.audio.io.Mpg123Decoder;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.northerneyes.audio.MediaPlayer;
import com.northerneyes.audio.VisualizationData;
import com.northerneyes.model.Constants;
import com.northerneyes.model.NotesHolder;
import com.northerneyes.model.Player;
import com.northerneyes.model.World;

import java.util.Random;


public class WorldController {

    public static final int FREQ_LENGTH = 32;
    private final VisualizationData data;
    //  private final Music theme;
   // private final AudioDevice device;
    public Player player;
    public NotesHolder notesHolder;
    public Random random=  new Random();

	public WorldController(World world) {
		this.player = world.getPlayer();
     //   theme =  Gdx.audio.newMusic(Gdx.files.internal("audio/Leaves_in_the_Wind.mp3"));
      //  theme.setVolume(1f);
        //theme.play();
        this.notesHolder = world.getNotesHolder();

        MediaPlayer.play("audio/Leaves_in_the_Wind.mp3");
        MediaPlayer.setVisualizationEnabled();
        data = new VisualizationData(FREQ_LENGTH);
	}



    private int filter = 0;
	public void update(float delta) {
		player.update(delta);
        notesHolder.update(delta);

      //  if(filter%5 == 0)
       // {
            MediaPlayer.GetVisualizationData(data);
            for (int i = 0; i < data.Frequences.length; i++) {
                   if (data.Frequences[i] > Constants.BEAT_REACTION  &&
                           notesHolder.Accumulator.get(i) > Constants.ACCOMULATOR_REACTION
                            && notesHolder.particles.size() < 50) //равномерно
                   {
                         notesHolder.beat(i, data.Frequences[i]);
                   }

            }
           // filter = 0;
       // }
       // filter++;
      //  for (int a = 0; a < NB_BARS; a++)
      //  {
          //  scale(avg(histoX, nb))
            //int histoX = a;
//            if (a < NB_BARS / 2) {
//                histoX = NB_BARS / 2 - a;
//            } else {
//                histoX = a - NB_BARS / 2;
//            }

           // int nb = samples.length / NB_BARS;

          // float freq = scale(avg(a, nb));
          //  Log.v("Game Freq", String.valueOf(avg(x, nb)));
          //   if (freq > Constants.BEAT_REACTION  && notesHolder.Accumulator.get(a) > Constants.ACCOMULATOR_REACTION)
         //   {
           //         notesHolder.beat(a/5.12f);
         //   }
       // }

    }

//    private float scale(float x) {
//        return x / 256 * HEIGHT * 2.0f;
//    }

}
