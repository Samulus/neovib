package src.primitives;

import src.constants.VibConstant;
import src.scene.Scene;

public class Triangle extends AbstractShape {

   public Triangle(float dst, float speed) {
      super(dst, speed);
   }

   public void render() {
      Scene.p.pushMatrix();
      Scene.p.translate(dst, VibConstant.SHAPE_ORIGIN_Y);

      if (super.state.equals("hit")) {
         int[] c = VibConstant.HIT_COLOR;
         Scene.p.stroke(c[0], c[1], c[2]);
      }

      /* Draw Shape */
      Scene.p.triangle(Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE / 2 + Scene.p.random(super.vibrate),
              0f,
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate));

      /* Blackout Bottom */
      Scene.p.stroke(0);
      Scene.p.line(Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate));

      Scene.p.stroke(255);
      Scene.p.popMatrix();
   }
}