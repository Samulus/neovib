package src.audio;

import java.util.LinkedList;

public class Beat {

   private float dst = 0.0f;
   private LinkedList<Double> beats = null;
   private int currBeat = 0;

   public Beat(LinkedList<Double> beats) {
      this.beats = beats;
   }

   public double peek() {
      return this.beats.get(currBeat);
   }

   public double time() {
      if (currBeat == 0) return beats.get(currBeat);
      return beats.get(currBeat) - beats.get(currBeat - 1);
   }

   public LinkedList<Double> getList() {
      return this.beats;
   }

}
