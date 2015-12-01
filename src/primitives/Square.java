package src.primitives;

import src.constants.VibConstant;
import src.scene.Scene;

public class Square extends AbstractShape {

   public Square(float dst, float speed) {
      super(dst, speed);
   }

   public void render() {
      Scene.p.pushMatrix();
      Scene.p.translate(dst, VibConstant.SHAPE_ORIGIN_Y);

       Scene.p.stroke(0);

      /* Status */
      if (super.state.equals("hit")) {
         int[] c = VibConstant.HIT_COLOR;
         Scene.p.stroke(c[0], c[1], c[2]);
      }

      /* Outline */
      Scene.p.line(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate),
              Scene.p.random(super.vibrate), VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate));

      Scene.p.line(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate), Scene.p.random(super.vibrate));

      Scene.p.line(VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate),
              Scene.p.random(super.vibrate), VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate));

      /* Reset Color / Matrix */
      Scene.p.popMatrix();
   }

}
