package src.audio;

import java.util.LinkedList;

public class Beat {

   private float dst = 0.0f;
   private LinkedList<Float> beats = null;
   private int currBeat = 0;

   public Beat(LinkedList<Float> beats) {
      this.beats = beats;
   }

   public float peek() {
      return this.beats.get(currBeat);
   }

   public float time() {
      if (currBeat == 0) return beats.get(currBeat);
      return beats.get(currBeat) - beats.get(currBeat - 1);
   }

   public LinkedList<Float> getList() {
      return this.beats;
   }

}
