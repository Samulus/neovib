package src.scene;

import processing.core.PApplet;
import src.event.VibEvent;

public class Debug extends AbstractScene {

   // private Audio player;
   private float size = 32;

   public void setup() {
      Scene.p.background(0);
      //globalAudio.play();
   }

   public void render() {
      Scene.p.background(0);
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 2f, Scene.p.height / 2f);
      Scene.p.textSize(this.size);
      this.size = PApplet.constrain(this.size * 0.95f, 32f, 64f);
      if (this.size >= 40)
         Scene.p.text("Beat", Scene.p.random(5), Scene.p.random(5));
      else
         Scene.p.text("Beat", 0, 0);

      Scene.p.popMatrix();
   }
   
   public void logic() {

      /*
      if (globalBeat.detect(globalAudio.position())) {
         this.size = 64;
      }
      */

   }

   public void input(VibEvent event) {}

}
