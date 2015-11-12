package src.scene;

import processing.core.PConstants;
import src.audio.Audio;
import src.audio.BeatKonducta;
import src.clock.Clock;
import src.event.EQ;
import src.event.VibEvent;
import src.primitives.AbstractShape;
import src.primitives.Player;
import src.primitives.Track;
import src.ui.Menu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class VideoLag extends AbstractScene {

   /* Menu */
   ArrayList<String> options;
   /* Graphics */
   Player player = new Player();
   Track track = new Track();
   /* Test */
   LinkedList<Double> actualHitTimes = new LinkedList<Double>();
   boolean testRunning = false;
   Clock time;
   double average;
   private Menu menu;
   /* "Audio" */
   private Audio audio;
   private LinkedList<Double> beat;
   private BeatKonducta konducta;

   public VideoLag() {
      options = new ArrayList<String>();
      options.add("Start");
      options.add("Return");
      menu = new Menu(options, "noscroll");
      audio = new Audio(30000);

      LinkedList<Double> beat = new LinkedList<Double>();
      for (int i = 3; i < 18; ++i) {
         beat.add(i + 0.0);
      }
      konducta = new BeatKonducta(audio, beat);
   }

   /* Required */
   public void setup() {
   }

   public void render() {
      Scene.p.background(0);

      /* Render Menu */
      if (!testRunning) {
         menu.render(Scene.p.width / 16f, Scene.p.height / 1.2f);
         renderTitle();
         renderTestMsg();
         return;
      }

      /* Render Test */
      player.render();
      track.render();
      for (AbstractShape s : konducta.getList()) {
         if (s.getDistance() <= Scene.p.width / 7)
            s.setVibrate(40);
         s.render();
      }
   }

   public void logic() {
      if (testRunning) {
         if (konducta.nextReady() == false) {
            testRunning = false;
            System.out.println("Average is " + average / 15);
         }
         for (AbstractShape s : konducta.getList()) {
            player.logic(s.getDistance());
            s.advance();
         }
      }
   }

   public void input(VibEvent event) {

      if (event.isNavigate()) {
         menu.navigate(event);
         return;
      }

      if (event == VibEvent.INPUT_ACCEPT) {

         if (testRunning && !actualHitTimes.isEmpty()) {
            double hit = Math.abs(actualHitTimes.pollFirst() - time.elapsedTime());
            System.out.println(hit);
            average += hit;
         }

         if (options.get(menu.getIndex()).equals("Return")) {
            EQ.enqueue(VibEvent.SCENE_TITLE);
            return;
         } else if (options.get(menu.getIndex()).equals("Start")) {
            if (!testRunning) {
               restartTest();
               System.out.println("Test Restarted");
               testRunning = true;
            }
         }
      }
   }

   private void restartTest() {
      average = 0;
      audio = new Audio(30000);
      beat = new LinkedList<Double>();
      actualHitTimes = new LinkedList<Double>();
      for (int i = 3; i < 18; ++i) {
         beat.add(i + 0.0);
         actualHitTimes.add(i * 1000.0);
      }

      konducta = new BeatKonducta(audio, beat);
      audio.play();
      time = new Clock();
   }

   /* Personal */
   private void renderTitle() {
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 1.1f, Scene.p.height / 1.1f);
      Scene.p.textAlign(PConstants.RIGHT);
      Scene.p.text("Video Lag", 0, 0);
      Scene.p.textAlign(PConstants.LEFT);
      Scene.p.popMatrix();
   }

   private void renderTestMsg() {
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 16, Scene.p.height / 16f);
      Scene.p.text("Press " + VibEvent.INPUT_ACCEPT.toString() + " each time the screen flashes", 0, 0);
      Scene.p.popMatrix();
   }


}

