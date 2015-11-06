package src.scene;

import processing.core.PApplet;
import src.audio.Audio;
import src.audio.Beat;
import src.audio.Detector;
import src.event.VibEvent;

import java.util.LinkedList;

public class Debug extends AbstractScene {

   private Audio audio;
   private Beat beat;
   private float size = 32;

   public void setup() {
      Scene.p.stroke(255);
      String fpath = null;

      if (Browser.fsong == null) {
         System.out.println("Enter a file first");
         Scene.p.exit();
         System.exit(0);
      }


      try {
         fpath = Browser.fsong.getCanonicalPath();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Debug.java: unable to open file");
      }

      audio = new Audio(fpath, 1024);
      System.out.println(fpath);
      LinkedList<Float> blist = Detector.load(Browser.fsong);
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
   }

   public void input(VibEvent event) {
   }

}
