package src.debug;

import processing.core.PConstants;
import src.audio.Audio;
import src.audio.BeatKonducta;
import src.scene.Scene;

import java.util.LinkedList;

public class BeatOverlay {

   private Audio audio;
   private LinkedList<Double> beat;
   private BeatKonducta konducta;

   public BeatOverlay(Audio audio, LinkedList<Double> beat, BeatKonducta konducta) {
      this.audio = audio;
      this.beat = beat;
      this.konducta = konducta;
   }

   public void render() {
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 1.1f, Scene.p.height / 1.1f);
      Scene.p.textAlign(PConstants.RIGHT);
      Scene.p.text("DEBUG", 0, 0);
      Scene.p.textAlign(PConstants.LEFT);
      Scene.p.popMatrix();
   }

}
