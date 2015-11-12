package src.test;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.PipedAudioStream;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;

public class Audio {

   AudioPlayer song;
   File f;
   float[] beats;
   private double previousFrame;
   private double lastReport;
   private double songPos;
   private AudioDispatcher dispatch;
   private PipedAudioStream pipe;
   private AudioPlayer ap;
   private TarsosDSPAudioFormat fmt;

   public Audio(String fpath, int sampleRate) {
      this.f = new File(fpath);

      /* Load Song w/ FFMPEG */
      try {
         dispatch = AudioDispatcherFactory.fromPipe(f.getCanonicalPath(), 44100, 1024, 0);
         fmt = dispatch.getFormat();
      } catch (IOException e) {
         e.printStackTrace();
      }

      /* Create Audio Player */
      try {
         // TODO: patch using this http://stackoverflow.com/a/20962169j
         ap = new AudioPlayer(JVMAudioInputStream.toAudioFormat(fmt));
         dispatch.addAudioProcessor(ap);
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   /* Create Empty Audio */
   public Audio(int ms) {
   }

   public void play() {
      previousFrame = System.nanoTime() / 1000000.0;
      lastReport = 0;
      songPos = 0;
      if (dispatch != null) // avoid starting music in case of dummy audio
         new Thread(dispatch).start();
   }

   // call each frame
   public double getPosition() {
      double ptime = System.nanoTime() / 1000000;
      songPos += ptime - previousFrame;
      previousFrame = ptime;

      if (dispatch != null ) {
         double minimPos = dispatch.secondsProcessed() * 1000;
         if (Math.abs(minimPos - lastReport) >= 0.1) {
            songPos = (songPos + minimPos) / 2;  // easing
            lastReport = minimPos;
         }
      }

      return songPos;
   }

    public static void main(String[] args) {
        Audio audio = new Audio("music/mg.ogg", 1024);
        audio.play();
    }

}