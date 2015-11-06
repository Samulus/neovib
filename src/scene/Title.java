package src.scene;

import processing.core.PConstants;
import src.event.EQ;
import src.event.VibEvent;
import src.ui.Menu;

public class Title extends AbstractScene {

   private String[] options = {"Browse", "Video Lag", "Audio Lag", "Gamepad", "Debug"};
   private Menu menu;

   public void setup() {
      menu = new Menu(options, "noscroll");
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
         if (options[menu.getIndex()].equals("Debug")) EQ.enqueue(VibEvent.SCENE_DEBUG);
         if (options[menu.getIndex()].equals("Browse")) EQ.enqueue(VibEvent.SCENE_BROWSER);

         //Scene.focus("Debug");
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