package src.scene;

import processing.core.PApplet;
import src.audio.Audio;
import src.audio.Beat;
import src.audio.BeatKonducta;
import src.audio.Detector;
import src.event.VibEvent;
import src.primitives.AbstractShape;
import src.primitives.Line;
import src.primitives.Player;

import java.io.File;

public class Game extends AbstractScene {

   String fpath;
   Player p = new Player();
   Line track = new Line();
   private Audio audio;
   private Beat beat;
   private BeatKonducta konducta;

   public void setup() {
      Scene.p.noFill();
      Scene.p.stroke(255);

      try {
         fpath = Browser.fsong.getCanonicalPath();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Game.java: unable to open file");
      }

      audio = new Audio(fpath, 1024);
      beat = new Beat(Detector.load(new File(fpath)));
      konducta = new BeatKonducta(audio, beat);
      audio.play();
   }

   public void render() {
      Scene.p.background(0);

      for (AbstractShape s : konducta.getList()) {
         s.render();
      }

      //p.render();
      track.render();
   }

   public void logic() {

      // TODO: remove old elements
      //audio.getPosition(); // each frame
      konducta.nextReady();

        /* vibrate, stop pulsating*/
      for (AbstractShape s : konducta.getList()) {
         if (s.getDistance() <= Scene.p.width / 7f) s.setVibrate(60);
         s.advance();
      }

      track.setVibrate(PApplet.constrain(track.getVibrate() * 0.95f, 0, 32));

   }

   public void input(VibEvent event) {
   }
}
