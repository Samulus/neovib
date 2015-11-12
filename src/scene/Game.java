package src.scene;
;
import src.audio.*;
import src.event.*;
import src.primitives.*;
import src.debug.BeatOverlay;

import java.io.File;
import java.util.LinkedList;

public class Game extends AbstractScene {

   /* Graphics */
   Player player = new Player();
   Track track = new Track();

   /* Audio */
   private Audio audio;
   private BeatKonducta konducta;

   public Game() {
      player = new Player();
      track = new Track();
   }

   public void setup() {

      String fpath = "";

      /* Load Browser Song */
      try {
         fpath = Browser.fsong.getCanonicalPath();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Game.java: unable to open file");
      }

      audio = new Audio(fpath, 1024);
      LinkedList<Double> beats = Detector.load(new File(fpath));

      // avoid songs with no / few beats */
      if (beats.size() < 5) {
         EQ.enqueue(VibEvent.SCENE_BROWSER);
         return;
      }

      konducta = new BeatKonducta(audio, beats);
      audio.play();
   }

   public void render() {
      Scene.p.background(0);

      /* Onscreen Elements */
      player.render();
      track.render();

      /* Render Obstacles */
      for (AbstractShape s : konducta.getList()) {
         if (s.getDistance() <= Scene.p.width / 7) {
            s.setVibrate(40);
         }
         s.render();
      }
   }

   public void logic() {
      konducta.nextReady();
      konducta.deleteOld();
      for (AbstractShape s : konducta.getList()) {
         player.logic(s.getDistance());
         s.advance();
      }
   }

   public void input(VibEvent event) {
      player.input(event);
   }
}
