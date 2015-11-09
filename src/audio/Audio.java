package src.audio;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.*;
import be.tarsos.dsp.io.jvm.*;
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
   private AudioPlayer ap;
   private TarsosDSPAudioFormat fmt;

   public Audio(String fpath, int sampleRate) {
      this.f = new File(fpath);

      /* Load Song w/ FFMPEG */
      try {
         dispatch = AudioDispatcherFactory.fromPipe(f.getCanonicalPath(), 44100, 1024, 3);
         fmt = dispatch.getFormat();
      } catch (IOException e) {
         e.printStackTrace();
      }

      /* Create Audio Player */
      try {
         ap = new AudioPlayer(JVMAudioInputStream.toAudioFormat(fmt));
         dispatch.addAudioProcessor(ap);
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   public void play() {
      previousFrame = System.nanoTime() / 1000000.0;
      lastReport = 0;
      songPos = 0;
      new Thread(dispatch).start();
   }

   // call each frame
   public double getPosition() {
      double ptime = System.nanoTime() / 1000000;
      songPos += ptime - previousFrame;
      previousFrame = ptime;

      double minimPos = dispatch.secondsProcessed() * 1000;
      if (Math.abs(minimPos - lastReport) >= 0.1) {
         songPos = (songPos + minimPos) / 2;  // easing
         lastReport = minimPos;
      }

      return songPos;
   }

}
