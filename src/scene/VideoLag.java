package src.scene;

import processing.core.PConstants;
import src.event.VibEvent;
import src.ui.Menu;

import java.util.ArrayList;

public class VideoLag extends AbstractScene {

   ArrayList<String> options;
   private Menu menu;

   public VideoLag() {
      options = new ArrayList<String>();
      options.add("Start");
      menu = new Menu(options, "noscroll");
   }

   /* Required */
   public void setup() {
   }

   public void render() {
      renderTitle();
      menu.render(Scene.p.width / 16f, Scene.p.height / 2f);
      renderReturnMsg();
      renderTestMsg();
   }

   public void logic() {
   }

   public void input(VibEvent event) {
      menu.navigate(event);
   }

   /* Personal */
   private void renderTitle() {
      Scene.p.background(0);
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 1.1f, Scene.p.height / 1.1f);
      Scene.p.textAlign(PConstants.RIGHT);
      Scene.p.text("Video Lag", 0, 0);
      Scene.p.textAlign(PConstants.LEFT);
      Scene.p.popMatrix();
   }

   private void renderReturnMsg() {
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 16f, Scene.p.height / 1.1f);
      Scene.p.text("Press " + VibEvent.INPUT_PREVIOUS.getStr() + "to return", 0, 0);
      Scene.p.popMatrix();
   }

   private void renderTestMsg() {
      Scene.p.pushMatrix();
      Scene.p.translate(Scene.p.width / 16, Scene.p.height / 16f);
      Scene.p.text("Press " + VibEvent.INPUT_ACCEPT.toString() + " each time the screen flashes", 0, 0);
      Scene.p.popMatrix();
   }


}
