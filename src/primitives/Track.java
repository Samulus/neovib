package src.primitives;

import src.scene.Scene;

public class Track {

   float vibrate;

   public void setVibrate(float vibrate) {
      this.vibrate = vibrate;
   }

   public void render() {
      Scene.p.pushMatrix();
      Scene.p.translate(0, Scene.p.height / 2f); // ,20?
      Scene.p.line(0 + Scene.p.random(vibrate),
              Scene.p.random(vibrate),
              Scene.p.width + Scene.p.random(vibrate),
              Scene.p.random(vibrate));
      Scene.p.popMatrix();
   }

}