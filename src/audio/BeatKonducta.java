/*
 * BeatKonducta.java
 * -------------------
 * The BeatKonducta module is responsible for determining when the next beat
 * is scheduled to occur. It accepts a list of Beats and destructively removes and
 * examines where
 * The BeatKonducta is responsible for accepting an Audio object and a LinkedList<Double> of
 * beats.
 */

package audio;

import primitives.*;
import scene.Scene;

import java.util.LinkedList;


public class BeatKonducta {

   public LinkedList<Double> impeding;
   private LinkedList<AbstractShape> render;
   private Audio audio;
   private LinkedList<Double> beat;
   private boolean first = false;

   public BeatKonducta(Audio audio, LinkedList<Double> beat) {
      this.audio = audio;
      this.beat = beat;
      this.render = new LinkedList<AbstractShape>();
      this.impeding = new LinkedList<Double>();
   }

   public LinkedList<AbstractShape> getList() {
      return this.render;
   }

   public void deleteOld() {
      if (!render.isEmpty()) {
         AbstractShape s = render.getFirst();
         if (s.getDistance() <= Scene.p.width / 7) {
            render.removeFirst();
            System.out.println("removing");
         }
      }
   }

   public boolean nextReady() {
      Double beatTime = this.beat.peekFirst();

      // play catchup if the beats starts to lag behind the audio
      // this only happens if the user minimizes the processing window
      // or in the rare case where bytes slip through the cracks

      while (!this.beat.isEmpty() && this.audio.getPosition() > this.beat.peekFirst() * 1000) {
         this.beat.pollFirst();
      }

      if (beatTime == null) return false; // EOF
      double speed = calcSpeed(beatTime);

      // higher = delay time until beat can enter (aka note scroll speed)
      if (speed < 15)
         return true;

      impeding.add(this.beat.pollFirst());
      AbstractShape tmp = null;
      switch ((int) Scene.p.random(4)) {
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
      //render.add(new Square(Scene.p.width, (float) speed));
      render.add(tmp);
      return true;
   }


   public double calcSpeed(double beatTime) {

      // Sam's Beat Detection:
      // Determine the amount of time until next beat, distance to travel,
      // and amount frames we have to work with, divide this by the travel time
      // to get the movement speed

      double pos = audio.getPosition();
      double dst = (beatTime * 1000) - pos; // where beat time is in seconds, pos in miliseconds
      double travel = (Scene.p.width - (Scene.p.width / 7f));
      double framesWeNeedToMoveIn = 60 * dst / 1000;
      double speed = (travel / framesWeNeedToMoveIn); // 1024 travel px  / 240 frames = scroll at 4.26 px / frame;
      return speed;
   }

}
