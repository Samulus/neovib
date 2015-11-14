package src.scene;

import src.audio.Audio;
import src.audio.BeatKonducta;
import src.audio.Detector;
import src.clock.Clock;
import src.event.EQ;
import src.event.VibEvent;
import src.primitives.AbstractShape;
import src.primitives.Player;
import src.primitives.Track;

import java.io.File;
import java.util.LinkedList;

;

public class Game extends AbstractScene {

   /* Audio */
   public Audio audio;
   public LinkedList<Double> beats;
   public BeatKonducta konducta;
   /* Graphics */
   Player player = new Player();
   Track track = new Track();
   /* Input Clock */
   Clock inclock;

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
      beats = Detector.load(new File(fpath));

      // avoid songs with no / few beats */
      if (beats.size() < 5) {
         EQ.enqueue(VibEvent.SCENE_BROWSER);
         return;
      }

      konducta = new BeatKonducta(audio, beats);
      audio.play();
      inclock = new Clock();
   }

   public void render() {
      Scene.p.background(0);

      /* Onscreen Elements */
      player.render();
      track.render(konducta.getList());

      /* Debug */
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 1.5f, Scene.p.height / 16f);
      if (!konducta.impeding.isEmpty()) {
         Scene.p.text(audio.getPosition() + "", 0, 0);
         Scene.p.text(konducta.impeding.getFirst() + "", 0, 64);
      }

      Scene.p.popMatrix();


      /* Render Obstacles */
      for (AbstractShape s : konducta.getList()) {
         if (s.getDistance() <= Scene.p.width / 7) {
            s.setState("hit");
            s.setVibrate(30);
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
      System.out.println(beats.getFirst() / 1000 - audio.getPosition() / 1000);
   }
}
