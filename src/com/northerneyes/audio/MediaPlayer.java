package com.northerneyes.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.analysis.KissFFT;
import com.badlogic.gdx.audio.io.Mpg123Decoder;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created with IntelliJ IDEA.
 * User: George
 * Date: 10.06.13
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
public class MediaPlayer {

    private static boolean isVisualizationEnabled = false;
    private static float volume = 1f;

    public enum MediaPlayerState {
        PAUSED,
        PLAYING,
        STOPPED
    }
    private static AudioDevice device;

    private static short[] samples = new short[2048];
    private static KissFFT fft;
    private static float[] spectrum = new float[2048];
    private static float[] maxValues = new float[2048];
    private static float[] topValues = new float[2048];

    private static MediaPlayerState state = MediaPlayerState.STOPPED;
    private static Mpg123Decoder decoder;

    public static void play(String fileName)
    {
        FileHandle externalFile = Gdx.files.external("tmp/test.mp3");

        //TODO: Check internal or external  file is
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

    private static void play()
    {
        if(isVisualizationEnabled)
            fft = new KissFFT(2048);
        // start a thread for playback
        Thread playbackThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int readSamples = 0;

                while (state == MediaPlayerState.PLAYING
                        && (readSamples = decoder.readSamples(samples, 0,
                        samples.length)) > 0) {
// get audio spectrum
                    if(isVisualizationEnabled)
                        fft.spectrum(samples, spectrum);

// write the samples to the AudioDevice
                    device.writeSamples(samples, 0, readSamples);
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

    public static void resume()
    {
        if(state == MediaPlayerState.PAUSED)
        {
            play();
        }

    }

    public static void GetVisualizationData(VisualizationData visualizationData)
    {

    }

    public static void Stop()
    {
        if(state == MediaPlayerState.PLAYING || state == MediaPlayerState.PAUSED)
        {
            state = MediaPlayerState.STOPPED;
        }
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
            fft = new KissFFT(2048);
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
    private float avg(int pos, int nb) {
        float sum = 0;
        for (int i = 0; i < nb; i++) {
            sum += spectrum[pos + i];
        }

        return sum / nb;
    }

    private float scale(float x) {
        return x / 64;
    }
}
