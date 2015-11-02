package src.gfx;

import src.scene.Scene;

public class Cross extends AbstractShape {

   public Cross(float dst, float maxDistX) {
     super(dst, maxDistX);
   }

   public void render() {
     Scene.p.pushMatrix();
     Scene.p.translate(dst, Scene.p.height/2f - 60, 20);
     Scene.p.line(Scene.p.random(super.vibrate), Scene.p.random(super.vibrate), Scene.p.random(super.vibrate) + 60, Scene.p.random(super.vibrate) + 60);
     Scene.p.line(Scene.p.random(super.vibrate) + 60, Scene.p.random(super.vibrate), Scene.p.random(super.vibrate), Scene.p.random(super.vibrate) + 60);
     Scene.p.popMatrix();
   }
 }