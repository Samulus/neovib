import processing.core.PApplet;
import src.event.EQ;
import src.event.VibEvent;
import src.input.Input;
import src.scene.Scene;

import java.awt.event.KeyEvent;

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
      Scene.p.frameRate(1000);
   }

   public void draw() {

      while (true) {

         VibEvent event = EQ.dequeue();
         if (event == null) break;

         else if (event == VibEvent.ERROR_CODE) {
            System.out.println("error detected audi5k we bounce now");
            System.exit(-1);
         } else if (event.isScene()) {
            Scene.focus(event);
         } else if (event.isNavigate() ||
                 event.isTraversal() ||
                 event.isPlayer()) {
            Scene.input(event);
         }
      }

      Scene.logic();
      Scene.render();

   }

   public void keyPressed() {
      Input.poll(key);
   }

   public void keyPressed(KeyEvent e) {
      System.out.println("Called");
      EQ.enqueue(VibEvent.INPUT_ACCEPT);
   }

}
