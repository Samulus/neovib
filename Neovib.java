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
      size(1280, 720, P3D);

   }

   public void setup() {
      Scene.setContext(this); // always call first
      Scene.initScenes();
      Scene.focus(VibEvent.SCENE_BROWSER);
      Scene.p.frameRate(60);
   }

   public void draw() {
      Input.poll(key);

      while (true) {

         VibEvent event = EQ.dequeue();

         if (event == null) break;

         else if (event == VibEvent.ERROR_CODE) {
            System.out.println("error detected audi5k");
            System.exit(-1);
         } else if (event.isScene()) {
            Scene.focus(event);
         } else if (event.isNavigate() || event.isTraversal()) {
            Scene.input(event);
         }
      }


      // TODO: only check events / poll / logic on clock ticks
      Scene.logic();
      Scene.render();

   }

}
