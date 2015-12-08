/*
   Audio.java
   ----------
   The Audio module is responsible for interfacing with TarsosDSP
   and providing an API for us to play a song, stop a song, and getting
   the current Artist name in the Metadata in the provided file.
 */

package audio;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.PipeDecoder;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import scene.Sound;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;

public class Audio {

   private double previousFrame;
   private double lastReport;
   private double lastDispatchTime = -1;
   private double songPos;
   private Thread playThread;
   private AudioDispatcher dispatch;
   private TarsosDSPAudioFormat fmt;
   private String artist; // filename is stored in event Artist cannot be parsed from FFMPEG.
   private String title;


   public Audio(String fpath) {
      File file = new File(fpath);
      AudioPlayer ap;

      /* Load Song w/ FFMPEG */
      try {
         dispatch = AudioDispatcherFactory.fromPipe(file.getCanonicalPath(), 44100, 1024, 0);
         fmt = dispatch.getFormat();
      } catch (Exception e) {
         e.printStackTrace();
      }

      /* Create Audio Player */
      try {
         System.out.println(Sound.getSelectedMixer());
         ap = new AudioPlayer(JVMAudioInputStream.toAudioFormat(fmt), 1024); // remove 1024 when on chromebook
         dispatch.addAudioProcessor(ap);
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }

      parseArtist(fpath);
      if (artist.equals("")) artist = file.getName();
   }

   public static void main(String[] args) {

      Audio a = new Audio("");
      a.play();
      while (!a.finished()) ;
      a.stop();

      System.out.println("Done");

   }

   public String getTitle() {
      return title;
   }

   public String getArtist() {
      return this.artist;
   }

   private String subArtist(String haystack, String field) {
      String original = haystack;
      haystack = haystack.toLowerCase();
      int apos = haystack.indexOf(field);
      String sub = haystack.substring(apos);
      int colon = sub.indexOf(":");
      int end = sub.indexOf("\n");
      return original.substring(apos + colon + 1, apos + end).trim();
   }

   private void parseArtist(String fpath) {
      PipeDecoder pipe = new PipeDecoder();
      try {
         String ffout = pipe.ffinfo(fpath);
         artist = subArtist(ffout, "artist");
         title = subArtist(ffout, "title");
      } catch (Exception e) {
         artist = "";
         title = "";
      }
   }

   public void play() {
      previousFrame = System.nanoTime() / 1000000.0;
      lastReport = 0;
      songPos = 0;
      if (dispatch != null) {
         playThread = new Thread(dispatch);
         playThread.start();
      }
   }

   // avoids desync by using audio timer + personal timer and returning
   // the average of the two when either begins to drift
   public double getPosition() {
      double ptime = System.nanoTime() / 1000000;
      songPos += ptime - previousFrame;
      previousFrame = ptime;

      if (dispatch != null) {
         double minimPos = dispatch.secondsProcessed() * 1000;
         if (Math.abs(minimPos - lastReport) >= 0.1) {
            songPos = (songPos + minimPos) / 2;
            lastReport = minimPos;
         }
      }


      return songPos;
   }

   public boolean finished() {
      return (this.getPosition() - dispatch.secondsProcessed() * 1000) > 2000; // TODO: magic number
   }

   public void props() {
   }

   public void stop() {
      dispatch.stop();
      playThread.interrupt();
   }

}
