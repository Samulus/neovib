package src.scene;

import processing.core.PConstants;
import src.event.EQ;
import src.event.VibEvent;
import src.ui.Menu;

import java.util.ArrayList;

public class Title extends AbstractScene {

   private ArrayList<String> options;
   private Menu menu;

   /* once per program life */
   public Title() {
      options = new ArrayList<String>();
      options.add("Browse");
      options.add("Video Lag");
      options.add("Audio Lag");
      options.add("Gamepad");
      menu = new Menu(options, "noscroll");
   }

   /* every time the scene is reentered */
   public void setup() {
      Scene.p.noFill();
      Scene.p.stroke(255);
      Scene.p.textAlign(PConstants.RIGHT);
   }

   public void render() {
      Scene.p.background(0);
      renderTitle();
      menu.render(Scene.p.width / 7f, Scene.p.height / 2f);
   }

   public void input(VibEvent event) {

      if (event.isNavigate())
         menu.navigate(event);

       /* switch state */
      if (event == VibEvent.INPUT_ACCEPT) {
         // I'll change this to EQ.enqueue(VibEvent.lookup(options[menu.getIndex()]) later
         if (options.get(menu.getIndex()).equals("Browse")) EQ.enqueue(VibEvent.SCENE_BROWSER);
         if (options.get(menu.getIndex()).equals("Video Lag")) EQ.enqueue(VibEvent.SCENE_VIDEO_LAG);
      }

   }

   public void logic() {
   }

   private void renderTitle() {
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 1.1f, Scene.p.height / 1.1f);
      Scene.p.textSize(32);
      Scene.p.text("neovib", 0, 0);
      Scene.p.popMatrix();
   }

}