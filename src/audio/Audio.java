package src.audio;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.PipeDecoder;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;

public class Audio {

    AudioPlayer song;
    File f;
    float[] beats;
    private double previousFrame;
    private double lastReport;
    private double songPos;
    private Thread playThread;
    private AudioDispatcher dispatch;
    private AudioPlayer ap;
    private TarsosDSPAudioFormat fmt;
    private String artist;

    public Audio(String fpath) {
        this.f = new File(fpath);

      /* Load Song w/ FFMPEG */
        try {
            //dispatch = AudioDispatcherFactory.fromFile(f.getCanonicalFile(), 1024,0);
            dispatch = AudioDispatcherFactory.fromPipe(f.getCanonicalPath(), 44100, 1024, 0);
            fmt = dispatch.getFormat();
        } catch (Exception e) {
            e.printStackTrace();
        }

      /* Create Audio Player */
        try {
            ap = new AudioPlayer(JVMAudioInputStream.toAudioFormat(fmt)); // remove 1024 when on chromebook
            dispatch.addAudioProcessor(ap);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fpath = "audio/xchange.mp3";
        Audio a = new Audio(fpath);
        System.out.println(parseArtist(fpath));
        a.play();
    }

    public static String parseArtist(String fpath) {
        String artist = "";
        PipeDecoder pipe = new PipeDecoder();
        try {
            String ffout = pipe.ffinfo(fpath);
            String original = ffout;
            ffout = ffout.toLowerCase();
            int apos = ffout.indexOf("artist");
            String sub = ffout.substring(apos);
            int colon = sub.indexOf(":");
            int end = sub.indexOf("\n");
            artist = original.substring(apos + colon + 1, apos + end).trim();
        } catch (Exception e) {
            artist = "";
        }

        return artist;
    }

    public void play() {
        previousFrame = System.nanoTime() / 1000000.0;
        lastReport = 0;
        songPos = 0;
        if (dispatch != null) {
            playThread = new Thread(dispatch);
            playThread.start();
            //new Thread(dispatch).start();
        }
    }

    // call each frame
    public double getPosition() {
        double ptime = System.nanoTime() / 1000000;
        songPos += ptime - previousFrame;
        previousFrame = ptime;

        if (dispatch != null) {
            double minimPos = dispatch.secondsProcessed() * 1000;
            if (Math.abs(minimPos - lastReport) >= 0.1) {
                songPos = (songPos + minimPos) / 2;  // easing
                lastReport = minimPos;
            }
        }

        return songPos;
    }

    public void stop() {
        dispatch.stop();
        playThread.interrupt();
    }

    public String getArtist() {
        return "Stub";
    }

}
