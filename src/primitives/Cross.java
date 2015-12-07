package primitives;

import constants.VibConstant;
import event.VibEvent;
import scene.Scene;

public class Cross extends AbstractShape {

   public Cross(float dst, float speed) {
      super(dst, speed);
      super.setType(VibEvent.INPUT_DODGE_CROSS.getStr());
   }

   public void render() {
      Scene.p.pushMatrix();
      Scene.p.translate(dst, Scene.p.height / 2 - 60);

      super.paint();

      Scene.p.line(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate),
              Scene.p.random(super.vibrate) + VibConstant.SHAPE_SIZE,
              Scene.p.random(super.vibrate) + VibConstant.SHAPE_SIZE);
      Scene.p.line(Scene.p.random(super.vibrate) + VibConstant.SHAPE_SIZE,
              Scene.p.random(super.vibrate), Scene.p.random(super.vibrate),
              Scene.p.random(super.vibrate) + VibConstant.SHAPE_SIZE);

      Scene.p.popMatrix();
   }
}