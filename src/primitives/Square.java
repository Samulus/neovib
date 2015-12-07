package src.primitives;

import src.constants.VibConstant;
import src.event.VibEvent;
import src.scene.Scene;

public class Square extends AbstractShape {

   public Square(float dst, float speed) {
      super(dst, speed);
      super.setType(VibEvent.INPUT_DODGE_SQUARE.getStr());
   }

   public void render() {
      Scene.p.pushMatrix();
      Scene.p.translate(dst, Scene.p.height / 2f - 60);

      super.paint();


      Scene.p.line(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate),
              Scene.p.random(super.vibrate), VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate));

      Scene.p.line(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate), Scene.p.random(super.vibrate));

      Scene.p.line(VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate),
              Scene.p.random(super.vibrate), VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate));

      Scene.p.popMatrix();
   }

}
