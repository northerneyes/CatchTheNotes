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
import com.northerneyes.model.Constants;
import com.northerneyes.model.NotesHolder;
import com.northerneyes.model.Player;
import com.northerneyes.model.World;

import java.util.Random;


public class WorldController {

   // int NB_BARS = 128;
  //  private final Music theme;
   // private final AudioDevice device;
    public Player player;
    public NotesHolder notesHolder;
    public Random random=  new Random();

  //  short[] samples = new short[2048];
   // KissFFT fft;
   // float[] spectrum = new float[2048];
   // float[] maxValues = new float[2048];
   // float[] topValues = new float[2048];

 //   boolean playing = false;
  //  Mpg123Decoder decoder;
	public WorldController(World world) {
		this.player = world.getPlayer();
     //   theme =  Gdx.audio.newMusic(Gdx.files.internal("audio/Leaves_in_the_Wind.mp3"));
      //  theme.setVolume(1f);
        //theme.play();
        this.notesHolder = world.getNotesHolder();

        MediaPlayer.play("audio/Leaves_in_the_Wind.mp3");
//        FileHandle externalFile = Gdx.files.external("tmp/test.mp3");
//        Gdx.files.internal("audio/Leaves_in_the_Wind.mp3").copyTo(externalFile);
//        decoder = new Mpg123Decoder(externalFile);
//
//        device = Gdx.audio.newAudioDevice(decoder.getRate(),
//                decoder.getChannels() == 1 ? true : false);
//
//        // fast fourier transform
//        fft = new KissFFT(2048);
//        // start a thread for playback
//        Thread playbackThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int readSamples = 0;
//
//// read until we reach the end of the file
//                while (playing
//                        && (readSamples = decoder.readSamples(samples, 0,
//                        samples.length)) > 0) {
//// get audio spectrum
//                    fft.spectrum(samples, spectrum);
//// write the samples to the AudioDevice
//                    device.writeSamples(samples, 0, readSamples);
//                }
//            }
//        });
//        playbackThread.setDaemon(true);
//        playbackThread.start();
//        playing = true;
	}




	public void update(float delta) {
		player.update(delta);
        notesHolder.update(delta);

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
