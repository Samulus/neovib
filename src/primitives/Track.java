package src.primitives;

import src.constants.VibConstant;
import src.scene.Scene;

import java.util.LinkedList;

public class Track {

   float vibrate;

   public void setVibrate(float vibrate) {
      this.vibrate = vibrate;
   }

   public void render(LinkedList<AbstractShape> lines) {

      Scene.p.pushMatrix();
      Scene.p.translate(0, VibConstant.TRACK_ORIGIN_Y);

      /* Flatline */
      Scene.p.line(Scene.p.random(vibrate),
              Scene.p.random(vibrate),
              Scene.p.width + Scene.p.random(vibrate),
              Scene.p.random(vibrate));

      Scene.p.popMatrix();
   }

}