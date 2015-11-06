package src.audio;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import src.scene.Scene;

public class Audio {

   Minim minim;
   AudioPlayer song;
   float[] beats;
   private int previousFrame;
   private int lastReport;
   private int songPos;

   public Audio(String fpath, int sampleRate) {
      this.minim = new Minim(Scene.p);
      this.song = minim.loadFile(fpath, sampleRate);
   }

   public void play() {
      Scene.p.millis();
      previousFrame = (int) (System.nanoTime() / 1000000);
      lastReport = 0;
      songPos = 0;
      this.song.play();
   }

   // call every frame!
   public int getPosition() {
      int ptime = (int) (System.nanoTime() / 1000000);
      songPos += ptime - previousFrame;
      previousFrame = ptime;

      int minimPos = song.position();
      if (minimPos != lastReport) {
         songPos = (songPos + minimPos) / 2;  // easing
         lastReport = minimPos;
         System.out.println("Desync");
      }

      System.out.println(songPos);
      return songPos;
   }

}
