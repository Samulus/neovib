package src.audio;
import java.util.ArrayList;

public class Beat {

  private float dst = 0.0f;
  private ArrayList<Float> beats = null;
  private int currBeat = 0;

  public Beat(ArrayList<Float> beats) {
    this.beats = beats;
  }

  public boolean detect(float pos) {
    pos /= 1000.0f;
    if (currBeat >= this.beats.size())
       return false;
    float beatTime = this.beats.get(currBeat);
    this.dst = Math.abs(pos - beatTime);

    /* Beat Detected */
    if (dst < 0.9) {
      System.out.println(beatTime);
      currBeat++; 
      return true;
    } 

    /* Missed Beat */
    if (pos > beatTime) {
      System.out.println("Missing " + beatTime);
      currBeat++; 
      return false;
    }

    return false;
  }
}
