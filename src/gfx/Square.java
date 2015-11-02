package src.gfx;

import src.scene.Scene;

public class Square extends AbstractShape {

   public Square(float dst, float maxDistX) {
     super(dst, maxDistX);
   }

   public void render() {
     Scene.p.pushMatrix();
     Scene.p.translate(dst, Scene.p.height/2f - 60, 20);
    
     Scene.p.line(0  + Scene.p.random(super.vibrate),   0 + Scene.p.random(super.vibrate), 0  + Scene.p.random(super.vibrate), 60 + Scene.p.random(super.vibrate));
     Scene.p.line(0  + Scene.p.random(super.vibrate),   0 + Scene.p.random(super.vibrate), 60 + Scene.p.random(super.vibrate),  0 + Scene.p.random(super.vibrate));
     Scene.p.line(60 + Scene.p.random(super.vibrate),   0 + Scene.p.random(super.vibrate), 60 + Scene.p.random(super.vibrate), 60 + Scene.p.random(super.vibrate));
     Scene.p.line(0 + Scene.p.random(super.vibrate),   60 + Scene.p.random(super.vibrate), 60 + Scene.p.random(super.vibrate), 60 + Scene.p.random(super.vibrate));
     Scene.p.popMatrix();
   }

 }
