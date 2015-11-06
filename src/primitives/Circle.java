package src.primitives;

import processing.core.PConstants;
import src.scene.Scene;

public class Circle extends AbstractShape {

   public Circle(float dst, float speed) {
      super(dst, speed);
   }

   public void render() {
      Scene.p.ellipseMode(PConstants.CORNER);
      Scene.p.pushMatrix();
      Scene.p.translate(dst, Scene.p.height / 2f - 60, 20);
      Scene.p.ellipse(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate), 60, 60);
      Scene.p.popMatrix();
   }

}