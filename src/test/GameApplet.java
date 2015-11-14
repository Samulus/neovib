package src.test;

import processing.core.PApplet;
import src.audio.Audio;
import src.audio.BeatKonducta;
import src.audio.Detector;
import src.clock.Clock;
import src.event.EQ;
import src.event.VibEvent;
import src.primitives.AbstractShape;
import src.primitives.Player;
import src.primitives.Track;
import src.scene.Browser;
import src.scene.Scene;

import java.io.File;
import java.util.LinkedList;

public class GameApplet extends PApplet {

   /* Audio */
   public Audio audio;
   public LinkedList<Double> beats;
   public BeatKonducta konducta;

   /* Graphics */
   Player player = new Player();
   Track track = new Track();

   public GameApplet() {
      player = new Player();
      track = new Track();
   }

   public void settings() {
      size(1280, 720, P2D);
   }


   public void setup() {

      String fpath = "audio/imgod.mp3";

      audio = new Audio(fpath, 1024);
      beats = Detector.load(new File(fpath));
      konducta = new BeatKonducta(audio, beats);
      audio.play();
   }

   public void render() {
      background(0);

      /* Onscreen Elements */
      player.render();
      track.render(konducta.getList());

      /* Debug */
      pushMatrix();
      translate(width / 1.5f, height / 16f);
      if (!konducta.impeding.isEmpty()) {
         text(audio.getPosition() + "", 0, 0);
         text(konducta.impeding.getFirst() + "", 0, 64);
      }

      popMatrix();


      /* Render Obstacles */
      for (AbstractShape s : konducta.getList()) {
         if (s.getDistance() <= width / 7) {
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

   public void input(char c) {
   }

   public void keyPressed() {
      System.out.println(beats.getFirst() - audio.getPosition() / 1000);
   }

   public static void main(String[] args) {
      PApplet.main(GameApplet.class.getName());
   }

}
