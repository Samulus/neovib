/*
   Game.java
   ----------

   The game module uses three ArrayDequees

   The Game module is where 90% of gameplay happens. The way the game works is
   that it provides an audio file to the Detector module which (will) returns an
   ArrayDeque of beats. Then we load the audio, then in rea\

 */

package scene;

import processing.core.PApplet;
import audio.Audio;
import audio.BeatKonducta;
import audio.Detector;
import event.EQ;
import event.VibEvent;
import input.Input;
import primitives.AbstractShape;
import primitives.Player;
import primitives.Track;
import ui.Score;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

public class Game extends AbstractScene {

   /* Audio */
   public Audio audio;
   public LinkedList<Double> beats;
   public BeatKonducta konducta;
   /* Graphics */
   Player player = new Player();
   Track track = new Track();
   LinkedList<AbstractShape> gutter;
   /* Score */
   Score score;
   private double lastBeat;
   private File song;
   private String artist;
   private String title;

   public Game() {
      player = new Player();
      track = new Track();
      gutter = new LinkedList<>();
      score = new Score();
   }

   public void pass(Object songFile) {
      this.song = (File) songFile;
   }

   public void setup() {

      String fpath = "";
      score.reset();

      /* Load Browser Song */
      try {
         fpath = this.song.getCanonicalPath();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Game.java: unable to open file " + this.song.getPath());
      }

      audio = new Audio(fpath);
      beats = Detector.load(new File(fpath));
      artist = audio.getArtist();
      title = audio.getTitle();

      if (beats == null || audio == null) {
         System.err.println("Game.java: could not create audio or beats objects");
         throw new RuntimeException();
      }

      /* bail if no beats detected */
      if (beats.size() < 5) {
         EQ.enqueue(VibEvent.SCENE_BROWSER);
         return;
      }

      konducta = new BeatKonducta(audio, beats);
      audio.play();
   }

   public void render() {
      Scene.p.background(255);
      Scene.p.stroke(0);
      Scene.p.strokeWeight(3);

      score.render();

      /* render track + player line */
      player.render();
      track.render(konducta.getList());

      /* render shapes in gutter */
      for (AbstractShape s : gutter) {
         s.render();
      }

      /* render shapes in the konducta queue */
      for (AbstractShape s : konducta.getList()) {
         s.render();
      }

      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 1.5f, Scene.p.height / 16f);
      Scene.p.fill(0);

      /*
      if (!konducta.impeding.isEmpty()) {
         double pos = audio.getPosition() / 1000;
         double btime = konducta.impeding.peek();
         double delta = btime - pos;

         Scene.p.text("Beats @:" + btime, 0, 0);
         Scene.p.text("Last Beat @:" + (lastBeat), 0, 64);
         Scene.p.text("Song @:" + pos, 0, 128);
         Scene.p.text("Delta @:" + delta, 0, 192);
      }
      */

      Scene.p.fill(255);

      /* Render Artist */
      Scene.p.popMatrix();

      Scene.p.pushMatrix();
      Scene.p.fill(0);
      Scene.p.textSize(64);
      Scene.p.translate(Scene.p.width / 16f, Scene.p.height / 1.3f);
      Scene.p.text(artist, 0, 0);
      Scene.p.textSize(32);
      Scene.p.text(title, 0, 23);
      Scene.p.fill(255);
      Scene.p.textSize(32);
      Scene.p.popMatrix();

   }

   public void logic() {
      input(null);

      if (audio.finished()) {
         audio.stop();

         Scene.pass(VibEvent.SCENE_PAUSE, artist);
         Scene.focus(VibEvent.SCENE_PAUSE);
      }

      /* let the konducta delete old beats and queue up new ones */
      konducta.deleteOld();
      konducta.nextReady();

      /* vibrate the track appropriately */
      track.setVibrate(PApplet.constrain(track.getVibrate() * 0.95f, 1, 20));

      // remove shapes that are now offscreen
      Iterator<AbstractShape> iter = gutter.iterator();
      while (iter.hasNext()) {
         AbstractShape s = iter.next();
         if (s.getDistance() <= -60) iter.remove();
      }

      // move each shpae in the incoming queue
      for (AbstractShape s : konducta.getList()) {
         s.advance();
      }

      // move each shape in the gutter
      for (AbstractShape s : gutter) {
         s.advance();
         s.setVibrate(PApplet.constrain(s.getVibrate() * 0.95f, 1, 50));
      }

      // switch from the incoming to gutter queues
      if (!konducta.getList().isEmpty()) {
         LinkedList<AbstractShape> q = konducta.getList();
         AbstractShape s = q.peekFirst();
         if (s.getDistance() <= Scene.p.width / 7) {
            gutter.add(s);
            if (!s.getState().equals("hit")) {
               score.miss();
               s.setState("miss");
               s.setVibrate(100);
            } else score.increase();
            q.pollFirst();
            track.setVibrate(25);
            lastBeat = konducta.impeding.poll();
         }
      }


   }

   public void input(VibEvent event) {

      if (event == VibEvent.INPUT_PREVIOUS) {
         audio.stop();
         Scene.pass(VibEvent.SCENE_PAUSE, artist);
         Scene.focus(VibEvent.SCENE_PAUSE);
      }

      if (konducta.impeding.isEmpty()) return;
      LinkedList<AbstractShape> q = konducta.getList();
      if (q == null || q.isEmpty()) return;

      double pos = audio.getPosition() / 1000;
      double btime = konducta.impeding.peek();
      double delta = btime - pos;

      VibEvent buttons[] = {VibEvent.INPUT_DODGE_SQUARE, VibEvent.INPUT_DODGE_TRIANGLE, VibEvent.INPUT_DODGE_CIRCLE, VibEvent.INPUT_DODGE_CROSS};

      for (VibEvent button : buttons) {
         if (Input.isDown(button) && q.peek().getType().equals(button.getStr())) {
            if (delta <= 0.4) { // early hit
               q.peek().setState("hit");
            } else if (!gutter.isEmpty() && lastBeat != 0 && gutter.peekFirst().getType().equals(button.getStr()) && pos - lastBeat <= 0.4) {
               gutter.peekFirst().setState("hit");
            }

            Input.depress(button);
         }
      }
   }

}
