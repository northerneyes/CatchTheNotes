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
    private static int FREQ_COUNT = 512;
    private static float volume = 1f;
   private static int NB_BARS = 31;


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
            fft = new KissFFT(FREQ_COUNT);
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



    public static void stop()
    {
        if(state == MediaPlayerState.PLAYING || state == MediaPlayerState.PAUSED)
        {
            state = MediaPlayerState.STOPPED;
        }
    }

    public static void Dispose() {
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
    public static void GetVisualizationData(VisualizationData visualizationData)
    {
       normalizateSpectrum();
       float coef = getPower();
       NB_BARS = visualizationData.Frequences.length;

//        //freq transform
//       for (int i = 0; i < NB_BARS; i++) {
//            int histoX = 0;
//            if (i < NB_BARS / 2) {
//                histoX = NB_BARS / 2 - i;
//            } else {
//                histoX = i - NB_BARS / 2;
//            }
//
//            int nb = (samples.length / NB_BARS) / 2;
//           // scale(avg(histoX, nb))
//
//            visualizationData.Frequences[i] = avg(histoX, nb);
//        }
//            for(int i = 0; i < NB_BARS; i++)
//            {
//                int nb = (samples.length / NB_BARS) / 2;
//
//                visualizationData.Frequences[i] =  avg(i, nb);
//            }

                    for(int i = 0; i < NB_BARS; i++)
            {
               // int nb = (samples.length / NB_BARS) / 2;

                visualizationData.Frequences[i] =  normalSpectrum[i];
            }

        //amplitude normal
      //  normalizate(visualizationData.Frequences, visualizationData.Frequences);

//        for(int i = 0; i < NB_BARS; i++)
//        {
//            visualizationData.Frequences[i] *= 2;
//            visualizationData.Frequences[i] -= 1;
//        }
        //power normal
//        for (int i = 0; i < visualizationData.Frequences.length; i++) {
//            visualizationData.Frequences[i] *= coef;
//        }
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
