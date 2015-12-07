package ui;

import scene.Scene;

public class Score {

   public int total;
   public int consecutive;
   public float amt;

   public void render() {
      Scene.p.pushMatrix();
      Scene.p.fill(0);
      Scene.p.textSize(64);
      Scene.p.translate(Scene.p.width / 2, Scene.p.height / 3);
      Scene.p.text(total, Scene.p.random(amt), Scene.p.random(amt));
      Scene.p.fill(255);
      Scene.p.popMatrix();
   }

   public void reset() {
      total = 0;
      consecutive = 0;
      amt = 0;
   }

   public void increase() {
      ++total;
   }

   public void miss() {
      consecutive = 0;
      amt = 0;
   }
}
