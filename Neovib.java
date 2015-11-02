import processing.core.*;
import src.event.EQ;
import src.event.VibEvent;
import src.input.Input;
import src.scene.Scene;

public class Neovib extends PApplet {
  
   public void settings() {
      size(1280, 720, P3D);

   }
   
   public void setup() {   
      Scene.init(this);
      Scene.focus("Title");
   }
   
   public void draw() {
      Input.poll(key);
      
      while (true) {

         VibEvent event = EQ.dequeue();
         if (event == null) break;
         
         switch (event) {
            
            /* errors */
            case ERROR_CODE: 
               System.out.println("A bad thing happened");
               System.exit(-1);
               break;
            
            /* scenes */
            case SCENE_TITLE: Scene.focus("Title"); break;
            case SCENE_GAME:  Scene.focus("Game"); break;
            case SCENE_AUDIO_LAG: 
            case SCENE_VIDEO_LAG:
            case SCENE_BROWSER:
            case SCENE_DEBUG:
            case SCENE_SETTINGS:
               System.out.printf("[%s scene not implemented yet]\n", event);
               break;
               
            /* input */
            case INPUT_UP:
            case INPUT_DOWN:
            case INPUT_LEFT:
            case INPUT_RIGHT:
            case INPUT_ACCEPT:
            case INPUT_CANCEL:
               Scene.input(event);
               break;
         }
         
      }
      
      // TODO: only check events / poll / logic on clock ticks
      Scene.logic();
      Scene.render();
    
   }
   
   public static void main(String[] args) {
      PApplet.main(Neovib.class.getName());
      
   }

}
