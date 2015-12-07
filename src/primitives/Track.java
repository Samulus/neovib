package src.primitives;

import src.scene.Scene;

import java.util.LinkedList;

public class Track {

   float vibrate;

   public void render(LinkedList<AbstractShape> lines) {

      Scene.p.pushMatrix();
      Scene.p.translate(0, Scene.p.height / 2);
      Scene.p.stroke(0);

      /* Flatline */
      Scene.p.line(Scene.p.random(vibrate),
              Scene.p.random(vibrate),
              Scene.p.width + Scene.p.random(vibrate),
              Scene.p.random(vibrate));

      Scene.p.popMatrix();
   }

    public float getVibrate() {
        return vibrate;
    }

    public void setVibrate(float vibrate) {
        this.vibrate = vibrate;
    }
}