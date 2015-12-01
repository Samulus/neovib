package src.primitives;

import processing.core.PConstants;
import src.constants.VibConstant;
import src.scene.Scene;

public class Circle extends AbstractShape {

   public Circle(float dst, float speed) {
      super(dst, speed);
   }

   public void render() {

       Scene.p.stroke(0);
      if (super.state.equals("hit")) {
         int[] c = VibConstant.HIT_COLOR;
         Scene.p.stroke(c[0], c[1], c[2]);
      }

      Scene.p.ellipseMode(PConstants.CORNER);
      Scene.p.pushMatrix();
      Scene.p.translate(dst, VibConstant.SHAPE_ORIGIN_Y);
      Scene.p.ellipse(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate), VibConstant.SHAPE_SIZE, VibConstant.SHAPE_SIZE);
      Scene.p.popMatrix();
   }

}