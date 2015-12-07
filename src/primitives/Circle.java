package src.primitives;

import processing.core.PConstants;
import src.constants.VibConstant;
import src.event.VibEvent;
import src.scene.Scene;

public class Circle extends AbstractShape {

   public Circle(float dst, float speed) {
      super(dst, speed);
      super.setType(VibEvent.INPUT_DODGE_CIRCLE.getStr());
   }

   public void render() {

      super.paint();

      Scene.p.ellipseMode(PConstants.CORNER);
      Scene.p.pushMatrix();
      Scene.p.translate(dst, Scene.p.height / 2f - 60);
      Scene.p.ellipse(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate), VibConstant.SHAPE_SIZE, VibConstant.SHAPE_SIZE);
      Scene.p.popMatrix();
   }

}