
/*
 * BeatKonducta.java
 * -------------------
 * The BeatKonducta module is responsible for determining when the next beat
 * is scheduled to occur. It accepts a list of Beats and destructively removes and
 * examines where
 * The BeatKonducta is responsible for accepting an Audio object and a LinkedList<Double> of
 * beats.
 */

package src.audio;

import src.primitives.*;
import src.scene.Scene;

import java.util.LinkedList;


public class BeatKonducta {

   public LinkedList<Float> impeding;
   private LinkedList<AbstractShape> render;
   private Audio audio;
   private LinkedList<Double> beat;

   public BeatKonducta(Audio audio, LinkedList<Double> beat) {
      this.audio = audio;
      this.beat = beat;
      this.render = new LinkedList<AbstractShape>();
      this.impeding = new LinkedList<Float>();
   }

   public LinkedList<AbstractShape> getList() {
      return this.render;
   }

   public void deleteOld() {

      if (!render.isEmpty()) {
         AbstractShape s = render.getFirst();
         if (s.getDistance() <= -60) {
            render.removeFirst();
            if (!impeding.isEmpty())
               impeding.removeFirst();
         }
      }


   }

   public boolean nextReady() {
      Double beatTime = this.beat.peekFirst();
      if (beatTime == null) return false; // EOF
      double speed = calcSpeed(beatTime);
      if (speed < 10) return true; // higher = delay time until beat can enter (aka note scroll speed)
      this.beat.removeFirst();
      AbstractShape tmp = null;
      switch ((int)Scene.p.random(4) * 1) {
          case 0:
              tmp = new Square(Scene.p.width, (float) speed);
              break;
          case 1:
              tmp = new Triangle(Scene.p.width, (float) speed);
              break;
          case 2:
              tmp = new Circle(Scene.p.width, (float) speed);
              break;
          case 3:
              tmp = new Cross(Scene.p.width, (float) speed);
              break;
      }
      render.add(tmp);
      return true;
   }


   public double calcSpeed(double beatTime) {

      // Sam's Beat Detection:
      // TLDR: determine the amount of time until next beat, distance to travel,
      // and amount frames we have to work with, divide this by the travel time
      // to get the movement speed

      double pos = audio.getPosition();
      double dst = (beatTime * 1000) - pos; // where beat time is in Seconds, pos Millis
      double travel = (Scene.p.width - (Scene.p.width / 7f) - 60);
      double framesWeNeedToMoveIn = 60 * dst / 1000;
      double speed = (travel / framesWeNeedToMoveIn) ; // 1024 px / 240 frames = scroll at 4.26;
      return speed;
   }

}
