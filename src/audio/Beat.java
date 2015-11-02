package src.audio;
public class Beat {

  private float dst = 0.0f;
  private float[] beats = null;
  private int currBeat = 0;

  public Beat(float[] beats) {
    this.beats = beats;
  }

  public boolean detect(float pos) {
    pos /= 1000.0f;
    float beatTime = this.beats[currBeat];
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
