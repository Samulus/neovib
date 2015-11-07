package src.primitives;

import processing.core.PConstants;
import src.scene.Scene;


public class Player {

   int i = 0;

   public void render() {
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 7f, Scene.p.height / 2.2f);
      Scene.p.line(0,0, 0, 64);
      Scene.p.popMatrix();

   }


}
