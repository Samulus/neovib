package src.audio;

import src.primitives.AbstractShape;
import src.primitives.Square;
import src.scene.Scene;

import java.util.LinkedList;

public class BeatKonducta {

   public LinkedList<Float> impeding;
   private LinkedList<AbstractShape> render;
   private Audio audio;
   private Beat beat;

   public BeatKonducta(Audio audio, Beat beat) {
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

   public void nextReady() {
      Double beatTime = this.beat.getList().peekFirst();
      if (beatTime == null) return; // EOF
      double speed = calcSpeed(beatTime);
      if (speed < 10) return; // higher = delay time until beat can enter (aka note scroll speed)
      this.beat.getList().removeFirst();
      Square tmp = new Square(Scene.p.width, (float) speed);
      render.add(tmp);
   }


   public double calcSpeed(double beatTime) {

      // Sam's Beat Detection:
      // TLDR: determine the amount of time until next beat, distance to travel,
      // and amount frames we have to work with, divide this by the travel time
      // to get the movement speed

      double pos = audio.getPosition();
      double dst = (beatTime * 1000) - pos; // where beat time is in Seconds, pos Millis
      double travel = Scene.p.width - (Scene.p.width / 7f) - 60;
      double framesWeNeedToMoveIn = 60 * dst / 1000;
      double speed = travel / framesWeNeedToMoveIn; // 1024 px / 240 frames = scroll at 4.26;
      return speed;
   }

}
