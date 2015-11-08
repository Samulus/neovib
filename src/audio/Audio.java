package src.audio;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.*;
import be.tarsos.dsp.io.jvm.*;
import src.scene.Scene;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;

public class Audio {

   AudioPlayer song;
   File f;
   float[] beats;
   private int previousFrame;
   private int lastReport;
   private int songPos;
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
      previousFrame = (int) (System.nanoTime() / 1000000);
      lastReport = 0;
      songPos = 0;
      new Thread(dispatch).start();
   }

   // call every frame!
   public int getPosition() {
      int ptime = (int) (System.nanoTime() / 1000000);
      songPos += ptime - previousFrame;
      previousFrame = ptime;

      int minimPos = (int) dispatch.secondsProcessed() * 1000;
      if (minimPos != lastReport) {
         songPos = (songPos + minimPos) / 2;  // easing
         lastReport = minimPos;
         System.out.println("Desync");
      }

      System.out.println(songPos);

      return songPos;
   }

}
