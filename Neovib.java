import processing.core.PApplet;
import src.event.EQ;
import src.event.VibEvent;
import src.input.Input;
import src.scene.Scene;

public class Neovib extends PApplet {

   public static void main(String[] args) {
      PApplet.main(Neovib.class.getName());

   }

   public void settings() {
      size(1280, 720, P2D);
   }

   public void setup() {
      Scene.setContext(this);
      Scene.initScenes();

      Scene.focus(VibEvent.SCENE_TITLE);
      Scene.p.frameRate(60);
   }

   public void draw() {

      Input.poll(key);

      while (true) {

         VibEvent event = EQ.dequeue();
         if (event == null) break;

         if (event.isNavigate() || event.isTraversal() || event.isPlayer()) {
            Scene.input(event);
         } else if (event == VibEvent.ERROR_CODE) {
            System.out.println("error detected audi5k we bounce now");
            System.exit(-1);
         } else if (event.isScene()) {
            Scene.focus(event);
         }
      }

      Scene.logic();
      Scene.render();

   }

   public void keyPressed() {
   }

}
