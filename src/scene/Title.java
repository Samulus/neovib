package src.scene;
import processing.core.PConstants;
import src.event.*;

public class Title extends AbstractScene {

     private String[] options = {"play", "browse", "video lag", "audio lag", "gamepad", "debug"};
     private int selected = 0;

     public void setup() {
       Scene.p.noFill();
       Scene.p.stroke(255);
       Scene.p.textAlign(PConstants.RIGHT);
     }

     public void render() {
       Scene.p.background(0);
       renderTitle();
       renderMenu();
     }

     public void input(VibEvent event) {

       /* move menu */
       if (event == VibEvent.INPUT_RIGHT)
         selected += (selected < options.length-1) ? 1 : 0;
       else if (event == VibEvent.INPUT_LEFT)
         selected -= (selected > 0) ? 1 : 0;

       /* switch state */
       else if (event == VibEvent.INPUT_ACCEPT) {
          System.out.println("This is the part where we switch to " + options[selected]);
         //Scene.focus("Debug");
       }

     }

     public void logic() {}

     private void renderTitle() {
       Scene.p.pushMatrix();
       Scene.p.translate(Scene.p.width/1.1f, Scene.p.height/1.1f);
       Scene.p.textSize(32);
       Scene.p.text("neovib", 0, 0);
       Scene.p.popMatrix();
     }

     private void renderMenu() {
       Scene.p.stroke(255);
       Scene.p.pushMatrix();
       Scene.p.translate(Scene.p.width/8f, Scene.p.height/2f);
       Scene.p.textSize(32);

       for (int i=0; i < this.options.length; ++i) {
         if (i == selected)
           Scene.p.text(options[i], Scene.p.random(3), Scene.p.random(3));
         else
            Scene.p.text(options[i], 0, 0);
         Scene.p.translate(128, 0);
       }

       Scene.p.popMatrix();
     }


   }