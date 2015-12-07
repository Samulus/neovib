import processing.core.PApplet;
import src.event.EQ;
import src.event.VibEvent;
import src.input.Input;
import src.musicdb.Echonest;
import src.scene.Scene;

public class Neovib extends PApplet {


   public static void main(String[] args) {
      PApplet.main(Neovib.class.getName());
   }

   public void settings() {
      size(1280, 720, P2D);
   }

   public void setup() {

      /* Graphics / Game Scenes */
      Scene.setContext(this);
      Scene.initScenes();
      Scene.p.frameRate(1000);
      Scene.focus(VibEvent.SCENE_TITLE);
      Echonest.ConnectFromFile();

      /* Input Subsystem */
      Input.Init();
   }

   public void draw() {

      while (true) {

         Input.poll();

         VibEvent event = EQ.dequeue();
         if (event == null) break;

         if (event.isNavigate()) {
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
      Input.keyAction(key, true);
   }

   public void keyReleased() {
      Input.keyAction(key, false);
   }

}
