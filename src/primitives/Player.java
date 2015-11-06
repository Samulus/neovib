package src.primitives;

import processing.core.PConstants;
import src.scene.Scene;


public class Player {

   int i = 0;

   public void render() {
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 16f, Scene.p.height / 2.2f);
      Scene.p.ellipseMode(PConstants.CORNER);
      Scene.p.ellipse(0, 0, 60, 60);
      Scene.p.triangle(-20, 0, 0, -20, 20, 0);
      Scene.p.triangle(-20, 0, 0, 20, 20, 0);
      Scene.p.popMatrix();

   }


}
