package com.northerneyes.CatchTheNotes.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.analysis.KissFFT;
import com.badlogic.gdx.audio.io.Mpg123Decoder;
import com.badlogic.gdx.files.FileHandle;
import sun.rmi.runtime.Log;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 10.06.13
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
public class MediaPlayer {

    private static boolean isVisualizationEnabled = false;
    private static int FREQ_COUNT = 512;
    private static float volume = 1f;
   private static int NB_BARS = 31;
    private static IMediaPlayerListener listener;
 //   private static int position;


    public enum MediaPlayerState {
        PAUSED,
        PLAYING,
        STOPPED
    }
    private static AudioDevice device;

    private static short[] samples = new short[FREQ_COUNT];
    private static KissFFT fft;
    private static float[] spectrum = new float[FREQ_COUNT];
    private static float[] normalSpectrum = new float[FREQ_COUNT];
   // private static float[] maxValues = new float[512];
  //  private static float[] topValues = new float[512];

    private static MediaPlayerState state = MediaPlayerState.STOPPED;
    private static Mpg123Decoder decoder;

    public static void play(String fileName, boolean external)
    {
        FileHandle externalFile = Gdx.files.external("tmp/test.mp3");
        Gdx.app.log("Game external", String.valueOf(external));
        if(external)
            Gdx.files.absolute(fileName).copyTo(externalFile);
        else
            Gdx.files.internal(fileName).copyTo(externalFile);
        decoder = new Mpg123Decoder(externalFile);

        device = Gdx.audio.newAudioDevice(decoder.getRate(),
                decoder.getChannels() == 1 ? true : false);

        device.setVolume(volume);
        play();
    }

    public static void pause(){
        if(state == MediaPlayerState.PLAYING)
            state = MediaPlayerState.PAUSED;
    }


    public static float getPosition(float position)
    {
         return position/decoder.getLength();
    }

    private static void play()
    {
        if(isVisualizationEnabled)
            fft = new KissFFT(FREQ_COUNT);
        // start a thread for playback
        Thread playbackThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int readSamples = 0;

                while (state == MediaPlayerState.PLAYING
                        && (readSamples = decoder.readSamples(samples, 0,
                        samples.length)) > 0) {
                    if(isVisualizationEnabled)
                        fft.spectrum(samples, spectrum);


                    device.writeSamples(samples, 0, readSamples);
                }

                if(state == MediaPlayerState.PLAYING)
                {
                    if(listener != null)
                        listener.onStop();
                    for(int i = 0; i < spectrum.length; i++)
                    {
                        spectrum[i] = 0;
                    }
                    decoder.dispose();
                    device.dispose();
                }
                if(state == MediaPlayerState.STOPPED)
                {
                    decoder.dispose();
                    device.dispose();
                }
            }
        });
        playbackThread.setDaemon(true);
        playbackThread.start();
        state = MediaPlayerState.PLAYING;
    }

    public static void setListener(IMediaPlayerListener _listener)
    {
        listener = _listener;
    }
    public static void resume()
    {
        if(state == MediaPlayerState.PAUSED)
        {
            play();
        }

    }



    public static void stop()
    {
        if(state == MediaPlayerState.PLAYING || state == MediaPlayerState.PAUSED)
        {
            state = MediaPlayerState.STOPPED;
        }
    }

    public static void dispose() {
      stop();
      Gdx.files.external("tmp/test.mp3").delete();
    }


    public static boolean isRepeating()
    {
        return false;
    }

    public static boolean isVisualizationEnabled()
    {
        return isVisualizationEnabled;
    }

    public static void setVisualizationEnabled()
    {
        if(state == MediaPlayerState.PLAYING || state == MediaPlayerState.PAUSED)
            fft = new KissFFT(FREQ_COUNT);
        isVisualizationEnabled = true;
    }

    public static MediaPlayerState state()
    {
        return state;
    }

    public static void setVolume(float _volume)
    {
        volume = _volume;
        if(state == MediaPlayerState.PLAYING || state == MediaPlayerState.PAUSED)
            device.setVolume(volume);
    }

    public static boolean isMuted()
    {
        return volume == 0f;
    }

    public static void setMuted(boolean muted)
    {
        if(state == MediaPlayerState.PLAYING || state == MediaPlayerState.PAUSED)
        {
            if(muted)
                device.setVolume(0f);
            else
                device.setVolume(volume);
        }
    }

    public static float getVolume()
    {
        return volume;
    }


    //processing
    public static void  GetVisualizationData(VisualizationData visualizationData)
    {
       normalizateSpectrum();
       float coef = getPower();
       NB_BARS = visualizationData.Frequences.length;



                    for(int i = 0; i < NB_BARS; i++)
            {

                visualizationData.Frequences[i] =  normalSpectrum[i];
            }

    }

    private static float getPower() {
        float maxPower = spectrum.length *1f;
        float sum = 0;
        for (float aSpectrum : spectrum) {
            sum += aSpectrum;
        }

        return sum/maxPower;
    }

    private static void normalizateSpectrum() {

        for (int i = 0; i < normalSpectrum.length; i++) {
            normalSpectrum[i] = 0;
        }

        normalizate(spectrum, normalSpectrum);
    }

    private static void normalizate(float[] origin, float[] normalizate)
    {
        float maxValue = max(origin);

        for (int i = 0; i <normalizate.length; i++) {
            normalizate[i] = origin[i] / maxValue;
        }
    }

    public static float max(float []a)
    {
        float maxVal=a[0];
        for (float anA : a) {
            if (anA > maxVal) {
                maxVal = anA;
            }
        }
        return maxVal;
    }

    private static float avg(int pos, int nb) {
        float sum = 0;
        for (int i = 0; i < nb; i++) {
            sum += spectrum[pos + i];
        }

        return sum / nb;
    }
}
