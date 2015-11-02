package src.scene;

import processing.core.PApplet;
import java.util.ArrayList;
import src.audio.Audio;
import src.audio.Beat;
import src.audio.Detector;
import src.event.VibEvent;

// good:


public class Debug extends AbstractScene {

   private Audio audio;
   private Beat beat; 
   private float size = 32;

   public void setup() {
      Scene.p.stroke(255);
      String fpath = "samson.mp3";
      audio = new Audio(fpath, 1024);
      ArrayList<Float> blist = Detector.load(fpath);
      System.out.println(blist.size());// block for a few 1-2 second
      beat = new Beat(blist);
      audio.play();
   }

   public void render() {
      Scene.p.background(0);
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 2f, Scene.p.height / 2f);
      Scene.p.textSize(this.size);
      this.size = PApplet.constrain(this.size * 0.95f, 32f, 64f);
      if (this.size >= 40)
         Scene.p.text("Beat", Scene.p.random(1), Scene.p.random(1));
      else
         Scene.p.text("Beat", 0, 0);

      Scene.p.popMatrix();
   }
   
   public void logic() {
      if (this.beat.detect(this.audio.position())) {
         this.size = 64;
      }
   }

   public void input(VibEvent event) {}

}
