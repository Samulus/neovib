package src.gfx;

import src.scene.Scene;

public class Triangle extends AbstractShape {

   public Triangle(float dst, float maxDistX) {
     super(dst, maxDistX);
   }
   

   public void render() {
     Scene.p.pushMatrix();
     Scene.p.translate(super.dst, Scene.p.height/2f - 60, 20);
     Scene.p.triangle(Scene.p.random(super.vibrate),       // x1
                      60 + Scene.p.random(super.vibrate),  // y1
                      30 + Scene.p.random(super.vibrate),  // x2
                      0f,                                  // y2
                      60 + Scene.p.random(super.vibrate),  // x3
                      60 + Scene.p.random(super.vibrate)); // y3
     Scene.p.popMatrix();
   }
}