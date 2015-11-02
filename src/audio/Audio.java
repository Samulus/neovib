package src.audio;
import ddf.minim.*;
import src.scene.Scene;

public class Audio {
  
  Minim minim;
  AudioPlayer song;
  float[] beats;


  public Audio(String fpath, int sampleRate) {
    this.minim = new Minim(Scene.p);
    this.song = minim.loadFile(fpath, sampleRate);
  }

  public void play() {
    this.song.play();
  }

  public int position() {
    return this.song.position();
  }

  // returns -1 on end of song
  public float nextBeat() {
    return 0.0f;
  }
  

}
