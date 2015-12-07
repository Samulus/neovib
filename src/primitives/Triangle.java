package src.primitives;

import src.constants.VibConstant;
import src.event.VibEvent;
import src.scene.Scene;

public class Triangle extends AbstractShape {

   public Triangle(float dst, float speed) {
      super(dst, speed);
      super.setType(VibEvent.INPUT_DODGE_TRIANGLE.getStr());
   }

   public void render() {
      Scene.p.pushMatrix();
      Scene.p.translate(dst, Scene.p.height / 2 - 60);

      super.paint();

      Scene.p.triangle(Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE / 2 + Scene.p.random(super.vibrate),
              0f,
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate));

      Scene.p.line(Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate),
              VibConstant.SHAPE_SIZE + Scene.p.random(super.vibrate));

      Scene.p.stroke(0);
      Scene.p.popMatrix();
   }
}